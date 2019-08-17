package main.model;

import java.time.LocalDate;
import java.util.Objects;

public class Organization {

    private String title;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private String description;

    public Organization(String title, LocalDate date, LocalDate dateEnd, String description) {
        this.title = title;
        this.dateStart = date;
        this.dateEnd = dateEnd;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(dateStart, that.dateStart) &&
                Objects.equals(dateEnd, that.dateEnd) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, dateStart, dateEnd, description);
    }

}
