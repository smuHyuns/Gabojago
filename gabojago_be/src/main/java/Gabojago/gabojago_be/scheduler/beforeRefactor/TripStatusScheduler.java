package Gabojago.gabojago_be.scheduler.beforeRefactor;

import Gabojago.gabojago_be.entity.Trip;
import Gabojago.gabojago_be.trip.TripRepository;
import Gabojago.gabojago_be.trip.TripService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class TripStatusScheduler {

    private final TripService tripService;
    private final TripRepository tripRepository;
    private final EntityManager em;

//    @Scheduled(cron = "0 * * * * ?", zone = "Asia/Seoul")
//    private void updatedTripStatus() {
//        try {
//            log.info("TripStatus 업데이트 시작");
//            updateTripStatus();
//            em.flush();
//            em.clear();
//            log.info("TripStatus 업데이트 작업 완료");
//        } catch (Exception e) {
//            log.error("TripStatus 업데이트 작업 중 오류 발생: {}", e.getMessage());
//        }
//    }

    @Scheduled(cron = "0 * * * * ?", zone = "Asia/Seoul")
    public void updateTripStatus() {
        try {
            int totalUpdatedCount = 0;
            LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));

            List<Trip> trips = tripRepository.findAll();

            for (Trip trip : trips) {
                Integer tripStatus = trip.getTripStatus();
                Integer correctStatus = 0;

                if (trip.getStartPeriod().isBefore(today)) {
                    correctStatus = 2;
                } else if (!trip.getEndPeriod().isAfter(today)) {
                    correctStatus = 1;
                }

                if (!Objects.equals(tripStatus, correctStatus)) {
                    trip.setTripStatus(correctStatus);
                    totalUpdatedCount++;
                }
            }
            tripRepository.saveAll(trips);
            em.flush();
            log.info("총 {}개의 TripStatus가 업데이트되었습니다.", totalUpdatedCount);
        } catch (Exception e) {
            log.error("TripStatus 업데이트 작업 중 오류 발생: {}", e.getMessage());
        }
    }

}
