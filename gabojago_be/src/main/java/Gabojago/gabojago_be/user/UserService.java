package Gabojago.gabojago_be.user;

import Gabojago.gabojago_be.dto.request.RequestLoginDto;
import Gabojago.gabojago_be.dto.request.RequestProfileDto;
import Gabojago.gabojago_be.dto.request.RequestSignUpDto;
import Gabojago.gabojago_be.dto.response.ResponseLoginDto;
import Gabojago.gabojago_be.dto.response.ResponseProfileDto;
import Gabojago.gabojago_be.entity.User;
import Gabojago.gabojago_be.exception.InvalidCredentialsException;
import Gabojago.gabojago_be.file.S3FileServiceImpl;
import Gabojago.gabojago_be.jwt.JwtTokenProvider;
import Gabojago.gabojago_be.jwt.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserUtilService userUtilService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Value("${default.profile.image:none}")
    private String defaultProfileImage;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByUserId(Long userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> getUserByUserLoginId(String userLoginId) {
        return userRepository.findByUserLoginId(userLoginId);
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
                .orElseThrow(() -> new InvalidCredentialsException("존재하지 않는 아이디 입니다."));

        if (!passwordEncoder.matches(dto.getUserPassword(), user.getUserPassword())) {
            throw new InvalidCredentialsException("아이디와 비밀번호가 일치하지 않습니다.");
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
                .orElseThrow(() -> new IllegalArgumentException("유저가 발견되지 않았습니다."));

        //패스워드 암호화 후 업데이트
        String password = dto.getUserPassword();
        password = passwordEncoder.encode(password);

        if(dto.isImgChanged()){
            userUtilService.deleteExistingProfileImg(user.getUserProfileImg());
        }
        user.setUserProfileImg(dto.getUserProfileImg());

        user.setUserPassword(password);
        user.setUserNickname(dto.getUserNickname());
    }

    public boolean checkUserProfileImg(String token, String userProfileImg) {
        Long userId = jwtUtil.extractUserIdFromToken(token);
        User user = userRepository.findById(userId).orElseThrow();
        return user.getUserProfileImg().equals(userProfileImg);
    }

    public String getUserProfileImg(String token) {
        Long userId = jwtUtil.extractUserIdFromToken(token);
        User user = userRepository.findById(userId).orElseThrow();
        return user.getUserProfileImg();
    }
}
