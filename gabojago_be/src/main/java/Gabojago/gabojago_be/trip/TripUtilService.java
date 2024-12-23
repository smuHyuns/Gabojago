package Gabojago.gabojago_be.trip;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TripUtilService {
    // 0 - 다가오는여행 / 1 - 진행중 / 2 - 여행완료
    public int calculateTripStatus(LocalDate startDate, LocalDate endDate) {
        LocalDate today = LocalDate.now();
        if (endDate.isBefore(today)) {
            return 2;
        } else if (!startDate.isAfter(today) && !endDate.isBefore(today)) {
            return 1;
        } else if (today.isBefore(startDate)) {
            return 0;
        }
        throw new IllegalArgumentException("유효하지 않은 날짜입니다.");
    }
}
