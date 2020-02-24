package aca.project.ftc.model.constants;

import lombok.Getter;

@Getter
public enum TransactionStatus {
    PENDING("pending"), CANCELLED("canceled"),
    ACCEPTED("accepted"), REJECTED("rejected"),
    FAILED("failed"), SUCCESS("success");

    private final String key;

    TransactionStatus(String key) {
        this.key = key;
    }

}
