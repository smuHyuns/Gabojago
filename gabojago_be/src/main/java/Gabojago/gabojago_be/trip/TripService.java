package Gabojago.gabojago_be.trip;

import Gabojago.gabojago_be.dto.request.RequestTripSaveDto;
import Gabojago.gabojago_be.dto.response.ResponseExchangeRateDto;
import Gabojago.gabojago_be.dto.response.ResponseTripDetailEntireDto;
import Gabojago.gabojago_be.dto.response.ResponseTripDto;
import Gabojago.gabojago_be.dto.response.ResponseTripSaveDto;
import Gabojago.gabojago_be.entity.ExchangeRate;
import Gabojago.gabojago_be.entity.Trip;
import Gabojago.gabojago_be.entity.User;
import Gabojago.gabojago_be.exception.ErrorCode;
import Gabojago.gabojago_be.exception.GabojagoException;
import Gabojago.gabojago_be.exchangeRate.ExchangeRateService;
import Gabojago.gabojago_be.jwt.JwtUtil;
import Gabojago.gabojago_be.transaction.TransactionService;
import Gabojago.gabojago_be.user.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final TransactionService transactionService;
    private final ExchangeRateService exchangeRateService;
    private final TripUtilService tripUtilService;
    private final JwtUtil jwtUtil;
    private final UserService userService;


    public String getCountry(long tripId) {
        Trip trip = tripRepository.findByTripId(tripId);
        return trip.getTripCountry();
    }

    public List<ResponseTripDto> getTrips(String token) {
        Long userId = jwtUtil.extractUserIdFromToken(token);
        if (userId == null) throw new GabojagoException(ErrorCode.USER_NOT_FOUND);

        List<Trip> trips = tripRepository.findByUserUserIdOrderByStartPeriodAscEndPeriodAsc(userId);

        return trips.stream()
                .map(trip -> new ResponseTripDto(
                        trip.getTripId(),
                        trip.getTripCountry(),
                        trip.getStartPeriod(),
                        trip.getEndPeriod(),
                        trip.getTripStatus(),
                        trip.getDescription()
                ))
                .toList();
    }

    public List<ResponseTripDto> getTripsByStatus(String token, Integer status) {

        Long userId = jwtUtil.extractUserIdFromToken(token);
        if (!(status == 0 || status == 1 || status == 2))
            throw new GabojagoException(ErrorCode.TRIP_INVALID_TRIPSTATUS);

        List<Trip> trips = tripRepository.findByTripStatusAndUserUserIdOrderByStartPeriodAscEndPeriodAsc(status, userId);
        return trips.stream()
                .map(trip -> new ResponseTripDto(
                        trip.getTripId(),
                        trip.getTripCountry(),
                        trip.getStartPeriod(),
                        trip.getEndPeriod(),
                        trip.getTripStatus(),
                        trip.getDescription()
                ))
                .toList();
    }

    @Transactional
    public ResponseTripDetailEntireDto getEntireTripDetail(String token, Long tripId) {
        Long userId = jwtUtil.extractUserIdFromToken(token);
        return getDetail(tripId);
    }

    @Transactional
    public ResponseTripDetailEntireDto getDetail(Long tripId) {
        ResponseTripDetailEntireDto response = new ResponseTripDetailEntireDto();
        Long totalExpense = Optional.ofNullable(transactionService.getSum(tripId)).orElse(0L);

        Trip t = tripRepository.findById(tripId)
                .orElseThrow(() -> new GabojagoException(ErrorCode.TRIP_NOT_FOUND));

        response.setTripBudget(Long.valueOf(Optional.ofNullable(t.getTripBudget()).orElse(0)));
        response.setDescription(t.getDescription());
        response.setStartPeriod(t.getStartPeriod());
        response.setEndPeriod(t.getEndPeriod());
        response.setTotalExpense(totalExpense);
        return response;
    }


    public ResponseExchangeRateDto getExchange(String token, Long tripId) {
        String country = getCountry(tripId);

        Optional<ExchangeRate> exchangeRate = exchangeRateService.getExchangeRateByCountry(country);

        ResponseExchangeRateDto response = new ResponseExchangeRateDto();
        response.setCurrency(exchangeRate.get().getCurrency());
        response.setRate(exchangeRate.get().getRate());
        return response;
    }


    public ResponseTripSaveDto saveTrip(String token, RequestTripSaveDto request) {
        long userId = jwtUtil.extractUserIdFromToken(token);

        User user = userService.getUserByUserId(userId)
                .orElseThrow(() -> new GabojagoException(ErrorCode.USER_NOT_FOUND));

        if (request.getCountry() == null || request.getHeadcount() == null ||
                request.getStartPeriod() == null || request.getEndPeriod() == null) {
            throw new GabojagoException(ErrorCode.TRIP_INVALID_FORMAT);
        }

        Trip trip = new Trip();
        trip.setTripCountry(request.getCountry());
        trip.setHeadcount(request.getHeadcount());
        trip.setStartPeriod(request.getStartPeriod());
        trip.setEndPeriod(request.getEndPeriod());
        trip.setTripBudget(0);
        trip.setTripExchangeBudget(0);
        trip.setDescription(request.getDescription());
        trip.setTripStatus(tripUtilService.calculateTripStatus(
                request.getStartPeriod(), request.getEndPeriod()
        ));
        trip.setUser(user);

        Trip savedTrip = tripRepository.save(trip);

        return new ResponseTripSaveDto(savedTrip.getTripId());
    }

    @Transactional
    public void updateTripStatus() {
        int page = 0;
        int size = 100;
        int totalUpdatedCount = 0;

        Page<Trip> tripPage;

        do {
            Pageable pageable = PageRequest.of(page, size);
            tripPage = tripRepository.findAll(pageable);
            List<Trip> trips = tripPage.getContent();

            int updatedCount = updateTrips(trips);
            totalUpdatedCount += updatedCount;

            page++;
        } while (!tripPage.isLast());

        log.info("총 {}개의 TripStatus가 업데이트되었습니다.", totalUpdatedCount);
    }

    public int updateTrips(List<Trip> trips) {
        int updatedCount = 0;

        for (Trip trip : trips) {
            int newStatus = tripUtilService.calculateTripStatus(trip.getStartPeriod(), trip.getEndPeriod());
            if (newStatus != trip.getTripStatus()) {
                trip.setTripStatus(newStatus);
                tripRepository.save(trip);
                updatedCount++;
            }
        }

        return updatedCount;
    }

    public void updateTripBudget(long tripId, Integer tripBudget, Integer exchangeTripBudget, String transactionType) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new GabojagoException(ErrorCode.TRIP_NOT_FOUND));

        Integer curBudget = trip.getTripBudget();
        Integer exchangedCurBudget = trip.getTripExchangeBudget();

        if ("지출".equals(transactionType)) {
            curBudget -= tripBudget;
            exchangedCurBudget -= exchangeTripBudget;
        } else if ("추가".equals(transactionType)) {
            curBudget += tripBudget;
            exchangedCurBudget += exchangeTripBudget;
        } else {
            throw new GabojagoException(ErrorCode.TRANSACTION_INVALID_TYPE);
        }

        trip.setTripBudget(curBudget);
        trip.setTripExchangeBudget(exchangedCurBudget);

        tripRepository.save(trip);
    }

    @Transactional
    public void saveTrips(Trip paraTrip) {
        Trip trip = tripRepository.findByTripId(paraTrip.getTripId());
        trip.setTripStatus(paraTrip.getTripStatus());
        tripRepository.save(trip);
    }

    @Transactional
    public void test(long tripId) {
        Trip trip = tripRepository.findByTripId(tripId);
        trip.setTripStatus(2);
        tripRepository.save(trip);
    }
}
