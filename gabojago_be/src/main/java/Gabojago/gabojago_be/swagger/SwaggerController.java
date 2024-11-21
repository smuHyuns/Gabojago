package Gabojago.gabojago_be.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SwaggerController {
    @Operation(summary = "Hello API", description = "Swagger에서 확인 가능한 예제 API입니다.")
    @GetMapping("/hello")
    public String hello() {
        return "Hello, Swagger!";
    }

    @Operation(summary = "Post Data", description = "데이터를 수신합니다.")
    @PostMapping("/data")
    public String postData(@Parameter(description = "전송할 데이터") @RequestBody String data) {
        return "Data received: " + data;
    }
}
