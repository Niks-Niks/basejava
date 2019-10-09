package main.storage;

import main.Config;
import main.exception.ExistStorageException;
import main.exception.NotExistStorageException;
import main.model.ContactType;
import main.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class AbstractStorageTest {

    protected Storage storage;

    protected static final File STORAGE_DIRECTORY = Config.get().getStorageDir();

    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME_1 = new Resume(UUID_1, "fullName");

    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME_2 = new Resume(UUID_2, "fullName");

    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_3 = new Resume(UUID_3, "fullName");

    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_4 = new Resume(UUID_4, "fullName");

//    private static String inList = "Some text for Array";
//    private static ArrayList list = new ArrayList();

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    static {
//        RESUME_1.addContact(ContactType.LINKEDIN, "linkedIn");
//        RESUME_1.addContact(ContactType.GITHUB, "GitHub");
        RESUME_1.addContact(ContactType.MAIL, "main");
        RESUME_1.addContact(ContactType.SKYPE, "skype");
        RESUME_1.addContact(ContactType.TELEPHONE, "tel");

//        RESUME_2.addContact(ContactType.LINKEDIN, "linkedIn");
//        RESUME_2.addContact(ContactType.GITHUB, "GitHub");
        RESUME_2.addContact(ContactType.MAIL, "main");
//        RESUME_2.addContact(ContactType.SKYPE, "skype");
//        RESUME_2.addContact(ContactType.TELEPHONE, "tel");
//
//        RESUME_3.addContact(ContactType.LINKEDIN, "linkedIn");
//        RESUME_3.addContact(ContactType.GITHUB, "GitHub");
//        RESUME_3.addContact(ContactType.MAIL, "main");
        RESUME_3.addContact(ContactType.SKYPE, "skype");
//        RESUME_3.addContact(ContactType.TELEPHONE, "tel");
//
//        RESUME_4.addContact(ContactType.LINKEDIN, "linkedIn");
//        RESUME_4.addContact(ContactType.GITHUB, "GitHub");
        RESUME_4.addContact(ContactType.MAIL, "main");
        RESUME_4.addContact(ContactType.SKYPE, "skype");
        RESUME_4.addContact(ContactType.TELEPHONE, "tel");

//        list.add(inList);

//        RESUME_1.addSection(SectionType.PERSONAL, new TextSection("I'm okey"));
//        RESUME_1.addSection(SectionType.OBJECTIVE, new TextSection("first is first"));
//        RESUME_1.addSection(SectionType.EDUCATION, new OrganizationSection(
//                new Organization("","",
//                        new Organization.Place(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 12, 1), "title", "description"),
//                        new Organization.Place(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 12, 1), "Title_2", "description")
//                )));
//        RESUME_1.addSection(SectionType.ACHIEVEMENT, new ListSection(list));
//        RESUME_1.addSection(SectionType.QUALIFICATIONS, new ListSection(list));
//        RESUME_1.addSection(SectionType.EXPERIENCE, new OrganizationSection(
//                new Organization("Link", "HomePage",
//                        new Organization.Place(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 12, 1), "title", "description"),
//                        new Organization.Place(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 12, 1), "Title_2", "description"))));

    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() throws Exception {
        Resume resume = new Resume(UUID_1, "fullName");
        resume.addContact(ContactType.LINKEDIN, "linkedIn");
        resume.addContact(ContactType.GITHUB, "GitHub");
//        resume.addContact(ContactType.MAIL, "main");
//        resume.addContact(ContactType.SKYPE, "skype");
//        resume.addContact(ContactType.TELEPHONE, "tel");
        storage.update(resume);
        Assert.assertEquals(resume, storage.get(UUID_1));
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_4);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(RESUME_4, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(RESUME_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(RESUME_4);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_3);
        Assert.assertEquals(2, storage.size());
        storage.get(UUID_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(UUID_4);
    }

    @Test
    public void get() throws Exception {
        Assert.assertEquals(RESUME_1, storage.get(UUID_1));
        Assert.assertEquals(RESUME_2, storage.get(UUID_2));
        Assert.assertEquals(RESUME_3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get(UUID_4);
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> resumes = storage.getAllSorted();
        Assert.assertEquals(3, resumes.size());
        Assert.assertEquals(resumes, Arrays.asList(RESUME_1, RESUME_2, RESUME_3));
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }
}
