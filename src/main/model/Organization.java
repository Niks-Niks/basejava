package main.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Organization {

    private String title;
    private List<OrganizationDate> list = new ArrayList<>();

    public Organization(String title, List<OrganizationDate> data) {
        this.title = title;
        list = data;
    }

    public Organization(String title, OrganizationDate... data) {
        this.title = title;
        list = Arrays.asList(data);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        return "Organization ( " + title + " " + list + ')';
    }

    public static class OrganizationDate {

        private LocalDate dateStart;
        private LocalDate dateEnd;
        private String description;

        public OrganizationDate(LocalDate date, LocalDate dateEnd, String description) {
            this.dateStart = date;
            this.dateEnd = dateEnd;
            this.description = description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            OrganizationDate that = (OrganizationDate) o;
            return Objects.equals(dateStart, that.dateStart) &&
                    Objects.equals(dateEnd, that.dateEnd) &&
                    Objects.equals(description, that.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(dateStart, dateEnd, description);
        }

        @Override
        public String toString() {
            return "OrganizationDate{" +
                    "dateStart=" + dateStart +
                    ", dateEnd=" + dateEnd +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}
