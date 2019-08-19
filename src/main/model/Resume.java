package main.model;

import java.util.*;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private String uuid;

    private String fullName;

    private Map<ContactType, String> Contacts = new EnumMap<>(ContactType.class);
    private Map<SectionType, AbstractSection> Sections = new EnumMap<>(SectionType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getContact(ContactType type) {
        return Contacts.get(type);
    }

    public AbstractSection getSection(SectionType type) {
        return Sections.get(type);
    }

    public void addContact(ContactType type, String value) {
        Contacts.put(type, value);
    }

    public void addSection(SectionType type, AbstractSection value) {
        Sections.put(type, value);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(Contacts, resume.Contacts) &&
                Objects.equals(Sections, resume.Sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, Contacts, Sections);
    }

    @Override
    public String toString() {
        return uuid;
    }

    @Override
    public int compareTo(Resume resume) {
        int check = fullName.compareTo(resume.getFullName());
        return check != 0 ? check : uuid.compareTo(resume.getUuid());
    }
}