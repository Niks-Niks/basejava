package main.model;

import main.util.LocalDateAdapter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {

    private List<Place> list;
    private Link homePage;

    private static final long serialVersionUID = 1L;

    public Organization() {

    }

    public Organization(Link link, Place... data) {
        homePage = link;
        list = Arrays.asList(data);
    }

    public List<Place> getList() {
        return list;
    }

    public Link getHomePage() {
        return homePage;
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

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Link implements Serializable {

        private String link;
        private String homePage;

        public Link() {
        }

        public Link(String link, String homePage) {
            this.link = link;
            this.homePage = homePage;
        }

        public String getLink() {
            return link;
        }

        public String getHomePage() {
            return homePage;
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

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Place implements Serializable{

        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate dateStart;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate dateEnd;
        private String title;
        private String description;

        public Place() {

        }

        public Place(String title, LocalDate date, LocalDate dateEnd, String description) {
            this.title = title;
            this.dateStart = date;
            this.dateEnd = dateEnd;
            this.description = description;
        }

        public LocalDate getDateStart() {
            return dateStart;
        }

        public LocalDate getDateEnd() {
            return dateEnd;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
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
