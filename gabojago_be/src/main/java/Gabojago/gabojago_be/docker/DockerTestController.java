package Gabojago.gabojago_be.docker;

import Gabojago.gabojago_be.dto.response.ResponseUserDto;
import Gabojago.gabojago_be.entity.User;
import Gabojago.gabojago_be.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dockerLetsgo")
@RequiredArgsConstructor
public class DockerTestController {
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<User>> test() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/2")
    public ResponseEntity<List<ResponseUserDto>> test2() {
        return ResponseEntity.ok(userService.getAllUsersWithoutTrips());
    }
}
