package main;

import main.model.ContactType;
import main.model.Resume;
import main.model.SectionType;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Name");

        for(SectionType type : SectionType.values()){
            System.out.println(type.getTitle());
        }
        System.out.println();
        for(ContactType type : ContactType.values()){
            System.out.println(type.getTitle());
        }

        resume.addContact(ContactType.LINKEDIN, "linkedIn");
        resume.addContact(ContactType.GITHUB, "GitHub");
        resume.addContact(ContactType.MAIL, "main");
        resume.addContact(ContactType.SKYPE, "skype");
        resume.addContact(ContactType.TELEPHONE, "tel");

        resume.addSection(SectionType.PERSONAL, "I'm");
        resume.addSection(SectionType.ACHIEVEMENT, "is");
        resume.addSection(SectionType.EDUCATION, "you");
        resume.addSection(SectionType.OBJECTIVE, "are");
        resume.addSection(SectionType.QUALIFICATIONS, ".");

        outContact(resume);
        outSection(resume);
    }

    private static void outContact(Resume resume){
        for (ContactType type : ContactType.values()) {
            System.out.println(resume.getContact(type));
        }
    }

    private static void outSection(Resume resume){
        for (SectionType type : SectionType.values()) {
            System.out.println(resume.getSection(type));
        }
    }
}
