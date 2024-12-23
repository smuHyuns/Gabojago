package Gabojago.gabojago_be.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSaveTransactionDto {
    private Long transactionId;
    private Long tripId;
    private Long userId;
    private Integer expenseType;
    private LocalDate expenseDate;
    private Integer expenseAmount;
    private Integer exchangeAmount;
}
