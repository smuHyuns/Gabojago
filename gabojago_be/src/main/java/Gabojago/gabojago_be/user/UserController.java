package Gabojago.gabojago_be.user;

import Gabojago.gabojago_be.dto.request.RequestLoginDto;
import Gabojago.gabojago_be.dto.request.RequestProfileCheckDto;
import Gabojago.gabojago_be.dto.request.RequestProfileDto;
import Gabojago.gabojago_be.dto.request.RequestSignUpDto;
import Gabojago.gabojago_be.dto.response.ResponseLoginDto;
import Gabojago.gabojago_be.dto.response.ResponseProfileDto;
import Gabojago.gabojago_be.entity.User;
import Gabojago.gabojago_be.exception.InvalidCredentialsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<User> signUp(@RequestBody RequestSignUpDto dto) {
        log.info("sign-up 요청");
        try {
            User savedUser = userService.addUser(dto);
            log.info("유저 등록 성공 : {}", savedUser);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseLoginDto> login(@RequestBody RequestLoginDto dto) {
        try {
            ResponseLoginDto response = userService.login(dto);
            return ResponseEntity.ok(response);
        } catch (InvalidCredentialsException e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/check-nickname")
    public ResponseEntity checkUserNickname(@RequestBody String userNickname) {
        try {
            if (userService.isDupUserNickname(userNickname)) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/check-loginId")
    public ResponseEntity checkUserLoginId(@RequestBody String userLoginId) {
        try {
            if (userService.isDupUserLoginId(userLoginId)) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<ResponseProfileDto> getProfile(@RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok(userService.getProfile(token));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/profile")
    public ResponseEntity updateProfile(@RequestHeader("Authorization") String token, @RequestBody RequestProfileDto request) {
        try {
            userService.updateUser(token, request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }



//    @PostMapping("/check-profileImg")
//    public ResponseEntity<Boolean> checkProfileImg(@RequestHeader("Authorization") String token, @RequestBody RequestProfileCheckDto request) {
//        try {
//            boolean isDifferent = userService.checkUserProfileImg(token, request.getUserProfileImg());
//            return ResponseEntity.ok(isDifferent);
//        } catch (Exception e) {
//            log.info(e.getMessage());
//            return ResponseEntity.badRequest().build();
//        }
//    }
}
