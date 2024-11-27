package Gabojago.gabojago_be.transaction;

import Gabojago.gabojago_be.dto.request.RequestTransactionDto;
import Gabojago.gabojago_be.dto.response.ResponseTripDetailDayDto;
import Gabojago.gabojago_be.entity.Transaction;
import Gabojago.gabojago_be.entity.Trip;
import Gabojago.gabojago_be.entity.User;
import Gabojago.gabojago_be.jwt.JwtUtil;
import Gabojago.gabojago_be.trip.TripRepository;
import Gabojago.gabojago_be.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public Transaction saveTransaction(RequestTransactionDto requestTransactionDto) {
        // Trip과 User를 데이터베이스에서 가져옴
        Trip trip = tripRepository.findById(requestTransactionDto.getTripId())
                .orElseThrow(() -> new IllegalArgumentException("Trip not found with ID: " + requestTransactionDto.getTripId()));

        User user = userRepository.findById(requestTransactionDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + requestTransactionDto.getUserId()));

        // Transaction 엔티티 생성 및 값 설정
        Transaction transaction = new Transaction();
        transaction.setTrip(trip);
        transaction.setUser(user);
        transaction.setExpenseType(requestTransactionDto.getExpenseType());
        transaction.setExpenseDate(requestTransactionDto.getExpenseDate());
        transaction.setExpenseAmount(requestTransactionDto.getExpenseAmount());
        transaction.setExchangeAmount(requestTransactionDto.getExchangeAmount());

        // 데이터베이스에 저장
        return transactionRepository.save(transaction);
    }


    public Long getSum(Long tripId) {
        Long response = transactionRepository.findSumByTripId(tripId);
        return response;
    }

    public ResponseTripDetailDayDto getExpenseByDate(Long tripId, LocalDate date) {
        Long expense = transactionRepository.findSumByTripIdAndExpenseDate(tripId, date);
        System.out.println(expense);
        ResponseTripDetailDayDto response = new ResponseTripDetailDayDto();
        response.setTotalExpense(expense);
        return response;
    }

    public List<Transaction> getTripDetailTransaction(String token, Long tripId, LocalDate date) {
        //토큰검증
        //Long userId = jwtUtil.extractUserIdFromToken(token);
        //탐색
        List<Transaction> transactions = transactionRepository.findAllByTripTripIdAndExpenseDate(tripId, date);
        System.out.println("transactions 크기 : " + transactions.size());
        return transactions;
    }
}
