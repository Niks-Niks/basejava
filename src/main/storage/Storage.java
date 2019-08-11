package main.storage;

import main.model.Resume;

import java.util.List;

public interface Storage {

    void clear();

    void update(Resume resume);

    void save(Resume resume);

    Resume get(String uuid);

    void delete(String uuid);

    List<Resume> getAllSorted();

    int size();
}