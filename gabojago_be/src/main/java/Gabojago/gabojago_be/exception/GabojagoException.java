package Gabojago.gabojago_be.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GabojagoException extends RuntimeException {
    private final ErrorCode errorCode;

    public GabojagoException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public HttpStatus getHttpStatus() {
        return errorCode.getHttpStatus();
    }

    public String getErrorCode() {
        return errorCode.getErrorCode();
    }
}
