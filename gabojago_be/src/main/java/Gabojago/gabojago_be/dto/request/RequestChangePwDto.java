package Gabojago.gabojago_be.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestChangePwDto {
    public String userLoginId;
    public String newPassword;
}
