package aca.project.ftc;

import aca.project.ftc.exception.CustomException;
import aca.project.ftc.exception.ErrorParam;
import aca.project.ftc.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;


import java.util.List;

@Slf4j
@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleException(CustomException ex) {
        return this.handleErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.name(), ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //TODO:: WHY DO WE NEED THIS? CAN't WE JUST IGNORE IT AND CALL THE OTHER METHOD ?
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

