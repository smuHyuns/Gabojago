package Gabojago.gabojago_be.scheduler.beforeRefactor;

import Gabojago.gabojago_be.entity.Trip;
import Gabojago.gabojago_be.trip.TripRepository;
import Gabojago.gabojago_be.trip.TripService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class TripStatusScheduler {

    private final TripService tripService;

    @Scheduled(cron = "0 * * * * ?", zone = "Asia/Seoul")
    private void updatedTripStatus() {
        try {
            log.info("TripStatus 업데이트 시작");
            tripService.updateTripStatus();
            log.info("TripStatus 업데이트 작업 완료");
        } catch (Exception e) {
            log.error("TripStatus 업데이트 작업 중 오류 발생: {}", e.getMessage());
        }
    }

}
