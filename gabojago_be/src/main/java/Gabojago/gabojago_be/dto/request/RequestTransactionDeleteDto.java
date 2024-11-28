package Gabojago.gabojago_be.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestTransactionDeleteDto {
    long[] transactionIds;
    long tripId;
}
