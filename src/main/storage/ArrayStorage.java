package main.storage;

import main.model.Resume;
import main.storage.AbstractArrayStorage;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected Object getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;

    }

    @Override
    protected void saveInArray(int index, Resume resume) {
        storage[size] = resume;
    }

    @Override
    protected void deleteFromArray(int index) {
        storage[index] = storage[size - 1];
    }
}