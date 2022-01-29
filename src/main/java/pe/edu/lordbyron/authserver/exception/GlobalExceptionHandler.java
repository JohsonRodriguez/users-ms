package pe.edu.lordbyron.authserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserRepositoryException.class)
    public ResponseEntity<?> customValidationErrorHandling(UserRepositoryException ex) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                "User repository exception",
                ex.getMessage()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
