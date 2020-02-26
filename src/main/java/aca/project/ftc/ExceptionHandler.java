package aca.project.ftc;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import aca.project.ftc.exception.UserNotFound;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler() {
        return new ResponseEntity<String>("Unknown Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UserNotFound.class)
    public ResponseEntity<String> userNotFoundHandler() {
        return new ResponseEntity<String>("User not found", HttpStatus.BAD_REQUEST);
    }
}
