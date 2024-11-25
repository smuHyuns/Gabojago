package Gabojago.gabojago_be.trip;

import Gabojago.gabojago_be.dto.request.RequestTripDetailDayDto;
import Gabojago.gabojago_be.dto.response.ResponseTripDetailDayDto;
import Gabojago.gabojago_be.dto.response.ResponseTripDetailEntireDto;
import Gabojago.gabojago_be.dto.response.ResponseTripDto;
import Gabojago.gabojago_be.entity.Trip;
import Gabojago.gabojago_be.jwt.JwtUtil;
import Gabojago.gabojago_be.transaction.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
@RequiredArgsConstructor
public class TripService {
    private final TripRepository tripRepository;
    private final TransactionRepository transactionRepository;
    private final JwtUtil jwtUtil;

    public List<ResponseTripDto> getTrips(String token) {
        try {
            Long userId = jwtUtil.extractUserIdFromToken(token);
            List<Trip> trips = tripRepository.findByUserIdOrderByStartPeriodAscEndPeriodAsc(userId);

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
            List<Trip> trips = tripRepository.findByTripStatusAndUserIdOrderByStartPeriodAscEndPeriodAsc(status, userId);
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

//    public ResponseTripDetailDayDto getTripExAndRes(String token, RequestTripDetailDayDto request) {
//        try {
//            Long userId = jwtUtil.extractUserIdFromToken(token);
//            Object result = tripRepository.findBudgetAndExpenses(request.getTripId(), request.getTripDate());
//            if (result != null) {
//                Object[] data = (Object[]) result;
//                return new ResponseTripDetailDayDto(
//                        String.valueOf(data[0]), // Description
//                        data[1] != null ? (Integer) data[1] : 0, // Expense
//                        data[2] != null ? (Integer) data[2] : 0  // Balance
//                );
//            }
//            return new ResponseTripDetailDayDto("No data", 0, 0);
//        } catch (Exception e) {
//            log.error("유효하지 않은 토큰 혹은 요청입니다 : {}", e.getMessage());
//            throw new RuntimeException("Error processing trip detail request");
//        }
//    }


    public ResponseTripDetailEntireDto getEntireTripDetail(String token, Long tripId) {
        Long userId = jwtUtil.extractUserIdFromToken(token);

        Trip trip = tripRepository.findById(tripId).orElse(null);
        //Integer totalExpense = tripRepository.findTotalExpenseByTripId(tripId);

        ResponseTripDetailEntireDto responseTripDetailDayDto = new ResponseTripDetailEntireDto(
                trip.getDescription(),
                trip.getStartPeriod(),
                trip.getEndPeriod(),
                30000,
                trip.getTripBudget()
        );

        log.info(responseTripDetailDayDto.toString());
        return responseTripDetailDayDto;
    }


}
