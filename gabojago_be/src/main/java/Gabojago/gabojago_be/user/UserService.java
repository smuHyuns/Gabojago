package Gabojago.gabojago_be.user;

import Gabojago.gabojago_be.dto.request.RequestLoginDto;
import Gabojago.gabojago_be.dto.request.RequestSignUpDto;
import Gabojago.gabojago_be.dto.response.ResponseLoginDto;
import Gabojago.gabojago_be.entity.User;
import Gabojago.gabojago_be.exception.InvalidCredentialsException;
import Gabojago.gabojago_be.jwt.JwtTokenProvider;
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

    @Value("${default.profile.image:none}")
    private String defaultProfileImage;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByLoginId(String userLoginId) {
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

}
