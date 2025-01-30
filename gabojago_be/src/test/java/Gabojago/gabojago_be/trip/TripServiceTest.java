package Gabojago.gabojago_be.trip;

import Gabojago.gabojago_be.dto.request.RequestTripSaveDto;
import Gabojago.gabojago_be.dto.response.ResponseExchangeRateDto;
import Gabojago.gabojago_be.dto.response.ResponseTripDetailEntireDto;
import Gabojago.gabojago_be.dto.response.ResponseTripDto;
import Gabojago.gabojago_be.dto.response.ResponseTripSaveDto;
import Gabojago.gabojago_be.entity.ExchangeRate;
import Gabojago.gabojago_be.entity.Trip;
import Gabojago.gabojago_be.entity.User;
import Gabojago.gabojago_be.exception.ErrorCode;
import Gabojago.gabojago_be.exception.GabojagoException;
import Gabojago.gabojago_be.exchangeRate.ExchangeRateService;
import Gabojago.gabojago_be.jwt.JwtTokenProvider;
import Gabojago.gabojago_be.jwt.JwtUtil;
import Gabojago.gabojago_be.transaction.TransactionService;
import Gabojago.gabojago_be.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@Slf4j
public class TripServiceTest {

    @Mock
    private TripRepository tripRepository;

    @Mock
    private TransactionService transactionService;

    @Mock
    private ExchangeRateService exchangeRateService;

    @Mock
    private TripUtilService tripUtilService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private TripService tripService;

    @Captor
    private ArgumentCaptor<Pageable> pageableCaptor;


    @Mock
    private TripService mockTripService;

    public TripServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("나라조회")
    @Test
    void getCountry() {
        //Given
        long tripId = 1L;
        Trip mockTrip = new Trip();

        when(tripRepository.findByTripId(tripId)).thenReturn(mockTrip);

        //When
        Trip response = tripRepository.findByTripId(tripId);

        //Then
        Assertions.assertThat(response).isEqualTo(mockTrip);
        verify(tripRepository, times(1)).findByTripId(tripId);
    }

    @DisplayName("여행불러오기")
    @Test
    void getTrips() {
        // Given
        Trip mockTrip = new Trip();
        User mockUser = new User();
        mockUser.setUserId(1L);
        mockTrip.setTripId(1L);
        mockTrip.setStartPeriod(LocalDate.of(2023, 1, 1));
        mockTrip.setEndPeriod(LocalDate.of(2023, 1, 10));
        mockTrip.setDescription("테스트 여행");
        mockTrip.setTripBudget(5000000);
        mockTrip.setTripCountry("Korea");
        mockTrip.setTripExchangeBudget(4500);
        mockTrip.setHeadcount(4);
        mockTrip.setTripStatus(1);
        mockTrip.setUser(mockUser);
        String token = jwtTokenProvider.generateToken(mockUser.getUserId());

        when(jwtUtil.extractUserIdFromToken(token)).thenReturn(mockUser.getUserId());
        when(tripRepository.findByUserUserIdOrderByStartPeriodAscEndPeriodAsc(mockUser.getUserId())).thenReturn(List.of(mockTrip));

        List<ResponseTripDto> expectedResponse = List.of(new ResponseTripDto(
                mockTrip.getTripId(),
                mockTrip.getTripCountry(),
                mockTrip.getStartPeriod(),
                mockTrip.getEndPeriod(),
                mockTrip.getTripStatus(),
                mockTrip.getDescription()
        ));

        // When
        List<ResponseTripDto> response = tripService.getTrips(token);

        // Then
        Assertions.assertThat(response).isEqualTo(expectedResponse);
        verify(jwtUtil, times(1)).extractUserIdFromToken(token);
        verify(tripRepository, times(1)).findByUserUserIdOrderByStartPeriodAscEndPeriodAsc(mockUser.getUserId());
    }

    @DisplayName("여행불러오기_USER_NOT_FOUND")
    @Test
    void getTrips_USER_NOT_FOUND() {
        // Given
        long userId = 1L;
        String token = jwtTokenProvider.generateToken(userId);
        when(jwtUtil.extractUserIdFromToken(token)).thenReturn(null);

        // When
        GabojagoException exception = assertThrows(GabojagoException.class, () -> tripService.getTrips(token));

        // Then
        assertEquals(ErrorCode.USER_NOT_FOUND.getErrorCode(), exception.getErrorCode());
        verify(jwtUtil, times(1)).extractUserIdFromToken(token);
        verify(tripRepository, times(0)).findByUserUserIdOrderByStartPeriodAscEndPeriodAsc(anyLong());
    }

    @DisplayName("여행상태별_여행불러오기")
    @Test
    void getTripsByStatus() {
        // Given
        Trip mockTrip = new Trip();
        User mockUser = new User();
        mockUser.setUserId(1L);
        mockTrip.setTripId(1L);
        mockTrip.setStartPeriod(LocalDate.of(2023, 1, 1));
        mockTrip.setEndPeriod(LocalDate.of(2023, 1, 10));
        mockTrip.setDescription("테스트 여행");
        mockTrip.setTripBudget(5000000);
        mockTrip.setTripCountry("Korea");
        mockTrip.setTripExchangeBudget(4500);
        mockTrip.setHeadcount(4);
        mockTrip.setTripStatus(1);
        mockTrip.setUser(mockUser);

        String token = jwtTokenProvider.generateToken(mockUser.getUserId());
        when(jwtUtil.extractUserIdFromToken(token)).thenReturn(mockUser.getUserId());
        when(tripRepository.findByUserUserIdOrderByStartPeriodAscEndPeriodAsc(mockUser.getUserId())).thenReturn(List.of(mockTrip));

        //When
        List<ResponseTripDto> expectedResponse = List.of(new ResponseTripDto(
                mockTrip.getTripId(),
                mockTrip.getTripCountry(),
                mockTrip.getStartPeriod(),
                mockTrip.getEndPeriod(),
                mockTrip.getTripStatus(),
                mockTrip.getDescription()
        ));

        //Then
        Assertions.assertThat(expectedResponse).isEqualTo(tripService.getTrips(token));
        verify(jwtUtil, times(1)).extractUserIdFromToken(token);
        verify(tripRepository, times(1)).findByUserUserIdOrderByStartPeriodAscEndPeriodAsc(anyLong());
    }

    @DisplayName("여행상태별_여행불러오기_TRIP_INVALID_TRIPSTATUS")
    @Test
    void getTripsByStatus_TRIP_INVALID_TRIPSTATUS() {
        //Given
        long userId = 1L;
        String token = jwtTokenProvider.generateToken(userId);
        int status = 4;

        when(jwtUtil.extractUserIdFromToken(token)).thenReturn(userId);

        //When
        GabojagoException exception = assertThrows(GabojagoException.class, () -> tripService.getTripsByStatus(token, status));

        //Then
        Assertions.assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.TRIP_INVALID_TRIPSTATUS.getErrorCode());
        verify(jwtUtil, times(1)).extractUserIdFromToken(token);
    }

    @DisplayName("여행상세_불러오기")
    @Test
    void getDetail() {
        //Given
        long tripId = 1L;
        Trip mockTrip = new Trip();

        mockTrip.setTripBudget(0);
        mockTrip.setDescription("테스트");
        mockTrip.setTripId(tripId);
        mockTrip.setStartPeriod(LocalDate.of(2023, 1, 1));
        mockTrip.setEndPeriod(LocalDate.of(2023, 1, 10));

        when(transactionService.getSum(tripId)).thenReturn(3L);
        when(tripRepository.findById(tripId)).thenReturn(Optional.of(mockTrip));

        ResponseTripDetailEntireDto expectedResponse = new ResponseTripDetailEntireDto();
        expectedResponse.setTripBudget(0L);
        expectedResponse.setDescription("테스트");
        expectedResponse.setStartPeriod(LocalDate.of(2023, 1, 1));
        expectedResponse.setEndPeriod(LocalDate.of(2023, 1, 10));
        expectedResponse.setTotalExpense(3L);

        //When
        ResponseTripDetailEntireDto response = tripService.getDetail(tripId);

        //Then
        Assertions.assertThat(response).isEqualTo(expectedResponse);
        verify(tripRepository, times(1)).findById(tripId);
        verify(transactionService, times(1)).getSum(tripId);
    }

    @DisplayName("여행상세_불러오기_TRIP_NOT_FOUND")
    @Test
    void getDetail_TRIP_NOT_FOUND() {
        //Given
        long tripId = 1L;

        when(transactionService.getSum(tripId)).thenReturn(3L);
        when(tripRepository.findById(tripId)).thenThrow(new GabojagoException(ErrorCode.TRIP_NOT_FOUND));

        //When
        GabojagoException exception = assertThrows(GabojagoException.class, () -> tripService.getDetail(tripId));

        //Then
        Assertions.assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.TRIP_NOT_FOUND.getErrorCode());
        verify(tripRepository, times(1)).findById(tripId);
    }

    @DisplayName("여행아이디_해당_국가_환율_불러오기")
    @Test
    void getExchange() {
        // Given
        long tripId = 1L;
        String token = jwtTokenProvider.generateToken(1L);
        Double rate = 1.0;
        String currency = "KOR";
        String country = "South Korea";

        Trip mockTrip = new Trip();
        mockTrip.setTripId(tripId);
        mockTrip.setTripCountry(country);

        ExchangeRate mockExchangeRate = new ExchangeRate();
        mockExchangeRate.setCurrency(currency);
        mockExchangeRate.setCountry(country);
        mockExchangeRate.setRate(rate);

        ResponseExchangeRateDto expectedDto = new ResponseExchangeRateDto();
        expectedDto.setCurrency(currency);
        expectedDto.setRate(rate);

        when(tripRepository.findByTripId(tripId)).thenReturn(mockTrip);
        when(exchangeRateService.getExchangeRateByCountry(country)).thenReturn(Optional.of(mockExchangeRate));
        // When
        ResponseExchangeRateDto response = tripService.getExchange(token, tripId);

        // Then
        Assertions.assertThat(response).isEqualTo(expectedDto);
        verify(tripRepository, times(1)).findByTripId(tripId);
        verify(exchangeRateService, times(1)).getExchangeRateByCountry(country);
    }

    @DisplayName("여행_저장하기")
    @Test
    void saveTrip() {
        // Given
        String token = jwtTokenProvider.generateToken(1L);
        String country = "South Korea";
        int headcount = 2;
        LocalDate startPeriod = LocalDate.of(2023, 1, 1);
        LocalDate endPeriod = LocalDate.of(2023, 1, 10);
        String description = "test";

        User mockUser = new User();
        mockUser.setUserId(1L);

        RequestTripSaveDto mockRequestTripSaveDto = new RequestTripSaveDto();
        mockRequestTripSaveDto.setStartPeriod(startPeriod);
        mockRequestTripSaveDto.setEndPeriod(endPeriod);
        mockRequestTripSaveDto.setHeadcount(headcount);
        mockRequestTripSaveDto.setCountry(country);
        mockRequestTripSaveDto.setDescription(description);

        Trip mockTrip = new Trip();
        mockTrip.setTripId(1L);

        ResponseTripSaveDto expectedDto = new ResponseTripSaveDto(1L);

        when(jwtUtil.extractUserIdFromToken(token)).thenReturn(1L);
        when(userService.getUserByUserId(1L)).thenReturn(Optional.of(mockUser));
        when(tripUtilService.calculateTripStatus(startPeriod, endPeriod)).thenReturn(2);
        when(tripRepository.save(any(Trip.class))).thenReturn(mockTrip);

        // When
        ResponseTripSaveDto response = tripService.saveTrip(token, mockRequestTripSaveDto);

        // Then
        Assertions.assertThat(response).isEqualTo(expectedDto);
        verify(jwtUtil, times(1)).extractUserIdFromToken(token);
        verify(userService, times(1)).getUserByUserId(1L);
        verify(tripRepository, times(1)).save(any(Trip.class));
    }

    @DisplayName("여행_저장하기_USER_NOT_FOUND")
    @Test
    void saveTrip_USER_NOT_FOUND() {
        //Given
        String token = jwtTokenProvider.generateToken(1L);
        RequestTripSaveDto mockRequestTripSaveDto = new RequestTripSaveDto();

        when(jwtUtil.extractUserIdFromToken(token)).thenReturn(1L);
        when(userService.getUserByUserId(1L)).thenThrow(new GabojagoException(ErrorCode.USER_NOT_FOUND));

        //When
        GabojagoException exception = assertThrows(GabojagoException.class, () -> tripService.saveTrip(token, mockRequestTripSaveDto));

        //Then
        Assertions.assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.USER_NOT_FOUND.getErrorCode());
        verify(jwtUtil, times(1)).extractUserIdFromToken(token);
        verify(userService, times(1)).getUserByUserId(1L);
    }

    @DisplayName("여행_저장하기_TRIP_INVALID_FORMAT")
    @Test
    void saveTrip_TRIP_INVALID_FORMAT() {
        //Given
        String token = jwtTokenProvider.generateToken(1L);
        RequestTripSaveDto mockRequestTripSaveDto = new RequestTripSaveDto();
        User mockUser = new User();

        when(jwtUtil.extractUserIdFromToken(token)).thenReturn(1L);
        when(userService.getUserByUserId(1L)).thenReturn(Optional.of(mockUser));

        //When
        GabojagoException exception = assertThrows(GabojagoException.class, () -> tripService.saveTrip(token, mockRequestTripSaveDto));

        //Then
        Assertions.assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.TRIP_INVALID_FORMAT.getErrorCode());
        verify(jwtUtil, times(1)).extractUserIdFromToken(token);
        verify(userService, times(1)).getUserByUserId(1L);
    }

//    @DisplayName("여행상태_페이지별_업데이트")
//    @Test
//    void updateTripStatus() {
//        // Given
//        int page = 0;
//        int size = 100;
//
//        Trip trip1 = new Trip();
//        trip1.setTripId(1L);
//        trip1.setStartPeriod(LocalDate.of(2023, 1, 1));
//        trip1.setEndPeriod(LocalDate.of(2023, 1, 10));
//        trip1.setTripStatus(1);
//
//        Trip trip2 = new Trip();
//        trip2.setTripId(2L);
//        trip2.setStartPeriod(LocalDate.of(2023, 2, 1));
//        trip2.setEndPeriod(LocalDate.of(2023, 2, 10));
//        trip2.setTripStatus(1);
//
//        List<Trip> tripsPage1 = List.of(trip1, trip2);
//        Page<Trip> tripPage1 = new PageImpl<>(tripsPage1, PageRequest.of(page, size), 200);
//
//        List<Trip> tripsPage2 = List.of();
//        Page<Trip> tripPage2 = new PageImpl<>(tripsPage2, PageRequest.of(page + 1, size), 200);
//
//        when(tripRepository.findAll(PageRequest.of(0, 100))).thenReturn(tripPage1);
//        when(tripRepository.findAll(PageRequest.of(1, 100))).thenReturn(tripPage2);
//        when(tripUtilService.calculateTripStatus(any(), any())).thenReturn(2);
//
//        // When
//        tripService.updateTripStatus();
//
//        // Then
//        verify(tripRepository, times(2)).findAll(pageableCaptor.capture());
//        List<Pageable> capturedPageables = pageableCaptor.getAllValues();
//        Assertions.assertThat(capturedPageables.get(0).getPageNumber()).isEqualTo(0);
//        Assertions.assertThat(capturedPageables.get(1).getPageNumber()).isEqualTo(1);
//        verify(tripRepository, times(2)).save(any(Trip.class));
//    }

    @DisplayName("여행상태_업데이트_테스트")
    @Test
    void updateTrips() {
        // Given
        Trip trip1 = new Trip();
        trip1.setTripId(1L);
        trip1.setStartPeriod(LocalDate.of(2023, 1, 1));
        trip1.setEndPeriod(LocalDate.of(2023, 1, 10));
        trip1.setTripStatus(1);

        Trip trip2 = new Trip();
        trip2.setTripId(2L);
        trip2.setStartPeriod(LocalDate.of(2023, 2, 1));
        trip2.setEndPeriod(LocalDate.of(2023, 2, 10));
        trip2.setTripStatus(2);

        List<Trip> trips = List.of(trip1, trip2);

        when(tripUtilService.calculateTripStatus(trip1.getStartPeriod(), trip1.getEndPeriod())).thenReturn(2);
        when(tripUtilService.calculateTripStatus(trip2.getStartPeriod(), trip2.getEndPeriod())).thenReturn(2);

        when(tripRepository.save(trip1)).thenReturn(trip1);
        when(tripRepository.save(trip2)).thenReturn(trip2);

        // When
        int updatedCount = tripService.updateTrips(trips);

        // Then
        Assertions.assertThat(updatedCount).isEqualTo(1);
        Assertions.assertThat(trip1.getTripStatus()).isEqualTo(2);
        Assertions.assertThat(trip2.getTripStatus()).isEqualTo(2);
        verify(tripUtilService, times(1)).calculateTripStatus(trip1.getStartPeriod(), trip1.getEndPeriod());
        verify(tripUtilService, times(1)).calculateTripStatus(trip2.getStartPeriod(), trip2.getEndPeriod());
        verify(tripRepository, times(1)).save(trip1);
        verify(tripRepository, times(0)).save(trip2);
    }

}
