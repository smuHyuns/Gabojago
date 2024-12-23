package Gabojago.gabojago_be.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestLoginDto {
    @JsonProperty
    private String userLoginId;

    @JsonProperty
    private String userPassword;
}
