package main.storage.serializer;

import main.model.*;

import java.io.*;
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
                                dos.writeUTF(place.getDateStart());
                                dos.writeUTF(place.getDateEnd());
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
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                return new TextSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new ListSection(listReader(dis, dis::readUTF));
            case EDUCATION:
            case EXPERIENCE:
                return new OrganizationSection(
                        listReader(dis, () -> new Organization(
                                new Organization.Link(dis.readUTF(), dis.readUTF()),
                                listReader(dis, () -> new Organization.Place(
                                        dis.readUTF(), dis.readUTF(), dis.readUTF(), dis.readUTF()
                                ))
                        )));
            default:
                throw new IllegalArgumentException();
        }
    }

    private <T> List<T> listReader(DataInputStream dis, Reader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

    private interface Read {
        void read() throws IOException;
    }

    private interface Reader<T> {
        T read() throws IOException;
    }

    private interface Writer<T> {
        void write(T t) throws IOException;
    }

    private void Reader(DataInputStream dis, Read reador) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            reador.read();
        }
    }

    private <T> void writer(DataOutputStream dos, Collection<T> collection, Writer<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            writer.write(item);
        }
    }
}