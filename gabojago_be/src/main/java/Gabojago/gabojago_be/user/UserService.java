package Gabojago.gabojago_be.user;

import Gabojago.gabojago_be.dto.request.RequestSignUpDto;
import Gabojago.gabojago_be.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserUtilService userUtilService;

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
        user.setUserEmail(dto.getUserEmail());
        user.setUserUsername(dto.getUserUsername());
        user.setUserBirth(userUtilService.stringToDate(dto.getUserBirth()));
        user.setUserGender(dto.getUserGender());
        return userRepository.save(user);
    }

    public boolean isDupUserLoginId(String userLoginId) {
        return userRepository.existsByUserLoginId(userLoginId);
    }

    public boolean isDupUserNickname(String userNickname) {
        return userRepository.existsByUserNickname(userNickname);
    }

}
