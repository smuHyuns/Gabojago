package Gabojago.gabojago_be.transaction;

import Gabojago.gabojago_be.dto.request.RequestTransactionAddDto;
import Gabojago.gabojago_be.dto.request.RequestTransactionDeleteDto;
import Gabojago.gabojago_be.entity.Transaction;
import Gabojago.gabojago_be.entity.Trip;
import Gabojago.gabojago_be.entity.User;
import Gabojago.gabojago_be.exception.ErrorCode;
import Gabojago.gabojago_be.exception.GabojagoException;
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
    private final TransactionUtilService transactionUtilService;
    private final TransactionTripCoordinator transactionTripCoordinator;


    public Long getSum(Long tripId) {
        Long response = transactionRepository.findSumByTripId(tripId);
        return response;
    }

    public List<Transaction> getTripDetailTransaction(String token, Long tripId, LocalDate date) {
        //토큰검증
        Long userId = jwtUtil.extractUserIdFromToken(token);
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

        Integer curBudget = 0;
        Integer curExchangeBudget = 0;

        for (long transactionId : transactionIds) {
            Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() ->
                    new GabojagoException(ErrorCode.TRANSACTION_NOT_FOUND));

            transactionRepository.deleteById(transactionId);

            //지출 -> + , 추가 + -> -
            if (transaction.getTransactionType().equals("지출")) {
                curBudget += transaction.getExpenseAmount();
                curExchangeBudget += transaction.getExchangeAmount();
            } else {
                curBudget -= transaction.getExpenseAmount();
                curExchangeBudget -= transaction.getExchangeAmount();
            }
        }

        String transactionType = curBudget >= 0 ? "추가" : "지출";
        curBudget = Math.abs(curBudget);
        curExchangeBudget = Math.abs(curExchangeBudget);

        transactionTripCoordinator.updateBudget(tripId, curBudget, curExchangeBudget,
                transactionType);
    }

    @Transactional
    public Transaction saveTransactionFromTrip(String token, RequestTransactionAddDto request) {
        Long userId = jwtUtil.extractUserIdFromToken(token);

        Trip trip = tripRepository.findById(request.getTripId())
                .orElseThrow(() -> new GabojagoException(ErrorCode.TRIP_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GabojagoException(ErrorCode.USER_NOT_FOUND));

        Transaction transaction = transactionUtilService.setTransaction(trip, user, request);
        Transaction response = transactionRepository.save(transaction);

        transactionTripCoordinator.updateBudget(request.getTripId(),
                request.getExpenseAmount(), request.getExchangeAmount(), request.getTransactionType());

        return response;
    }

    //미사용

//    @Transactional
//    public Transaction saveTransaction(RequestTransactionDto requestTransactionDto) {
//        Trip trip = tripRepository.findById(requestTransactionDto.getTripId())
//                .orElseThrow(() -> new TripNotFoundException(requestTransactionDto.getTripId()));
//
//        User user = userRepository.findById(requestTransactionDto.getUserId())
//                .orElseThrow(() -> new UserNotFoundException(requestTransactionDto.getUserId()));
//
//        // Transaction 엔티티 생성 및 값 설정
//        Transaction transaction = new Transaction();
//        transaction.setTrip(trip);
//        transaction.setUser(user);
//        transaction.setExpenseType(requestTransactionDto.getExpenseType());
//        transaction.setExpenseDate(requestTransactionDto.getExpenseDate());
//        transaction.setExpenseAmount(requestTransactionDto.getExpenseAmount());
//        transaction.setExchangeAmount(requestTransactionDto.getExchangeAmount());
//
//        // 데이터베이스에 저장
//        return transactionRepository.save(transaction);
//    }

//    public ResponseTripDetailDayDto getExpenseByDate(Long tripId, LocalDate date) {
//        Long expense = transactionRepository.findSumByTripIdAndExpenseDate(tripId, date);
//        System.out.println(expense);
//        ResponseTripDetailDayDto response = new ResponseTripDetailDayDto();
//        response.setTotalExpense(expense);
//        return response;
//    }

}
