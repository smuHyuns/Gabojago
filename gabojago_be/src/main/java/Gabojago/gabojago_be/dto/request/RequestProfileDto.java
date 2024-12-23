package Gabojago.gabojago_be.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestProfileDto {
    private String userProfileImg;
    private String userNickname;
    private String userEmail;
    private String userPassword;
    private boolean isImgChanged;
}
