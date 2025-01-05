package Gabojago.gabojago_be.user;

import Gabojago.gabojago_be.dto.response.ResponseUserDto;
import Gabojago.gabojago_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserLoginId(String userLoginId);

    boolean existsByUserNickname(String userNickname);

    Optional<User> findByUserLoginId(String userLoginId);

    boolean findByUserLoginIdAndUserPassword(String userLoginId, String userPassword);

    Optional<Long> findUserIdByUserLoginId(String userLoginId);

    @Query("select u from User u")
    List<ResponseUserDto> findAllUserDto();

    Optional<User> findUserByUserEmailAndUserUsername(String email, String username);
}
