package Gabojago.gabojago_be.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTripDetailEntireDto {
    private String description;
    private Date startPeriod;
    private Date endPeriod;
    private Integer expense;
    private Integer balance;
}
