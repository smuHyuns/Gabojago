package Gabojago.gabojago_be.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
        super("해당 유저를 발견하지 못했습니다 : " + userId);
    }
}
