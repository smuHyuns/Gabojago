package Gabojago.gabojago_be.transaction;

import Gabojago.gabojago_be.dto.request.RequestTransactionAddDto;
import Gabojago.gabojago_be.entity.Transaction;
import Gabojago.gabojago_be.entity.Trip;
import Gabojago.gabojago_be.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TransactionUtilService {
    String[] list = {"관광", "교통", "쇼핑", "숙박", "음식", "항공", "기타"};

    public Integer convertExpenseType(String expenseType) {
        for (int i = 0; i < list.length; i++) {
            if (list[i].equals(expenseType)) {
                return i;
            }
        }
        return -1;
    }

    public Transaction setTransaction(Trip trip, User user, RequestTransactionAddDto request) {
        Transaction transaction = new Transaction();
        transaction.setTrip(trip);
        transaction.setUser(user);
        transaction.setExpenseType(convertExpenseType(request.getExpenseType()));
        transaction.setExpenseDate(request.getExpenseDate());
        transaction.setExpenseAmount(request.getExpenseAmount());
        transaction.setExchangeAmount(request.getExchangeAmount());
        transaction.setTransactionType(request.getTransactionType());
        return transaction;
    }
}
