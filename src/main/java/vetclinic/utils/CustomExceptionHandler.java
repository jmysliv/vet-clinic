package vetclinic.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.DateTimeException;
import java.util.Objects;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomResponse<Object>> handleConstraintViolationException(ConstraintViolationException e) {
        CustomResponse<Object> response = new CustomResponse<>(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        return response.generateResponseEntity();
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<CustomResponse<Object>> handleResponseStatusException(ResponseStatusException e) {
        CustomResponse<Object> response = new CustomResponse<>(e.getStatus(), e.getReason(), null);
        return response.generateResponseEntity();
    }

    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<CustomResponse<Object>> handleDateTimeException(DateTimeException e) {
        CustomResponse<Object> response = new CustomResponse<>(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        return response.generateResponseEntity();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(
                new CustomResponse<>(status, Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage(), null), status);
    }
}
