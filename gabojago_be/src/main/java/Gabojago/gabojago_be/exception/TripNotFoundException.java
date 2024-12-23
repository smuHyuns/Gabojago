package Gabojago.gabojago_be.exception;

public class TripNotFoundException extends RuntimeException {
    public TripNotFoundException(Long tripId) {
        super("해당 여행이 존재하지 않습니다: " + tripId);
    }
}
