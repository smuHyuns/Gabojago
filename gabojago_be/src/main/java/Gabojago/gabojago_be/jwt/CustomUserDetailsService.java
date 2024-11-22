package Gabojago.gabojago_be.jwt;

import Gabojago.gabojago_be.entity.User;
import Gabojago.gabojago_be.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public CustomUserDetails loadUserByUsername(String userLoginId) throws UsernameNotFoundException {
        Optional<User> user = userService.getUserByLoginId(userLoginId);
        log.info("loadUserByUsername: {}", user);

        if (user == null) {
            throw new UsernameNotFoundException("유저를 찾지 못했습니다: " + userLoginId);
        }

        return new CustomUserDetails(
                user.get().getUserId(),
                user.get().getUserLoginId(),
                user.get().getUserPassword() // 데이터베이스에서 조회된 인코딩된 비밀번호
        );
    }
}
