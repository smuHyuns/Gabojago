package Gabojago.gabojago_be.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestTripSaveDto {
    private String country;

    private Integer headcount;

    @JsonProperty("start_period")
    private LocalDate startPeriod;

    @JsonProperty("end_period")
    private LocalDate endPeriod;

    private String description;
}