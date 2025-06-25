package be.freedombox.backend.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public static ResponseEntity<Map<String, String>> createErrorResponse(String message, HttpStatus status) {
        Map<String, String> error = new HashMap<>();
        error.put("error", message);
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String errorMessage = error.getDefaultMessage();
            errors.put(error.getField(), errorMessage);
            logger.error("Validation error on field '{}': {}", error.getField(), errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ObjectAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleObjectAlreadyExistsException(ObjectAlreadyExistsException ex) {
        return createErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ObjectDoesNotExistException.class)
    public ResponseEntity<Map<String, String>> handleObjectDoesNotExistException(ObjectDoesNotExistException ex) {
        return createErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<Map<String, String>> handleUnAuthorizedException(UnAuthorizedException ex) {
        return createErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalInputException.class)
    public ResponseEntity<Map<String, String>> handleIllegalInputException(IllegalInputException ex) {
        return createErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotImplementedException.class)
    public ResponseEntity<Map<String, String>> handleNotImplementedException(NotImplementedException ex) {
        return createErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FileException.class)
    public ResponseEntity<Map<String, String>> handleFileException(FileException ex) {
        return createErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DishException.class)
    public ResponseEntity<Map<String, String>> handleDishException(DishException ex) {
        return createErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CategoryException.class)
    public ResponseEntity<Map<String, String>> handleCategoryException(CategoryException ex) {
        return createErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}