package main.model;

import java.time.LocalDate;

public class Organization{

    private String title;
    private LocalDate date;
    private String description;

    public Organization(String title, LocalDate date, String description) {
        this.title = title;
        this.date = date;
        this.description = description;
    }
    
}
