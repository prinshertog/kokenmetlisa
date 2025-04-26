package be.freedombox.backend.exception;

public class ObjectDoesNotExistException extends RuntimeException {
    public ObjectDoesNotExistException(String message) {
        super(message);
    }
}
