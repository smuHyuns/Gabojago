package Gabojago.gabojago_be.dto.response;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTripDetailEntireDto {
    private String description;
    private LocalDate startPeriod;
    private LocalDate endPeriod;
    private Long totalExpense;
    private Long tripBudget;
}
