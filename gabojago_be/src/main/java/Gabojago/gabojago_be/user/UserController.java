package Gabojago.gabojago_be.user;

import Gabojago.gabojago_be.dto.request.*;
import Gabojago.gabojago_be.dto.response.ResponseEmailAuthDto;
import Gabojago.gabojago_be.dto.response.ResponseFindIdDto;
import Gabojago.gabojago_be.dto.response.ResponseLoginDto;
import Gabojago.gabojago_be.dto.response.ResponseProfileDto;
import Gabojago.gabojago_be.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "사용자(유저)", description = "회원가입, 로그인, 아이디 중복확인, 닉네임 중복확인, 프로필 저장/업데이트")
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    @Operation(summary = "회원가입 요청처리", description = "전달된 정보를 기반으로 회원가입을 진행합니다.")
    public ResponseEntity<User> signUp(@RequestBody RequestSignUpDto dto) {
        User savedUser = userService.addUser(dto);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "전달된 정보를 기반으로 로그인을 진행합니다. JWT 토큰을 생성하여 반환합니다.")
    public ResponseEntity<ResponseLoginDto> login(@RequestBody RequestLoginDto dto) {
        ResponseLoginDto response = userService.login(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check-nickname")
    @Operation(summary = "닉네임 중복확인", description = "닉네임의 중복여부를 반환합니다.")
    public ResponseEntity<Void> checkUserNickname(@RequestBody String userNickname) {
        boolean isDuplicate = userService.isDupUserNickname(userNickname);
        return isDuplicate ? ResponseEntity.badRequest().build() : ResponseEntity.ok().build();
    }

    @GetMapping("/check-loginId")
    @Operation(summary = "로그인아이디 중복확인", description = "로그인아이디의 중복여부를 반환합니다.")
    public ResponseEntity<Void> checkUserLoginId(@RequestBody String userLoginId) {
        boolean isDuplicate = userService.isDupUserLoginId(userLoginId);
        return isDuplicate ? ResponseEntity.badRequest().build() : ResponseEntity.ok().build();
    }

    @GetMapping("/profile")
    @Operation(summary = "유저 프로필 정보 불러오기", description = "전달된 토큰을 해독하여 토큰의 유저아이디에 해당하는 값의 프로필 정보를 불러옵니다.")
    public ResponseEntity<ResponseProfileDto> getProfile(@RequestHeader("Authorization") String token) {
        ResponseProfileDto profile = userService.getProfile(token);
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/profile")
    @Operation(summary = "유저 프로필 정보 업데이트", description = "토큰을 해독하여 유저를 확인한 후, 해당 유저의 프로필 정보를 업데이트합니다.")
    public ResponseEntity<Void> updateProfile(@RequestHeader("Authorization") String token,
                                              @RequestBody RequestProfileDto request) {
        userService.updateUser(token, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/authEmail")
    @Operation(summary = "유저 이메일 인증번호 발송", description = "같이 전송된 유저의 이메일 주소에 인증 번호를 보냅니다")
    public ResponseEntity<Void> authEmail(@RequestBody RequestEmailAuthDto request) {
        userService.sendEmail(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/authEmail-check")
    @Operation(summary = "유저 이메일 인증번호 확인", description = "유저에게 전송된 유저 이메일 인증번호와 캐시에 저장된 인증번호가 동일한지 확인합니다.")
    public ResponseEntity<Void> authEmailCheck(@RequestBody RequestAuthCheckDto request) {
        userService.checkEmail(request);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/find-id")
    @Operation(summary = "아이디 찾기", description = "사용자의 이름과 이메일에 해당하는 아이디를 반환합니다.")
    public ResponseEntity<ResponseFindIdDto> findId(@RequestParam("email") String email, @RequestParam("username") String username) {
        ResponseFindIdDto response = userService.findId(email, username);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/change-password")
    @Operation(summary = "비밀번호 바꾸기", description = "사용자의 아이디와 일치하는 유저의 비밀번호를 변경합니다")
    public ResponseEntity<Void> changePw(@RequestBody RequestChangePwDto request){
        userService.changePassword(request);
        return ResponseEntity.ok().build();
    }
}
