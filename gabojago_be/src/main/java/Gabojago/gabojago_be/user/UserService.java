package Gabojago.gabojago_be.user;

import Gabojago.gabojago_be.dto.request.RequestEmailAuthDto;
import Gabojago.gabojago_be.dto.request.RequestLoginDto;
import Gabojago.gabojago_be.dto.request.RequestProfileDto;
import Gabojago.gabojago_be.dto.request.RequestSignUpDto;
import Gabojago.gabojago_be.dto.response.ResponseEmailAuthDto;
import Gabojago.gabojago_be.dto.response.ResponseFindIdDto;
import Gabojago.gabojago_be.dto.response.ResponseLoginDto;
import Gabojago.gabojago_be.dto.response.ResponseProfileDto;
import Gabojago.gabojago_be.entity.User;
import Gabojago.gabojago_be.exception.ErrorCode;
import Gabojago.gabojago_be.exception.GabojagoException;
import Gabojago.gabojago_be.jwt.JwtTokenProvider;
import Gabojago.gabojago_be.jwt.JwtUtil;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserUtilService userUtilService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final JwtUtil jwtUtil;


    @Value("${default.profile.image:none}")
    private String defaultProfileImage;

    public Optional<User> getUserByUserId(Long userId) {
        return userRepository.findById(userId);
    }

    public User addUser(RequestSignUpDto dto) {
        User user = userUtilService.setUser(dto);
        return userRepository.save(user);
    }

    public boolean isDupUserLoginId(String userLoginId) {
        return userRepository.existsByUserLoginId(userLoginId);
    }

    public boolean isDupUserNickname(String userNickname) {
        return userRepository.existsByUserNickname(userNickname);
    }

    public ResponseLoginDto login(RequestLoginDto dto) {
        ResponseLoginDto response = new ResponseLoginDto();

        User user = userRepository.findByUserLoginId(dto.getUserLoginId())
                .orElseThrow(() -> new GabojagoException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(dto.getUserPassword(), user.getUserPassword())) {
            throw new GabojagoException(ErrorCode.USER_INVALID_LOGIN);
        }

        String token = jwtTokenProvider.generateToken(user.getUserId());
        response.setToken(token);
        return response;
    }

    public ResponseProfileDto getProfile(String token) {
        Long userId = jwtUtil.extractUserIdFromToken(token);
        User user = userRepository.findById(userId).orElseThrow();
        ResponseProfileDto response = userUtilService.setProfileDto(user);
        return response;
    }

    @Transactional
    public void updateUser(String token, RequestProfileDto dto) {
        Long userId = jwtUtil.extractUserIdFromToken(token);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GabojagoException(ErrorCode.USER_NOT_FOUND));

        if (dto.getUserPassword() != null) {
            String password = dto.getUserPassword();
            password = passwordEncoder.encode(password);
            user.setUserPassword(password);
        }

        if (dto.isImgChanged()) {
            userUtilService.deleteExistingProfileImg(user.getUserProfileImg());
        }
        user.setUserProfileImg(dto.getUserProfileImg());
        user.setUserNickname(dto.getUserNickname());
    }

    public ResponseEmailAuthDto sendEmail(RequestEmailAuthDto request) {
        String authenticateNumber = mailService.mailSend(request.getEmail());
        ResponseEmailAuthDto response = new ResponseEmailAuthDto();
        response.setAuthenticateNumber(authenticateNumber);
        return response;
    }

    public ResponseFindIdDto findId(String email, String username) {
        User user = userRepository.findUserByUserEmailAndUserUsername(email, username)
                .orElseThrow(() -> new GabojagoException(ErrorCode.USER_INVALID_USERNAME_EMAIL));
        return new ResponseFindIdDto(user.getUserLoginId());
    }

}
