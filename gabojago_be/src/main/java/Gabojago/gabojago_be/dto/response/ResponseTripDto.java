package Gabojago.gabojago_be.dto.response;

import lombok.*;

import java.sql.Date;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTripDto {
    private Long tripId;
    private String tripCountry;
    private LocalDate startPeriod;
    private LocalDate endPeriod;
    private Integer tripStatus;
    private String description;
}
