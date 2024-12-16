package Gabojago.gabojago_be.transaction;

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
}
