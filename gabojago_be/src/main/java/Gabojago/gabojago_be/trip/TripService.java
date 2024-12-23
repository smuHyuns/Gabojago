package Gabojago.gabojago_be.trip;

import Gabojago.gabojago_be.dto.request.RequestTripSaveDto;
import Gabojago.gabojago_be.dto.response.ResponseExchangeRateDto;
import Gabojago.gabojago_be.dto.response.ResponseTripDetailEntireDto;
import Gabojago.gabojago_be.dto.response.ResponseTripDto;
import Gabojago.gabojago_be.dto.response.ResponseTripSaveDto;
import Gabojago.gabojago_be.entity.ExchangeRate;
import Gabojago.gabojago_be.entity.Trip;
import Gabojago.gabojago_be.entity.User;
import Gabojago.gabojago_be.exception.TripNotFoundException;
import Gabojago.gabojago_be.exception.UserNotFoundException;
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


import java.util.Collections;
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
        try {
            Long userId = jwtUtil.extractUserIdFromToken(token);
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
        } catch (Exception e) {
            log.error("Error fetching trips: {}", e.getMessage());
            throw new RuntimeException("Error fetching trips");
        }
    }

    public List<ResponseTripDto> getTripsByStatus(String token, Integer status) {
        try {
            Long userId = jwtUtil.extractUserIdFromToken(token);
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
        } catch (Exception e) {
            log.info("유효하지 않은 토큰혹은 상태코드입니다 : {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Transactional
    public ResponseTripDetailEntireDto getEntireTripDetail(String token, Long tripId) {
        //Long userId = jwtUtil.extractUserIdFromToken(token);
        //logger.info("userId = {}", userId);
        log.info("tripId = {}", tripId);
        return getDetail(tripId);
    }

    @Transactional
    public ResponseTripDetailEntireDto getDetail(Long tripId) {
        ResponseTripDetailEntireDto response = new ResponseTripDetailEntireDto();
        Long totalExpense = Optional.ofNullable(transactionService.getSum(tripId)).orElse(0L);

        Trip t = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found with ID: " + tripId));

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

    public void updateTripBudget(long tripId, Integer tripBudget, Integer exchangeTripBudget, String transactionType) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new TripNotFoundException(tripId));

        Integer curBudget = trip.getTripBudget();
        Integer exchangedCurBudget = trip.getTripExchangeBudget();

        if ("지출".equals(transactionType)) {
            curBudget -= tripBudget;
            exchangedCurBudget -= exchangeTripBudget;
        } else if ("추가".equals(transactionType)) {
            curBudget += tripBudget;
            exchangedCurBudget += exchangeTripBudget;
        } else {
            throw new IllegalArgumentException("유효하지 않은 트랜잭션 타입입니다: " + transactionType);
        }

        trip.setTripBudget(curBudget);
        trip.setTripExchangeBudget(exchangedCurBudget);

        tripRepository.save(trip);
    }


    public ResponseTripSaveDto saveTrip(String token, RequestTripSaveDto request) {
        long userId = jwtUtil.extractUserIdFromToken(token);

        // 사용자 조회
        User user = userService.getUserByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        // 요청 데이터 유효성 검사
        if (request.getCountry() == null || request.getHeadcount() == null ||
                request.getStartPeriod() == null || request.getEndPeriod() == null) {
            throw new IllegalArgumentException("Missing required fields in request");
        }


        log.info("reqeuest의 description  : {}", request.getDescription());

        // Trip 객체 초기화
        Trip trip = new Trip();
        trip.setTripCountry(request.getCountry());
        trip.setHeadcount(request.getHeadcount());
        trip.setStartPeriod(request.getStartPeriod());
        trip.setEndPeriod(request.getEndPeriod());
        trip.setTripBudget(0);
        trip.setTripExchangeBudget(0);
        trip.setDescription(request.getDescription());
        // Trip 상태 설정
        trip.setTripStatus(tripUtilService.calculateTripStatus(
                request.getStartPeriod(), request.getEndPeriod()
        ));
        trip.setUser(user);

        // Trip 저장
        Trip savedTrip = tripRepository.save(trip);

        // Response 반환
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

    private int updateTrips(List<Trip> trips) {
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
}
