package Gabojago.gabojago_be.trip;

import Gabojago.gabojago_be.dto.response.ResponseTripDto;
import Gabojago.gabojago_be.entity.Trip;
import Gabojago.gabojago_be.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class TripService {
    private final TripRepository tripRepository;
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
}
