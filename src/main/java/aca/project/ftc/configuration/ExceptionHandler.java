package aca.project.ftc.configuration;

import aca.project.ftc.exception.*;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;


import java.util.List;

@Slf4j
@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleException(CustomException ex) {
        log.error("Received {} exception with message {}", ex.getClass().getSimpleName(), ex.getMessage());
        return this.handleErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.name(), ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error("Received Exception: {} {} {}", ex.getLocalizedMessage(), ex.getCause(), ex);
        return this.handleErrorResponse(HttpStatus.UNAUTHORIZED.name(), ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(UserNotFoundException ex) {
        log.error("Received {} exception with message {}", ex.getClass().getSimpleName(), ex.getMessage());
        return this.handleErrorResponse(HttpStatus.NOT_FOUND.name(), ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(NotFoundException ex) {
        log.error("Received {} exception with message {}", ex.getClass().getSimpleName(), ex.getMessage());
        return this.handleErrorResponse(HttpStatus.NOT_FOUND.name(), ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ErrorResponse> handleException(InvalidParameterException ex) {
        log.error("Received {} exception with message {}", ex.getClass().getSimpleName(), ex.getMessage());
        return this.handleErrorResponse(HttpStatus.BAD_REQUEST.name(), ex.getMessage(), HttpStatus.BAD_REQUEST, ex.getErrorParamList());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponse> handleException(InvalidRequestException ex) {
        log.error("Received {} exception with message {}", ex.getClass().getSimpleName(), ex.getMessage());
        return this.handleErrorResponse(HttpStatus.CONFLICT.name(), ex.getMessage(), HttpStatus.CONFLICT);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(UnauthorizedRequestException.class)
    public ResponseEntity<ErrorResponse> handleException(UnauthorizedRequestException ex) {
        log.error("Received {} exception with message {}", ex.getClass().getSimpleName(), ex.getMessage());
        return this.handleErrorResponse(HttpStatus.UNAUTHORIZED.name(), ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ExistingUserException.class)
    public ResponseEntity<ErrorResponse> handleException(ExistingUserException ex) {
        log.error("Received {} exception with message {}", ex.getClass().getSimpleName(), ex.getMessage());
        return this.handleErrorResponse(HttpStatus.BAD_REQUEST.name(), ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NotificationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(NotificationNotFoundException ex) {
        log.error("Received {} exception with message {}", ex.getClass().getSimpleName(), ex.getMessage());
        return this.handleErrorResponse(HttpStatus.NOT_FOUND.name(), ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleException(BadCredentialsException ex) {
        log.error("Received {} exception with message {}", ex.getClass().getSimpleName(), ex.getMessage());
        return this.handleErrorResponse(HttpStatus.BAD_REQUEST.name(), ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleException(RuntimeException ex) {
        log.error("Received {} exception with message {}", ex.getClass().getSimpleName(), ex.getMessage());
        return this.handleErrorResponse(HttpStatus.BAD_REQUEST.name(), ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> handleErrorResponse(String errorCode, String errorMessage, HttpStatus httpStatus) {
        return this.handleErrorResponse(errorCode, errorMessage, httpStatus, null);
    }


    private ResponseEntity<ErrorResponse> handleErrorResponse(String errorCode, String errorMessage, HttpStatus httpStatus, List<ErrorParam> errorParams) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(errorCode)
                .message(errorMessage)
                .errorParamList(errorParams)
                .build();
        return ResponseEntity.status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }
}

