package Gabojago.gabojago_be.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseProfileDto {
    private String userProfileImg;
    private String userUsername;
    private String userNickname;
    private String userEmail;
    private String userPassword;
}
