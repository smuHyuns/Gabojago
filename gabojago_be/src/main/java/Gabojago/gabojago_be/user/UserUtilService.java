package Gabojago.gabojago_be.user;

import Gabojago.gabojago_be.dto.request.RequestSignUpDto;
import Gabojago.gabojago_be.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class UserUtilService {
    private final PasswordEncoder passwordEncoder;
    @Value("${default.profile.image:none}")
    private String defaultProfileImage;

    public Date stringToDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, formatter);
            return Date.valueOf(localDate);
        } catch (Exception e) {
            throw new IllegalArgumentException("유효하지 않은 포맷입니다. 'YYYY-MM-DD' 를 사용해주세요.", e);
        }
    }

    public User setUser(RequestSignUpDto dto){
        User user = new User();
        String password = dto.getUserPassword();
        String encodedPassword = passwordEncoder.encode(password);
        user.setUserNickname(dto.getUserNickname());
        user.setUserPassword(encodedPassword);
        user.setUserLoginId(dto.getUserLoginId());
        user.setUserProfileImg(defaultProfileImage);
        user.setUserEmail(dto.getUserEmail());
        user.setUserUsername(dto.getUserUsername());
        user.setUserBirth(stringToDate(dto.getUserBirth()));
        user.setUserGender(dto.getUserGender());
        return user;
    }
}
