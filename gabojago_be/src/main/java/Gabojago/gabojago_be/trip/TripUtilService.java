package Gabojago.gabojago_be.trip;

import Gabojago.gabojago_be.exception.ErrorCode;
import Gabojago.gabojago_be.exception.GabojagoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class TripUtilService {
    // 0 - 다가오는 여행 / 1 - 진행 중 / 2 - 여행 완료
    public int calculateTripStatus(LocalDate startDate, LocalDate endDate) {
        LocalDate today = LocalDate.now();

        boolean isCompleted = endDate.isBefore(today) && startDate.isBefore(today);
        boolean isOngoing = (endDate.isAfter(today) || endDate.isEqual(today))
                && (startDate.isBefore(today) || startDate.isEqual(today));
        boolean isUpcoming = today.isBefore(startDate);


        if (isCompleted) {
//            log.info("Calculating status for Start: {}, End: {} , Result : {}", startDate, endDate, 2);
            return 2;
        } else if (isOngoing) {
//            log.info("Calculating status for Start: {}, End: {} , Result : {}", startDate, endDate, 1);
            return 1;
        } else if (isUpcoming) {
//            log.info("Calculating status for Start: {}, End: {} , Result : {}", startDate, endDate, 0);
            return 0;
        }

        log.info("시작일: {}", startDate);
        log.info(" 종료일: {}", endDate);
        throw new GabojagoException(ErrorCode.TRIP_INVALID_DATE_FORMAT);
    }
}
