package main.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Organization implements Serializable {

    private List<Place> list;
    private Link homePage;
    private static final long serialVersionUID = 1L;

    public Organization(Link link, Place... data) {
        homePage = link;
        list = Arrays.asList(data);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(list, that.list) &&
                Objects.equals(homePage, that.homePage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list, homePage);
    }

    @Override
    public String toString() {
        return "( " + homePage + ", " + list + ')';
    }

    public static class Link implements Serializable {

        private String link;
        private String homePage;

        public Link(String link, String homePage) {
            this.link = link;
            this.homePage = homePage;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Link link1 = (Link) o;
            return Objects.equals(link, link1.link) &&
                    Objects.equals(homePage, link1.homePage);
        }

        @Override
        public int hashCode() {
            return Objects.hash(link, homePage);
        }

        @Override
        public String toString() {
            return "link->( " + link + ", " + homePage + " )";
        }
    }

    public static class Place implements Serializable{

        private String title;
        private LocalDate dateStart;
        private LocalDate dateEnd;
        private String description;

        public Place(String title, LocalDate date, LocalDate dateEnd, String description) {
            this.title = title;
            this.dateStart = date;
            this.dateEnd = dateEnd;
            this.description = description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Place that = (Place) o;
            return Objects.equals(title, that.title) &&
                    Objects.equals(dateStart, that.dateStart) &&
                    Objects.equals(dateEnd, that.dateEnd) &&
                    Objects.equals(description, that.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(title, dateStart, dateEnd, description);
        }

        @Override
        public String toString() {
            return "Place-> (" + title + ", " + dateStart + ", " + dateEnd + ", " + description + " )";
        }
    }
}
