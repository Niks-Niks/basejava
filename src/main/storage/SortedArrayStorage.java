package main.storage;

import main.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage implements Comparator<Resume> {

    @Override
    public int compare(Resume one, Resume two) {
        return one.getUuid().compareTo(two.getUuid());
    }

    @Override
    protected void saveInArray(int index, Resume resume) {
        int idx = -index - 1;
        System.arraycopy(storage, idx, storage, idx + 1, size - idx);
        storage[idx] = resume;
    }

    @Override
    protected void deleteFromArray(int index) {
        int idx = size - index - 1;
        if (idx > 0) {
            System.arraycopy(storage, index + 1, storage, index, idx);
        }
    }

    @Override
    protected Object getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "fullName");
        return Arrays.binarySearch(storage, 0, size, searchKey, this::compare);
    }
}
