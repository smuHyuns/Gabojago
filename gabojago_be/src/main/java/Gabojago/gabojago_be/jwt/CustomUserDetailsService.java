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
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            // username을 userId로 변환 (필요 시 username 파싱)
            Long userId = Long.parseLong(username);
            return loadUserByUserId(userId);
        } catch (NumberFormatException e) {
            log.error("Invalid username format: {}", username);
            throw new UsernameNotFoundException("유효하지 않은 username입니다: " + username, e);
        }
    }

    public CustomUserDetails loadUserByUserId(Long userId) throws UsernameNotFoundException {
        Optional<User> user = userService.getUserByUserId(userId);
        log.info("loadUserByUserId: {}", user);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("유저를 찾지 못했습니다: " + userId);
        }

        return new CustomUserDetails(
                user.get().getUserId(),
                user.get().getUserLoginId(),
                user.get().getUserPassword()
        );
    }
}
