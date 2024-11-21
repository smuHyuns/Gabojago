package Gabojago.gabojago_be.test;


import Gabojago.gabojago_be.entity.User;
import Gabojago.gabojago_be.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final UserService userService;

    @GetMapping("/hi")
    public String Hello() {
        return "성공!";
    }

    @GetMapping("/users")
    public String user(){
        List<User> users = userService.getAllUsers();
        StringBuilder sb = new StringBuilder();
        sb.append("userId : ").append(users.get(0).getUserId()).append("<br>");
        sb.append("userNickname : ").append(users.get(0).getUserNickname()).append("<br>");
        sb.append("userPassword :" + users.get(0).getUserPassword()).append("<br>");
        sb.append("userProfileImg: ").append(users.get(0).getUserProfileImg()).append("<br>");
        sb.append("userLoginId : ").append(users.get(0).getUserLoginId()).append("<br>");
        return sb.toString();
    }

}
