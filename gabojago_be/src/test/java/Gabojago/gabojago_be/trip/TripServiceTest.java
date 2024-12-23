package Gabojago.gabojago_be.trip;

import Gabojago.gabojago_be.entity.Trip;
import Gabojago.gabojago_be.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)  // Mockito를 사용하기 위한 어노테이션
@Slf4j
public class TripServiceTest {

    @Mock
    private TripRepository tripRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private TripService tripService;

    @Test
    public void testGetCountry() {
        // Arrange
        Long tripId = 1L;

        Trip trip = new Trip();
        trip.setTripId(tripId);
        trip.setTripCountry("South Korea");  // 여행 국가 설정

        // Mock behavior
        when(tripRepository.findByTripId(tripId)).thenReturn(trip);  // mock 객체로 반환 값 설정

        // Act
        String country = tripService.getCountry(tripId);
        log.info("country : {}", country);
        // Assert
        assertEquals("South Korea", country);  // 반환 값이 예상 값과 일치하는지 확인
    }
}
