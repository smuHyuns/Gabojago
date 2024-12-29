package Gabojago.gabojago_be.dto.response;

import lombok.*;

import java.time.LocalDate;

@Data
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
