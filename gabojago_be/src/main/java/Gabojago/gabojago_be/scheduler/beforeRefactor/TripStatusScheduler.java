package Gabojago.gabojago_be.scheduler.beforeRefactor;

import Gabojago.gabojago_be.trip.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class TripStatusScheduler {

    private final TripService tripService;

//    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Seoul")
//    public void runDailyTask() {
//        log.info("TripStatus 업데이트 작업 시작: {}", java.time.LocalDateTime.now());
//        callApi();
//    }
//
//    private void callApi() {
//        try {
////            tripService.updateTripStatus();
//            log.info("TripStatus 업데이트 작업 완료");
//        } catch (Exception e) {
//            log.error("TripStatus 업데이트 작업 중 오류 발생: ", e.getMessage());
//        }
//    }
}
