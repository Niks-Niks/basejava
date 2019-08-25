package main.storage;

import main.model.Resume;

import java.io.IOException;
import java.util.List;

public interface Storage {

    void clear();

    void update(Resume resume);

    void save(Resume resume) throws IOException;

    Resume get(String uuid) throws IOException;

    void delete(String uuid);

    List<Resume> getAllSorted() throws IOException;

    int size() throws IOException;
}