package aca.project.ftc.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class InvalidParameters extends RuntimeException {
    private List<ErrorParam> errorParamList;

    public InvalidParameters() {
    }

    public InvalidParameters(String message) {
        super(message);
    }

    public InvalidParameters(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidParameters(Throwable cause) {
        super(cause);
    }
}
