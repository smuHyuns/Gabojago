package Gabojago.gabojago_be.transaction;

import Gabojago.gabojago_be.dto.request.RequestTransactionAddDto;
import Gabojago.gabojago_be.dto.request.RequestTransactionDeleteDto;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

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
    private final TransactionUtilService transactionUtilService;

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


    @Transactional
    public void deleteTransaction(String token, RequestTransactionDeleteDto request) {
        Long userId = jwtUtil.extractUserIdFromToken(token);

        long[] transactionIds = request.getTransactionIds();
        long tripId = request.getTripId();

        // 트립 조회
        Trip trip = tripRepository.findById(tripId).orElseThrow(() ->
                new IllegalArgumentException("해당 여행이 존재하지 않습니다: " + tripId));

        for (long transactionId : transactionIds) {
            Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() ->
                    new IllegalArgumentException("없는 트랜잭션 아이디입니다: " + transactionId));
            trip.setTripBudget(trip.getTripBudget() + transaction.getExpenseAmount());
            // 트랜잭션 삭제
            transactionRepository.deleteById(transactionId);
        }

        tripRepository.save(trip);
    }

    public Transaction saveTransactionFromTrip(String token, RequestTransactionAddDto request) {
        Long userId = jwtUtil.extractUserIdFromToken(token);

        // Trip과 User를 데이터베이스에서 가져옴
        Trip trip = tripRepository.findById(request.getTripId())
                .orElseThrow(() -> new IllegalArgumentException("Trip not found with ID: " + request.getTripId()));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        Transaction transaction = new Transaction();
        transaction.setTrip(trip);
        transaction.setUser(user);
        transaction.setExpenseType(transactionUtilService.convertExpenseType(request.getExpenseType()));
        transaction.setExpenseDate(request.getExpenseDate());
        transaction.setExpenseAmount(request.getExpenseAmount());
        transaction.setExchangeAmount(request.getExchangeAmount());

        Transaction response = transactionRepository.save(transaction);
        return response;
    }
}
