package kz.safetrip.safetrip.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(EntityNotFoundException ex) { return build(HttpStatus.NOT_FOUND, ex.getMessage(), null); }
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<ApiError> handleBadRequest(RuntimeException ex) { return build(HttpStatus.BAD_REQUEST, ex.getMessage(), null); }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentials(BadCredentialsException ex) { return build(HttpStatus.UNAUTHORIZED, "Invalid email or password", null); }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> validationErrors = new LinkedHashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        return build(HttpStatus.BAD_REQUEST, "Validation failed", validationErrors);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex) { return build(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null); }
    private ResponseEntity<ApiError> build(HttpStatus status, String message, Map<String, String> validationErrors) {
        return ResponseEntity.status(status).body(ApiError.builder().timestamp(LocalDateTime.now()).status(status.value()).error(status.getReasonPhrase()).message(message).validationErrors(validationErrors).build());
    }
}
