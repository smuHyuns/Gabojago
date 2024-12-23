package Gabojago.gabojago_be.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
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
