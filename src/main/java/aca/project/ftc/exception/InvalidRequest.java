package aca.project.ftc.exception;




public class InvalidRequest extends RuntimeException {

    public InvalidRequest() {
    }

    public InvalidRequest(String message) {
        super(message);
    }

    public InvalidRequest(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRequest(Throwable cause) {
        super(cause);
    }
}
