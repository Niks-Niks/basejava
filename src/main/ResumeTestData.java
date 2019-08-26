package main;

import main.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Name");
        String textForTextSection = "This new text special for textSection.";

        List<String> listForListSection = new ArrayList<>();
        listForListSection.add(textForTextSection);
        listForListSection.add(textForTextSection);

        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle());
        }
        System.out.println();
        for (ContactType type : ContactType.values()) {
            System.out.println(type.getTitle());
        }

        resume.addContact(ContactType.LINKEDIN, "linkedIn");
        resume.addContact(ContactType.GITHUB, "GitHub");
        resume.addContact(ContactType.MAIL, "main");
        resume.addContact(ContactType.SKYPE, "skype");
        resume.addContact(ContactType.TELEPHONE, "tel");

        resume.addSection(SectionType.PERSONAL, new TextSection("I'm okey"));
        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(listForListSection));
        resume.addSection(SectionType.EDUCATION, new OrganizationSection(new Organization(new Organization.Link("Link", "HomePage"),
                new Organization.Place("title", (LocalDate.of(2019, 8, 18)), LocalDate.of(2020, 8, 18), "description"),
                new Organization.Place("title", (LocalDate.of(2019, 8, 18)), LocalDate.of(2020, 8, 18), "description"),
                new Organization.Place("title", (LocalDate.of(2019, 8, 18)), LocalDate.of(2020, 8, 18), "description"))));
        resume.addSection(SectionType.OBJECTIVE, new TextSection("first is first"));
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(listForListSection));
        resume.addSection(SectionType.EXPERIENCE, new OrganizationSection(new Organization(new Organization.Link("Link", "HomePage"),
                new Organization.Place("title", (LocalDate.of(2019, 8, 18)), LocalDate.of(2020, 8, 18), "description"),
                new Organization.Place("title", (LocalDate.of(2019, 8, 18)), LocalDate.of(2020, 8, 18), "description"),
                new Organization.Place("title", (LocalDate.of(2019, 8, 18)), LocalDate.of(2020, 8, 18), "description"))));

        outContact(resume);
        outSection(resume);
    }

    private static void outContact(Resume resume) {
        for (ContactType type : ContactType.values()) {
            System.out.println(resume.getContact(type));
        }
    }

    private static void outSection(Resume resume) {
        for (SectionType type : SectionType.values()) {
            System.out.println(resume.getSection(type).toString());
        }
    }
}
