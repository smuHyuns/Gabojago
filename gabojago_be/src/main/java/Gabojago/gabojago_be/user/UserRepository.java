package Gabojago.gabojago_be.user;

import Gabojago.gabojago_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserLoginId(String userLoginId);
    boolean existsByUserNickname(String userNickname);

    Optional<User> findByUserLoginId(String userLoginId);
}
