package aca.project.ftc.exception;

public class NotificationNotFound extends RuntimeException {

    public NotificationNotFound() {
    }

    public NotificationNotFound(String message) {
        super(message);
    }

    public NotificationNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public NotificationNotFound(Throwable cause) {
        super(cause);
    }
}
