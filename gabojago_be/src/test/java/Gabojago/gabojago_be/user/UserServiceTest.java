package Gabojago.gabojago_be.user;

import Gabojago.gabojago_be.dto.request.RequestLoginDto;
import Gabojago.gabojago_be.dto.request.RequestProfileDto;
import Gabojago.gabojago_be.dto.request.RequestSignUpDto;
import Gabojago.gabojago_be.dto.response.ResponseLoginDto;
import Gabojago.gabojago_be.dto.response.ResponseProfileDto;
import Gabojago.gabojago_be.entity.User;
import Gabojago.gabojago_be.exception.ErrorCode;
import Gabojago.gabojago_be.exception.GabojagoException;
import Gabojago.gabojago_be.jwt.JwtTokenProvider;
import Gabojago.gabojago_be.jwt.JwtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserUtilService userUtilService;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    public UserServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("유저아이디로_유저찾기")
    @Test
    void getUserByUserId() {
        //Given
        long userId = 1L;
        User mockUser = new User();
        mockUser.setUserId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        //When

        Optional<User> response = userService.getUserByUserId(userId);
        //Then
        assertTrue(response.isPresent());
        assertEquals(mockUser.getUserId(), response.get().getUserId());
        verify(userRepository, times(1)).findById(anyLong());
    }

    @DisplayName("유저_추가하기")
    @Test
    void addUser() {
        //Given
        RequestSignUpDto request = new RequestSignUpDto();
        request.setUserNickname("test");
        request.setUserEmail("test@gmail.com");
        request.setUserPassword("test1234!");
        request.setUserLoginId("test1234");
        request.setUserUsername("테스트");
        request.setUserGender(0);
        request.setUserBirth(LocalDate.of(2024, 12, 30));

        User expectedUser = new User();
        expectedUser.setUserId(1L);
        expectedUser.setUserNickname("test");
        expectedUser.setUserEmail("test@gmail.com");
        expectedUser.setUserPassword("test1234!");
        expectedUser.setUserLoginId("test1234");
        expectedUser.setUserUsername("테스트");
        expectedUser.setUserGender(0);
        expectedUser.setUserBirth(LocalDate.of(2024, 12, 30));

        when(userUtilService.setUser(request)).thenReturn(expectedUser);
        when(userRepository.save(expectedUser)).thenReturn(expectedUser);

        //When
        User response = userService.addUser(request);

        //Then
        Assertions.assertThat(response).isEqualTo(expectedUser);
        verify(userUtilService, times(1)).setUser(request);
        verify(userRepository, times(1)).save(expectedUser);
    }


    @DisplayName("로그인아이디_중복_확인")
    @Test
    void isDupUserLoginId() {
        //Given
        String userLoginId = "test1234";
        when(userRepository.existsByUserLoginId(userLoginId)).thenReturn(true);
        //When
        boolean response = userService.isDupUserLoginId(userLoginId);
        //Then
        assertTrue(response);
        verify(userRepository, times(1)).existsByUserLoginId(userLoginId);
    }

    @DisplayName("닉네임_중복_확인")
    @Test
    void isDupUserNickname() {
        //Given
        String userNickname = "test";
        when(userRepository.existsByUserNickname(userNickname)).thenReturn(true);
        //When
        boolean response = userService.isDupUserNickname(userNickname);
        //Then
        assertTrue(response);
        verify(userRepository, times(1)).existsByUserNickname(userNickname);
    }

    @DisplayName("로그인_확인")
    @Test
    void login() {
        //Given
        RequestLoginDto request = new RequestLoginDto();
        request.setUserLoginId("test1234");
        request.setUserPassword("test1234!");

        User expectedUser = new User();
        expectedUser.setUserId(1L);
        expectedUser.setUserLoginId("test1234");
        expectedUser.setUserPassword("test1234!");

        String mockToken = "token";

        ResponseLoginDto expectedResponse = new ResponseLoginDto();
        expectedResponse.setToken(mockToken);

        when(userRepository.findByUserLoginId(request.getUserLoginId())).thenReturn(Optional.of(expectedUser));
        when(passwordEncoder.matches(request.getUserPassword(), expectedUser.getUserPassword())).thenReturn(true);
        when(jwtTokenProvider.generateToken(expectedUser.getUserId())).thenReturn(mockToken);

        //When
        ResponseLoginDto response = userService.login(request);

        //Then
        Assertions.assertThat(response).isEqualTo(expectedResponse);
        verify(userRepository, times(1)).findByUserLoginId(request.getUserLoginId());
        verify(jwtTokenProvider, times(1)).generateToken(expectedUser.getUserId());
        verify(passwordEncoder, times(1)).matches(request.getUserPassword(), expectedUser.getUserPassword());
    }

    @DisplayName("로그인_확인_USER_NOT_FOUND")
    @Test
    void login_USER_NOT_FOUND() {
        //Given
        RequestLoginDto request = new RequestLoginDto();
        request.setUserLoginId("test1234");

        when(userRepository.findByUserLoginId(request.getUserLoginId())).thenThrow(new GabojagoException(ErrorCode.USER_NOT_FOUND));
        //When
        GabojagoException exception = assertThrows(GabojagoException.class, () -> userService.login(request));
        //Then
        Assertions.assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.USER_NOT_FOUND.getErrorCode());
        verify(userRepository, times(1)).findByUserLoginId(request.getUserLoginId());
    }

    @DisplayName("로그인_확인_USER_INVALID_LOGIN")
    @Test
    void login_USER_INVALID_LOGIN() {
        //Given
        RequestLoginDto request = new RequestLoginDto();
        request.setUserLoginId("test1234");
        request.setUserPassword("test1234!");

        User expectedUser = new User();
        expectedUser.setUserId(1L);
        expectedUser.setUserLoginId("test1234");
        expectedUser.setUserPassword("test12345");

        ResponseLoginDto expectedResponse = new ResponseLoginDto();

        when(userRepository.findByUserLoginId(request.getUserLoginId())).thenReturn(Optional.of(expectedUser));
        when(passwordEncoder.matches(request.getUserPassword(), expectedUser.getUserPassword())).thenReturn(false);

        //When
        GabojagoException exception = assertThrows(GabojagoException.class, () -> userService.login(request));

        //Then
        Assertions.assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.USER_INVALID_LOGIN.getErrorCode());
        verify(userRepository, times(1)).findByUserLoginId(request.getUserLoginId());
        verify(passwordEncoder, times(1)).matches(request.getUserPassword(), expectedUser.getUserPassword());
    }


    @DisplayName("프로필_가져오기")
    @Test
    void getProfile() {
        //Given
        String token = "token";
        long userId = 1L;
        User user = new User();
        user.setUserNickname("test");
        user.setUserEmail("test@gmail.com");
        user.setUserPassword("test1234!");
        user.setUserProfileImg("img");
        user.setUserUsername("test");

        ResponseProfileDto expectedResponse = new ResponseProfileDto();
        expectedResponse.setUserUsername(user.getUserUsername());
        expectedResponse.setUserPassword(user.getUserPassword());
        expectedResponse.setUserNickname(user.getUserNickname());
        expectedResponse.setUserProfileImg(user.getUserProfileImg());
        expectedResponse.setUserEmail(user.getUserEmail());

        when(jwtUtil.extractUserIdFromToken(token)).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userUtilService.setProfileDto(user)).thenReturn(expectedResponse);

        //When
        ResponseProfileDto response = userService.getProfile(token);

        //Then
        Assertions.assertThat(response).isEqualTo(expectedResponse);
        verify(userRepository, times(1)).findById(userId);
        verify(jwtUtil, times(1)).extractUserIdFromToken(token);
        verify(userUtilService, times(1)).setProfileDto(user);
    }

    @DisplayName("유저_업데이트")
    @Test
    void updateUser() {
        //Given
        String token = "token";
        RequestProfileDto request = new RequestProfileDto();
        request.setUserNickname("test");
        request.setUserEmail("test@gmail.com");
        request.setUserPassword("test1234!");
        request.setUserProfileImg("img");
        request.setImgChanged(true);

        long userId = 1L;
        User user = new User();

        User expectedUser = new User();
        expectedUser.setUserNickname(request.getUserNickname());
        expectedUser.setUserPassword(request.getUserPassword());
        expectedUser.setUserEmail(request.getUserEmail());
        expectedUser.setUserProfileImg(request.getUserProfileImg());

        when(jwtUtil.extractUserIdFromToken(token)).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        //When
        userService.updateUser(token, request);

        //Then
        verify(userRepository, times(1)).findById(userId);
        verify(jwtUtil, times(1)).extractUserIdFromToken(token);
        Assertions.assertThat(expectedUser.getUserNickname()).isEqualTo(request.getUserNickname());
        Assertions.assertThat(expectedUser.getUserPassword()).isEqualTo(request.getUserPassword());
        Assertions.assertThat(expectedUser.getUserEmail()).isEqualTo(request.getUserEmail());
        Assertions.assertThat(expectedUser.getUserProfileImg()).isEqualTo(request.getUserProfileImg());
    }

    @DisplayName("유저_업데이트_USER_NOT_FOUND")
    @Test
    void updateUser_USER_NOT_FOUND() {
        //Given
        long userId = 1L;
        String token = "token";
        RequestProfileDto request = new RequestProfileDto();
        when(jwtUtil.extractUserIdFromToken(token)).thenReturn(userId);
        when(userRepository.findById(userId)).thenThrow(new GabojagoException(ErrorCode.USER_NOT_FOUND));
        //When
        GabojagoException exception = assertThrows(GabojagoException.class, () -> userService.updateUser(token, request));
        //Then
        Assertions.assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.USER_NOT_FOUND.getErrorCode());
        verify(userRepository, times(1)).findById(userId);
        verify(jwtUtil, times(1)).extractUserIdFromToken(token);
    }
}