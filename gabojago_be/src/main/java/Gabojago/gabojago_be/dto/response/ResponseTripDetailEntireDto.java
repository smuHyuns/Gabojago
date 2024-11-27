package Gabojago.gabojago_be.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTripDetailEntireDto {
    private String description;
    private LocalDate startPeriod;
    private LocalDate endPeriod;
    private Long totalExpense;
    private Long tripBudget;
}
