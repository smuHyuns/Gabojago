package Gabojago.gabojago_be.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class UserUtilService {

    public Date stringToDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, formatter);
            return Date.valueOf(localDate);
        } catch (Exception e) {
            throw new IllegalArgumentException("유효하지 않은 포맷입니다. 'YYYY-MM-DD' 를 사용해주세요.", e);
        }
    }
}
