package aca.project.ftc.exception;

public class UnauthorizedRequest extends RuntimeException {
    public UnauthorizedRequest() {
    }

    public UnauthorizedRequest(String message) {
        super(message);
    }

    public UnauthorizedRequest(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedRequest(Throwable cause) {
        super(cause);
    }
}
