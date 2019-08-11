package main.storage;

import main.exception.StorageException;
import main.model.Resume;

import java.util.List;

import static java.util.Arrays.*;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void updateToStorage(Object index, Resume resume) {
        storage[(int) index] = resume;
    }

    @Override
    protected void saveToStorage(Object index, Resume resume) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            saveInArray((Integer) index, resume);
            size++;
        }
    }

    @Override
    protected void deleteFromStorage(Object index) {
        deleteFromArray((Integer) index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public Resume getStorage(Object index) {
        return storage[(int) index];
    }

    @Override
    protected List<Resume> copyAllSorted() {
        return asList(copyOf(storage, size));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected boolean isExist(Object index) {
        return (Integer) index >= 0;
    }

    protected abstract void saveInArray(int index, Resume resume);

    protected abstract void deleteFromArray(int index);
}
