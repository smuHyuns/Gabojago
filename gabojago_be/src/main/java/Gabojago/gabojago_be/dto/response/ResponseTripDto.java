package Gabojago.gabojago_be.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTripDto {
    private Long tripId;
    private String tripCountry;
    private Date startPeriod;
    private Date endPeriod;
    private Integer tripStatus;
    private String description;
}
