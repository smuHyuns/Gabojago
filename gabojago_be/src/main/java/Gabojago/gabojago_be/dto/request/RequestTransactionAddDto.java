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
public class RequestTransactionAddDto {
    private Long tripId;
    private Integer paymentMethod;
    private String transactionType;
    private LocalDate expenseDate;
    private String expenseDetail;
    private Integer expenseAmount;
    private Integer exchangeAmount;
    private String expenseType;
}
