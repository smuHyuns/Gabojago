package Gabojago.gabojago_be.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class CustomErrorResponse {
    private final HttpStatus status;
    private final String errorCode;
    private final String message;

    public int getStatusCode() {
        return status.value();
    }
}
