package Gabojago.gabojago_be.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestAuthCheckDto {
    String email;
    String authCode;
}
