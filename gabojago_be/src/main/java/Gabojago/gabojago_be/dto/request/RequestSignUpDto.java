package Gabojago.gabojago_be.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestSignUpDto {
    @JsonProperty("user_nickname")
    private String userNickname;

    @JsonProperty("user_password")
    private String userPassword;

    @JsonProperty("user_login_id")
    private String userLoginId;

    @JsonProperty("user_username")
    private String userUsername;

    @JsonProperty("user_email")
    private String userEmail;

    @JsonProperty("user_gender")
    private int userGender;

    @JsonProperty("user_birth")
    private LocalDate userBirth;
}
