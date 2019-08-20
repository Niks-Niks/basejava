package main.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Organization {

    private String title;
    private List<Place> list;
    private Link name;

    public Organization(String title, List<Place> data) {
        this.title = title;
        list = data;
    }

    public Organization(String title, Link name, Place... data) {
        this.title = title;
        this.name = name;
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
        return "( " + title + ", " + name + ", " + list + ')';
    }

    public static class Link {

        private String link;
        private String name;

        public Link(String link, String name) {
            this.link = link;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Link link1 = (Link) o;
            return Objects.equals(link, link1.link) &&
                    Objects.equals(name, link1.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(link, name);
        }

        @Override
        public String toString() {
            return "link->( " + link + ", " + name + " )";
        }
    }

    public static class Place {

        private LocalDate dateStart;
        private LocalDate dateEnd;
        private String description;

        public Place(LocalDate date, LocalDate dateEnd, String description) {
            this.dateStart = date;
            this.dateEnd = dateEnd;
            this.description = description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Place that = (Place) o;
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
            return "Place-> (" + dateStart + ", " + dateEnd + ", " + description + " )";
        }
    }
}
