package Gabojago.gabojago_be.dto.request;


import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestTripDetailDayDto {
    private Long tripId;
    private Date tripDate;
}
