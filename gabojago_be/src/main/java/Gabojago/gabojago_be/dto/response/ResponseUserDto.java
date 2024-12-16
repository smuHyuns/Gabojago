package Gabojago.gabojago_be.dto.response;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUserDto {
    private Long userId;
    private String userNickname;
    private String userPassword;
    private String userProfileImg;
    private String userLoginId;
    private String userUsername;
    private String userEmail;
    private Integer userGender;
    private LocalDate userBirth;
}
