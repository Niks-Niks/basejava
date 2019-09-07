package main.storage.serializer;

import main.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public class DataStreamSerializer implements Stream {

    static TextSection text;
    static ListSection list;

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            writer(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            writer(dos, resume.getSections().entrySet(), entry -> {
                SectionType Key = entry.getKey();
                AbstractSection section = entry.getValue();
                dos.writeUTF(Key.name());
                switch (Key) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) section).getText());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writer(dos, ((ListSection) section).getList(), dos::writeUTF);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        writer(dos, ((OrganizationSection) section).getList(), item -> {
                            dos.writeUTF(item.getHomePage().getLink());
                            dos.writeUTF(item.getHomePage().getHomePage());
                            writer(dos, item.getList(), place -> {
                                System.out.println("-----------------------------------------------------------------------------");
                                writeTime(dos, place.getDateStart());
                                writeTime(dos, place.getDateEnd());
                                dos.writeUTF(place.getTitle());
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
                resume.addSection(sectionType, sectionValue(sectionType, dis));
            });
            return resume;
        }
    }

    private AbstractSection sectionValue(SectionType sectionType, DataInputStream dis) throws IOException {
        System.out.println("    SectionType---> " + sectionType);
        switch (sectionType) {
            case PERSONAL:
                text = new TextSection(dis.readUTF());

                System.out.println("This text --> " + text.getText());
                return text;
            case OBJECTIVE:
                text = new TextSection(dis.readUTF());

                System.out.println("This text 2 --> " + text.getText());
                return text;
            case ACHIEVEMENT:
                list = new ListSection(listReader(dis, dis::readUTF));
                for (String q : list.getList()) {
                    System.out.println(q);
                }
                return list;
            case QUALIFICATIONS:
                list = new ListSection(listReader(dis, dis::readUTF));
                for (String q : list.getList()) {
                    System.out.println(q);
                }
                return list;
            case EDUCATION:
                return new OrganizationSection(listReader(dis, () -> new Organization(
                        new Organization.Link(dis.readUTF(), dis.readUTF()),
                        new Organization.Place()
                )));
//                return null;
            case EXPERIENCE:
                return new OrganizationSection(
                        listReader(dis, () -> new Organization(
                                new Organization.Link(dis.readUTF(), dis.readUTF()),
                                listReader(dis, () -> new Organization.Place(
                                        readTime(dis), readTime(dis), dis.readUTF(), dis.readUTF()
                                ))
                        )));
            default:
                return null;
        }
    }

    private <T> List<T> listReader(DataInputStream dis, Reader<T> reader) throws IOException {
        int size = dis.readInt();
        System.out.println("Size list read -- > " + size);
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
            System.out.println("In list -> " + list);
        }
        return list;
    }

    private interface Processor {
        void process() throws IOException;
    }

    private interface Reader<T> {
        T read() throws IOException;
    }

    private interface Writer<T> {
        void write(T t) throws IOException;
    }

    private void Reader(DataInputStream dis, Processor processor) throws IOException {
        int size = dis.readInt();
        System.out.println("Read int --> " + size);
        for (int i = 0; i < size; i++) {
            processor.process();
        }
    }

    private <T> void writer(DataOutputStream dos, Collection<T> collection, Writer<T> writer) throws IOException {
        dos.writeInt(collection.size());
        System.out.println("Col.size ->> " + collection.size());
        for (T item : collection) {
            System.out.println("What write --> "+ item.toString());
            writer.write(item);
        }
    }

    private void writeTime(DataOutputStream dos, LocalDate ld) throws IOException {
        dos.writeInt(ld.getYear());
        dos.writeInt(ld.getMonth().getValue());
    }

    private LocalDate readTime(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), 1);
    }


}
