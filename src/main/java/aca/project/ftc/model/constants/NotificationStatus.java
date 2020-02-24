package aca.project.ftc.model.constants;

import lombok.Getter;

@Getter
public enum NotificationStatus {
    PENDING("Pending"), ACCEPTED("Accepted"), REJECTED("Rejected");

    private final String key;

    NotificationStatus(String key) {
        this.key = key;
    }

}
