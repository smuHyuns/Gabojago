package Gabojago.gabojago_be.user;

import Gabojago.gabojago_be.dto.request.RequestSignUpDto;
import Gabojago.gabojago_be.dto.response.ResponseProfileDto;
import Gabojago.gabojago_be.entity.User;
import Gabojago.gabojago_be.exception.ErrorCode;
import Gabojago.gabojago_be.exception.GabojagoException;
import Gabojago.gabojago_be.file.S3FileServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserUtilService {
    private final PasswordEncoder passwordEncoder;
    @Value("${default.profile.image:none}")
    private String defaultProfileImage;

    @Value("${default.profile.img.src : https://gabojago.s3.ap-northeast-2.amazonaws.com/%EB%8C%95%EB%8C%95%EC%93%B0.jpg}")
    private String defaultProfileImageSrc;
    private final S3FileServiceImpl s3FileService;

    public Date stringToDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, formatter);
            return Date.valueOf(localDate);
        } catch (Exception e) {
            log.info("에러 메시지 : {}", e.getMessage());
            throw new GabojagoException(ErrorCode.USER_UTIL_INVALID_DATE_FORMAT);
        }
    }

    public User setUser(RequestSignUpDto dto) {
        User user = new User();
        String password = dto.getUserPassword();
        String encodedPassword = passwordEncoder.encode(password);
        user.setUserNickname(dto.getUserNickname());
        user.setUserPassword(encodedPassword);
        user.setUserLoginId(dto.getUserLoginId());
        user.setUserProfileImg(defaultProfileImage);
        user.setUserEmail(dto.getUserEmail());
        user.setUserUsername(dto.getUserUsername());
        user.setUserBirth(dto.getUserBirth());
        user.setUserGender(dto.getUserGender());
        return user;
    }

    public ResponseProfileDto setProfileDto(User user) {
        ResponseProfileDto response = new ResponseProfileDto();
        response.setUserPassword(user.getUserPassword());
        response.setUserNickname(user.getUserNickname());

        if (user.getUserProfileImg().equals("none")) {
            String imgUrl = defaultProfileImageSrc;
            response.setUserProfileImg(imgUrl);
        } else {
            response.setUserProfileImg(user.getUserProfileImg());
        }

        response.setUserEmail(user.getUserEmail());
        response.setUserUsername(user.getUserUsername());
        return response;
    }

    public void deleteExistingProfileImg(String existingImg) {
        s3FileService.delete(existingImg);
    }
}
