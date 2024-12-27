package Gabojago.gabojago_be.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public enum ErrorCode {
    // JWT 관련 에러
    TOKEN_EXTRACTION_FAILED(HttpStatus.UNAUTHORIZED, "JWT_001", "유효기간이 지난 JWT 토큰입니다."),

    TRIP_NOT_FOUND(HttpStatus.BAD_REQUEST, "TRIP_001", "tripId에 해당하는 여행을 찾을 수 없습니다."),
    TRIP_INVALID_DATE_FORMAT(HttpStatus.BAD_REQUEST, "TRIP_002", "date의 양식이 잘못되었습니다."),
    TRIP_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "TRIP_003", "여행 등록 양식에 누락된 부분이 있습니다."),
    TRIP_INVALID_TRIPSTATUS(HttpStatus.BAD_REQUEST, "TRIP_004", "유효하지 않은 여행상태코드입니다"),

    TRANSACTION_NOT_FOUND(HttpStatus.BAD_REQUEST, "TRANSACTION_001", "transaction Id에 해당하는 거래를 찾을 수 없습니다."),
    TRANSACTION_MISSING_VALUES(HttpStatus.BAD_REQUEST, "TRANSACTION_002", "거래에 필요한 값이 누락되었습니다."),
    TRANSACTION_INVALID_TYPE(HttpStatus.BAD_REQUEST, "TRANSACTION_003", "거래 유형은 '지출'과 '추가'만 허용됩니다."),

    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER_001", "userId에 해당하는 사용자를 찾을 수 없습니다."),
    USER_INVALID_LOGIN(HttpStatus.BAD_REQUEST, "USER_002", "아이디 혹은 비밀번호가 일치하지 않습니다."),

    USER_UTIL_INVALID_DATE_FORMAT(HttpStatus.BAD_REQUEST, "USER_UTIL_001", "유효하지 않은 포맷입니다. 'YYYY-MM-DD' 를 사용해주세요"),

    EXCHANGE_RATE_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "EXCHANGE_RATE_001", "유효하지 않은환율 데이터 형식입니다."),
    EXCHANGE_RATE_API_CALL_FAILED(HttpStatus.BAD_GATEWAY, "EXCHANGE_RATE_002", "환율 데이터를 가져오는데 실패했습니다."),

    ;


    ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}