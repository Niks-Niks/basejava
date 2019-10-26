package main.model;

import main.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static main.util.DateUtil.NOW;
import static main.util.DateUtil.of;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {

    private List<Place> list = new ArrayList<>();
    private Link homePage;

    private static final long serialVersionUID = 1L;

    public Organization() {

    }

    public Organization(String name, String url, Place... list) {
        this(new Link(name, url), Arrays.asList(list));
    }

    public Organization(Link homePage, List<Place> list) {
        this.list = list;
        this.homePage = homePage;
    }

    public Organization(String name, String url, List<Place> list) {
        this.homePage = new Link(name, url);
        this.list = list;
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

        public Place(LocalDate dateStart, LocalDate dateEnd, String title,  String description) {
            this.dateStart = dateStart;
            this.dateEnd = dateEnd;
            this.title = title;
            this.description = description;
        }

        public Place(int startYear, Month startMonth, String title, String description) {
            this(of(startYear, startMonth), NOW, title, description);
        }

        public Place(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
            this(of(startYear, startMonth), of(endYear, endMonth), title, description);
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
            return  Objects.equals(dateStart, that.dateStart) &&
                    Objects.equals(dateEnd, that.dateEnd) &&
                    Objects.equals(title, that.title) &&
                    Objects.equals(description, that.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(dateStart, dateEnd, title, description);
        }

        @Override
        public String toString() {
            return "Place{" +
                    "dateStart=" + dateStart +
                    ", dateEnd=" + dateEnd +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}
