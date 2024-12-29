package Gabojago.gabojago_be.dto.response;

import jakarta.persistence.Column;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseProfileDto {
    private String userProfileImg;
    private String userUsername;
    private String userNickname;
    private String userEmail;
    private String userPassword;
}
