package main.model;

public enum ContactType {
    TELEPHONE("Telephone"),
    SKYPE("Skype"),
    MAIL("Mail"),
    LINKEDIN("LinkedIn"),
    GITHUB("GitHub");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
