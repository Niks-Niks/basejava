package main.storage.serializer;

import main.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements Stream {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Writter(dos, resume.getSections().entrySet(), entry -> {
                SectionType Key = entry.getKey();
                AbstractSection section = entry.getValue();
                switch (Key) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) section).getText());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        Writter(dos, ((ListSection) section).getList(), str -> dos.writeUTF(str));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        Writter(dos, ((OrganizationSection) section).getList(), item -> {
                            dos.writeUTF(item.getHomePage().getLink());
                            dos.writeUTF(item.getHomePage().getHomePage());
                            Writter(dos, item.getList(), place -> {
                                dos.writeUTF(place.getTitle());
                                dos.writeInt(place.getDateStart().getMonthValue());
                                dos.writeInt(place.getDateEnd().getMonthValue());
                                dos.writeUTF(place.getDescription());
                            });
                        });
                        break;
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            Reader(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            Reader(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        new TextSection(dis.readUTF());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        new ListSection(listReader(dis, dis::readUTF));//list reader
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        new OrganizationSection(listReader(dis, () -> new Organization(
                                new Organization.Link(dis.readUTF(), dis.readUTF()),
                                new Organization.Place(dis.readUTF(), readTime(dis), readTime(dis), dis.readUTF())
                        )));
                        break;
                }
            });


            return resume;
        }
    }

    private interface Writter<T> {
        void write(T t) throws IOException;
    }

    private interface Reader<T> {
        T read() throws IOException;
    }

    private interface Write {
        void write() throws IOException;
    }

    private <T> void Writter(DataOutputStream dos, Collection<T> list, Writter<T> printer) throws IOException {
        for (T out : list) {
            printer.write(out);
        }
    }

    private <T> void Reader(DataInputStream dis, Write write) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            write.write();
        }
    }

    private LocalDate readTime(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), 7);
    }

    private <T> List<T> listReader(DataInputStream dis, Reader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }
}
