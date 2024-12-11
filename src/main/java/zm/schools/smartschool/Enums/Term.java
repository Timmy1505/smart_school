package zm.schools.smartschool.Enums;

public enum Term {
    TERM1("Term 1"),
    TERM2("Term 2"),
    TERM3("Term 3");

    private final String displayName;

    private Term(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}