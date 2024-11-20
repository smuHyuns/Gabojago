package Gabojago.gabojago_be.repository;

import Gabojago.gabojago_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
}
