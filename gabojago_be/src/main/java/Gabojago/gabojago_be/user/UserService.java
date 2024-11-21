package Gabojago.gabojago_be.service;

import Gabojago.gabojago_be.dto.request.RequestSignUpDto;
import Gabojago.gabojago_be.entity.User;
import Gabojago.gabojago_be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Value("${default.profile.image:none}")
    private String defaultProfileImage;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User addUser(RequestSignUpDto dto) {
        User user = new User();
        user.setUserNickname(dto.getUserNickname());
        user.setUserPassword(dto.getUserPassword());
        user.setUserLoginId(dto.getUserLoginId());
        user.setUserProfileImg(defaultProfileImage);
        return userRepository.save(user);
    }

    public String isDupUserNickname(String nickname) {
        boolean
        String msg = "사용 가능한 아이디 입니다.";
        if ()) {
            msg = "이미 존재하는 아이디입니다.";
        }
        return msg;
    }
}
