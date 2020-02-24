package aca.project.ftc.model.constants;

public enum Gender {
    FEMALE("Female"), MALE("Male"), OTHER("Other");

    private final String key;

    Gender(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
