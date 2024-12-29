package Gabojago.gabojago_be.transaction;

import Gabojago.gabojago_be.dto.request.RequestTransactionAddDto;
import Gabojago.gabojago_be.dto.request.RequestTransactionDeleteDto;
import Gabojago.gabojago_be.entity.Transaction;
import Gabojago.gabojago_be.entity.Trip;
import Gabojago.gabojago_be.entity.User;
import Gabojago.gabojago_be.exception.ErrorCode;
import Gabojago.gabojago_be.exception.GabojagoException;
import Gabojago.gabojago_be.jwt.JwtTokenProvider;
import Gabojago.gabojago_be.jwt.JwtUtil;
import Gabojago.gabojago_be.trip.TripRepository;
import Gabojago.gabojago_be.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Slf4j
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TripRepository tripRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionUtilService transactionUtil;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private TransactionTripCoordinator coordinator;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private TransactionService transactionService;


    public TransactionServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("거래내역_저장_테스트")
    @Test
    void saveTransactionFromTrip() {
        // Given
        long userId = 1L;
        long tripId = 1L;
        User user = new User();
        Trip trip = new Trip();
        Transaction mockTransaction = new Transaction();
        String token = jwtTokenProvider.generateToken(userId);

        RequestTransactionAddDto request = new RequestTransactionAddDto(tripId, 0, "추가",
                LocalDate.of(2024, 10, 27), "테스트", 3000, 3000, "관광");

        when(jwtUtil.extractUserIdFromToken(token)).thenReturn(userId);
        when(tripRepository.findById(tripId)).thenReturn(Optional.of(trip));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(transactionUtil.setTransaction(trip, user, request)).thenReturn(mockTransaction);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(mockTransaction);

        // When
        Transaction response = transactionService.saveTransactionFromTrip(token, request);

        // Then
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response).isEqualTo(mockTransaction);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(coordinator, times(1)).updateBudget(
                eq(request.getTripId()),
                eq(request.getExpenseAmount()),
                eq(request.getExchangeAmount()),
                eq(request.getTransactionType())
        );
    }

    @DisplayName("거래내역_저장_TRIP_NOP_FOUND_EXCEPTION")
    @Test
    void saveTransactionFromTrip_TRIP_NOP_FOUND_EXCEPTION() {
        // Given
        long userId = 1L;
        long tripId = 1L;
        String token = jwtTokenProvider.generateToken(userId);

        RequestTransactionAddDto request = new RequestTransactionAddDto(tripId, 0, "추가",
                LocalDate.of(2024, 10, 27), "테스트", 3000, 3000, "관광");

        when(jwtUtil.extractUserIdFromToken(token)).thenReturn(userId);
        when(tripRepository.findById(tripId)).thenThrow(new GabojagoException(ErrorCode.TRIP_NOT_FOUND));

        // When
        GabojagoException response = assertThrows(GabojagoException.class, () -> transactionService.saveTransactionFromTrip(token, request));

        // Then
        assertEquals(ErrorCode.TRIP_NOT_FOUND.getErrorCode(), response.getErrorCode());

        verify(transactionRepository, times(0)).save(any(Transaction.class));
        verify(userRepository, times(0)).findById(anyLong());
        verify(coordinator, times(0)).updateBudget(
                anyLong(),
                anyInt(),
                anyInt(),
                anyString()
        );
    }

    @DisplayName("거래내역_저장_USER_NOT_FOUND_EXCEPTION")
    @Test
    void saveTransactionFromTrip_USER_NOT_FOUND_EXCEPTION() {
        // Given
        long userId = 1L;
        long tripId = 1L;
        User user = new User();
        Trip trip = new Trip();
        String token = jwtTokenProvider.generateToken(userId);

        RequestTransactionAddDto request = new RequestTransactionAddDto(tripId, 0, "추가",
                LocalDate.of(2024, 10, 27), "테스트", 3000, 3000, "관광");

        Transaction mockTransaction = new Transaction();
        when(jwtUtil.extractUserIdFromToken(token)).thenReturn(userId);
        when(tripRepository.findById(tripId)).thenReturn(Optional.of(trip));
        when(userRepository.findById(userId)).thenThrow(new GabojagoException(ErrorCode.USER_NOT_FOUND));

        // When
        GabojagoException exception = assertThrows(GabojagoException.class, () -> transactionService.saveTransactionFromTrip(token, request));

        // Then
        assertEquals(ErrorCode.USER_NOT_FOUND.getErrorCode(), exception.getErrorCode());
        verify(transactionRepository, times(0)).save(any(Transaction.class));
        verify(userRepository, times(1)).findById(userId);
        verify(coordinator, times(0)).updateBudget(
                anyLong(),
                anyInt(),
                anyInt(),
                anyString()
        );
    }

    @DisplayName("거래내역_저장_COORDINATOR_TRIP_NOT_FOUND")
    @Test
    void saveTransactionFromTrip_COORDINATOR_TRIP_NOT_FOUND() {
        // Given
        long userId = 1L;
        long tripId = 1L;
        User user = new User();
        Trip trip = new Trip();
        String token = jwtTokenProvider.generateToken(userId);

        RequestTransactionAddDto request = new RequestTransactionAddDto(
                tripId, 3000, "추가",
                LocalDate.of(2024, 10, 27), "테스트", 3000, 3000, "관광");

        Transaction mockTransaction = new Transaction();
        when(jwtUtil.extractUserIdFromToken(token)).thenReturn(userId);
        when(tripRepository.findById(tripId)).thenReturn(Optional.of(trip));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(transactionUtil.setTransaction(trip, user, request)).thenReturn(mockTransaction);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(mockTransaction);

        doThrow(new GabojagoException(ErrorCode.TRIP_NOT_FOUND))
                .when(coordinator)
                .updateBudget(eq(tripId), anyInt(), anyInt(), anyString());

        // When
        GabojagoException exception = assertThrows(GabojagoException.class,
                () -> transactionService.saveTransactionFromTrip(token, request));

        // Then
        assertEquals(ErrorCode.TRIP_NOT_FOUND.getErrorCode(), exception.getErrorCode());

        verify(jwtUtil, times(1)).extractUserIdFromToken(token);
        verify(tripRepository, times(1)).findById(tripId);
        verify(userRepository, times(1)).findById(userId);
        verify(transactionUtil, times(1)).setTransaction(trip, user, request);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(coordinator, times(1)).updateBudget(tripId, 3000, 3000, "추가");
    }

    @DisplayName("거래내역_저장_COORDINATOR_TRANSACTION_INVALID_TYPE")
    @Test
    void saveTransactionFromTrip_COORDINATOR_TRANSACTION_INVALID_TYPE() {
        // Given
        long userId = 1L;
        long tripId = 1L;
        User user = new User();
        Trip trip = new Trip();
        String token = jwtTokenProvider.generateToken(userId);

        RequestTransactionAddDto request = new RequestTransactionAddDto(
                tripId, 3000, "무야호",
                LocalDate.of(2024, 10, 27), "테스트", 3000, 3000, "관광");

        Transaction mockTransaction = new Transaction();
        when(jwtUtil.extractUserIdFromToken(token)).thenReturn(userId);
        when(tripRepository.findById(tripId)).thenReturn(Optional.of(trip));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(transactionUtil.setTransaction(trip, user, request)).thenReturn(mockTransaction);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(mockTransaction);
        doThrow(new GabojagoException(ErrorCode.TRANSACTION_INVALID_TYPE)).when(coordinator).updateBudget(tripId, 3000, 3000, request.getTransactionType());

        // When
        GabojagoException exception = assertThrows(GabojagoException.class,
                () -> transactionService.saveTransactionFromTrip(token, request));

        // Then
        assertEquals(ErrorCode.TRANSACTION_INVALID_TYPE.getErrorCode(), exception.getErrorCode());

        verify(jwtUtil, times(1)).extractUserIdFromToken(token);
        verify(tripRepository, times(1)).findById(tripId);
        verify(userRepository, times(1)).findById(userId);
        verify(transactionUtil, times(1)).setTransaction(trip, user, request);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(coordinator, times(1)).updateBudget(anyLong(), anyInt(), anyInt(), anyString());
    }

    @DisplayName("소비내역_총합_불러오기_테스트")
    @Test
    void getSum() {
        //Given
        Long tripId = 1L;
        Long expected = 139974L;

        when(transactionRepository.findSumByTripId(tripId)).thenReturn(expected);

        //When
        long response = transactionService.getSum(tripId);

        //Then
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response).isEqualTo(expected);
        verify(transactionRepository, times(1)).findSumByTripId(tripId);
    }

    @DisplayName("여행아이디_해당하는_거래내역_불러오기_테스트")
    @Test
    void getTripDetailTransaction() {
        //Given
        Long userId = 1L;
        Long tripId = 1L;
        LocalDate date = LocalDate.of(2024, 10, 27);
        String token = jwtTokenProvider.generateToken(userId);
        Transaction mockTransaction = new Transaction();
        when(jwtUtil.extractUserIdFromToken(token)).thenReturn(userId);
        when(transactionRepository.findAllByTripTripIdAndExpenseDate(tripId, date)).thenReturn(List.of(mockTransaction));

        //When
        List<Transaction> response = transactionRepository.findAllByTripTripIdAndExpenseDate(tripId, date);

        //Then
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response).hasSize(1);
        verify(transactionRepository, times(1)).findAllByTripTripIdAndExpenseDate(tripId, date);
    }

    @DisplayName("거래내역_삭제_테스트")
    @Test
    void deleteTransaction() {
        // Given
        Long userId = 1L;
        Long tripId = 1L;
        String token = jwtTokenProvider.generateToken(userId);
        RequestTransactionDeleteDto request = new RequestTransactionDeleteDto(new long[]{1L, 2L, 3L}, 1L);

        Trip mockTrip = new Trip();
        mockTrip.setTripId(tripId);

        User mockUser = new User();
        mockUser.setUserId(userId);

        Transaction transaction1 = new Transaction();
        transaction1.setTransactionId(1L);
        transaction1.setTrip(mockTrip);
        transaction1.setUser(mockUser);
        transaction1.setExpenseAmount(1000);
        transaction1.setExchangeAmount(500);
        transaction1.setTransactionType("지출");

        Transaction transaction2 = new Transaction();
        transaction2.setTransactionId(2L);
        transaction2.setTrip(mockTrip);
        transaction2.setUser(mockUser);
        transaction2.setExpenseAmount(2000);
        transaction2.setExchangeAmount(1000);
        transaction2.setTransactionType("추가");

        Transaction transaction3 = new Transaction();
        transaction3.setTransactionId(3L);
        transaction3.setTrip(mockTrip);
        transaction3.setUser(mockUser);
        transaction3.setExpenseAmount(800);
        transaction3.setExchangeAmount(400);
        transaction3.setTransactionType("지출");

        when(jwtUtil.extractUserIdFromToken(token)).thenReturn(userId);
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction1));
        when(transactionRepository.findById(2L)).thenReturn(Optional.of(transaction2));
        when(transactionRepository.findById(3L)).thenReturn(Optional.of(transaction3));
        when(tripRepository.findById(request.getTripId())).thenReturn(Optional.of(mockTrip));

        // When
        transactionService.deleteTransaction(token, request);

        // Then
        int expectedTripBudget = 1000 - 2000 + 800;
        int expectedExchangeTripBudget = 500 - 1000 + 400;
        String expectedTransactionType = expectedTripBudget >= 0 ? "추가" : "지출";
        expectedTripBudget = Math.abs(expectedTripBudget);
        expectedExchangeTripBudget = Math.abs(expectedExchangeTripBudget);

        verify(coordinator, times(1)).updateBudget(
                eq(request.getTripId()),
                eq(expectedTripBudget),
                eq(expectedExchangeTripBudget),
                eq(expectedTransactionType)
        );

        verify(transactionRepository, times(3)).findById(anyLong());
        verify(transactionRepository, times(3)).deleteById(anyLong());
    }

    @DisplayName("거래내역_삭제_NOT_FOUND_EXCEPTION")
    @Test
    void deleteTransaction_NOT_FOUND_EXCEPTION() {
        // Given
        Long userId = 1L;
        Long tripId = 1L;
        String token = jwtTokenProvider.generateToken(userId);
        RequestTransactionDeleteDto request = new RequestTransactionDeleteDto(new long[]{1L, 2L, 7L}, tripId);

        Trip mockTrip = new Trip();
        mockTrip.setTripId(tripId);

        User mockUser = new User();
        mockUser.setUserId(userId);

        Transaction transaction1 = new Transaction();
        transaction1.setTransactionId(1L);
        transaction1.setTrip(mockTrip);
        transaction1.setUser(mockUser);
        transaction1.setExpenseAmount(1000);
        transaction1.setExchangeAmount(500);
        transaction1.setTransactionType("지출");

        Transaction transaction2 = new Transaction();
        transaction2.setTransactionId(2L);
        transaction2.setTrip(mockTrip);
        transaction2.setUser(mockUser);
        transaction2.setExpenseAmount(2000);
        transaction2.setExchangeAmount(1000);
        transaction2.setTransactionType("추가");

        when(jwtUtil.extractUserIdFromToken(token)).thenReturn(userId);
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction1));
        when(transactionRepository.findById(2L)).thenReturn(Optional.of(transaction2));
        when(transactionRepository.findById(7L)).thenThrow(new GabojagoException(ErrorCode.TRANSACTION_NOT_FOUND));
        when(tripRepository.findById(request.getTripId())).thenReturn(Optional.of(mockTrip));

        // When
        GabojagoException exception = assertThrows(GabojagoException.class, () -> {
            transactionService.deleteTransaction(token, request);
        });


        // Then
        assertEquals(ErrorCode.TRANSACTION_NOT_FOUND.getErrorCode(), exception.getErrorCode());

        verify(transactionRepository, times(3)).findById(anyLong());
        verify(transactionRepository, times(1)).deleteById(1L);
        verify(transactionRepository, times(1)).deleteById(2L);
        verify(transactionRepository, times(0)).deleteById(7L);
        verify(coordinator, times(0)).updateBudget(anyLong(), anyInt(), anyInt(), anyString());
    }


}
