package Gabojago.gabojago_be.trip;

import Gabojago.gabojago_be.dto.response.ResponseTripDetailEntireDto;
import Gabojago.gabojago_be.dto.response.ResponseTripDto;
import Gabojago.gabojago_be.entity.Transaction;
import Gabojago.gabojago_be.entity.Trip;
import Gabojago.gabojago_be.jwt.JwtUtil;
import Gabojago.gabojago_be.transaction.TransactionRepository;
import Gabojago.gabojago_be.transaction.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final TransactionService transactionService;
    private final JwtUtil jwtUtil;


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
        return test(tripId);
    }

    @Transactional
    public ResponseTripDetailEntireDto test(Long tripId) {
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



}
