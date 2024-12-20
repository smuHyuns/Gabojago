package Gabojago.gabojago_be.trip;

import Gabojago.gabojago_be.dto.response.ResponseExchangeRateDto;
import Gabojago.gabojago_be.dto.response.ResponseTripDetailEntireDto;
import Gabojago.gabojago_be.dto.response.ResponseTripDto;
import Gabojago.gabojago_be.entity.ExchangeRate;
import Gabojago.gabojago_be.entity.Trip;
import Gabojago.gabojago_be.exception.TripNotFoundException;
import Gabojago.gabojago_be.exchangeRate.ExchangeRateService;
import Gabojago.gabojago_be.jwt.JwtUtil;
import Gabojago.gabojago_be.transaction.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final JwtUtil jwtUtil;


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
        ResponseTripDetailEntireDto dto = new ResponseTripDetailEntireDto();
        Long totalExpense = Optional.ofNullable(transactionService.getSum(tripId)).orElse(0L);

        Trip t = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found with ID: " + tripId));

        dto.setTripBudget(Long.valueOf(Optional.ofNullable(t.getTripBudget()).orElse(0)));
        dto.setDescription(t.getDescription());
        dto.setStartPeriod(t.getStartPeriod());
        dto.setEndPeriod(t.getEndPeriod());
        dto.setTotalExpense(totalExpense);

        log.info("Trip Budget = {}", dto.getTripBudget());
        log.info("Description = {}", dto.getDescription());
        log.info("Start Period = {}", dto.getStartPeriod());
        log.info("End Period = {}", dto.getEndPeriod());
        log.info("Total Expense = {}", dto.getTotalExpense());

        return dto;
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


}
