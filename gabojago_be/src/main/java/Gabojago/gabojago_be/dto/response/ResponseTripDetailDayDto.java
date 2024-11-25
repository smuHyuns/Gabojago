package Gabojago.gabojago_be.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTripDetailDayDto {
    private String description;
    private Integer expense;
    private Integer balance;
}
