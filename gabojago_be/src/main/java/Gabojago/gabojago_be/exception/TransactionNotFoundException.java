package Gabojago.gabojago_be.exception;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(Long transactionId) {
        super("해당 트랜잭션을 발견하지 못했습니다 : " + transactionId);
    }
}
