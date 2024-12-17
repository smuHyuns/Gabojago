package Gabojago.gabojago_be.trip;

import Gabojago.gabojago_be.dto.request.RequestTripDetailDayDto;
import Gabojago.gabojago_be.dto.response.ResponseExchangeRateDto;
import Gabojago.gabojago_be.dto.response.ResponseTripDetailDayDto;
import Gabojago.gabojago_be.dto.response.ResponseTripDetailEntireDto;
import Gabojago.gabojago_be.dto.response.ResponseTripDto;
import Gabojago.gabojago_be.entity.Trip;
import Gabojago.gabojago_be.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/trip")
@Slf4j
@RequiredArgsConstructor
public class TripController {
    private final TripService tripService;
    private final JwtUtil jwtUtil;

    @GetMapping
    public Long getTrips(@RequestHeader("Authorization") String token) {
        log.info("Received token: {}", token);
        try {
            Long userId = jwtUtil.extractUserIdFromToken(token);
            log.info("Extracted userId: {}", userId);
            return userId;
        } catch (Exception e) {
            log.error("Error extracting userId from token: ", e);
            return null;
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ResponseTripDto>> getUserTrips(@RequestHeader("Authorization") String token) {
        try {
            List<ResponseTripDto> response = tripService.getTrips(token);
            log.info(response.toString());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/status")
    public ResponseEntity<List<ResponseTripDto>> getUserTripsByStatus(@RequestHeader("Authorization") String token, @RequestParam("status") Integer status) {
        try {
            List<ResponseTripDto> response = tripService.getTripsByStatus(token, status);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/detail-all/{tripId}")
    public ResponseEntity<ResponseTripDetailEntireDto> getTripDetailEntire(@RequestHeader("Authorization") String token,
                                                                           @PathVariable("tripId") Long tripId) {
        log.info("trip-detail-all 환영");
        log.info("token : {}", token);
        log.info("tripId : {}", tripId);

        try {
            ResponseTripDetailEntireDto response = tripService.getEntireTripDetail(token, tripId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    //trip Id 나라 조회 -> 해당 나라에 맞는 화폐단위와 exchange-rate 조회 -> 전달
    @GetMapping("/get-country")
    public ResponseEntity<ResponseExchangeRateDto> getCountry(@RequestHeader("Authorization") String token, @RequestParam("tripId") Long tripId) {
        try {
            return ResponseEntity.ok(tripService.getExchange(token, tripId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // trip-detail
    @GetMapping("/detail/{tripId}")
    public ResponseEntity<ResponseTripDetailEntireDto> getTripDetail(@PathVariable("tripId") Long tripId) {
        return ResponseEntity.ok(tripService.getDetail(tripId));
    }

}
