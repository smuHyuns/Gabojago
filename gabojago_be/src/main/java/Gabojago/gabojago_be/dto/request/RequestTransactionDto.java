package Gabojago.gabojago_be.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestTransactionDto {
    private Long tripId;
    private Long userId;
    private Integer expenseType;
    private LocalDate expenseDate;
    private Integer expenseAmount;
    private Integer exchangeAmount;
}
