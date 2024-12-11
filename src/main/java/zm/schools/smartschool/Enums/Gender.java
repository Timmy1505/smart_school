package zm.schools.smartschool.Enums;

public enum Gender {

    MALE ("Male"),
    FEMALE ("Female");

    private final String displayName;

    private Gender(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
