package Gabojago.gabojago_be.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
