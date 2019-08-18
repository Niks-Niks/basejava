package main.model;

public enum SectionType {
    PERSONAL("Personal qualities"),
    OBJECTIVE("Position"),
    ACHIEVEMENT("Achievement"),
    QUALIFICATIONS("Qualifications"),
    EDUCATION("Education"),
    EXPERIENCE("Experience");

    private final String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
