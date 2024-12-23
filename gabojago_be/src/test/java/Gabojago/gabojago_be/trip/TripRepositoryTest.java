package Gabojago.gabojago_be.trip;

import Gabojago.gabojago_be.dto.response.ResponseTripDetailEntireDto;
import Gabojago.gabojago_be.entity.Transaction;
import Gabojago.gabojago_be.transaction.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Slf4j
class TripRepositoryTest {

//    @Autowired
//    private TripRepository tripRepository;
//
//    @Autowired
//    private TransactionRepository transactionRepository;
//    @Test
//    void findTotalExpenseByTripId() {
//        List<Transaction> transactions = transactionRepository.findAllByTripId(1L);
//        for (Transaction transaction : transactions) {
//            log.info(transaction.getExpenseAmount().toString());
//        }
//    }

//    @Test
//    void getTripDetailDayNative() {
//        ResponseTripDetailEntireDto result = tripRepository.getTripDetailDayNative(1L);
//        System.out.println(result);
//    }

}
