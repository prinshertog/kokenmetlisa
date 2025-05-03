package be.freedombox.backend.exception;

public class IllegalInputException extends RuntimeException {
    public IllegalInputException(String message) {
        super(message);
    }
}
