package Gabojago.gabojago_be.dto.response;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseExchangeRateDto {
    private String currency;
    private Double rate;
}
