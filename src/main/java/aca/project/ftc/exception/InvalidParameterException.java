package aca.project.ftc.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class InvalidParameterException extends RuntimeException {
    private List<ErrorParam> errorParamList;

    public InvalidParameterException() {
    }

    public InvalidParameterException(String message) {
        super(message);
    }

    public InvalidParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidParameterException(Throwable cause) {
        super(cause);
    }
}
