package Gabojago.gabojago_be.trip;

import Gabojago.gabojago_be.dto.response.ResponseTripDto;
import Gabojago.gabojago_be.entity.Trip;
import Gabojago.gabojago_be.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
