package main.storage;

import main.exception.*;
import main.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        Object searchKey = getExistSearchKey(resume.getUuid());
        updateToStorage(searchKey, resume);
    }

    @Override
    public void save(Resume resume) {
        Object searchKey = getNotExistSearchKey(resume.getUuid());
        saveToStorage(searchKey, resume);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getExistSearchKey(uuid);
        deleteFromStorage(searchKey);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getExistSearchKey(uuid);
        return getStorage(searchKey);
    }

    private Object getExistSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> sortedList = copyAllSorted();
        Collections.sort(sortedList);
        return sortedList;
    }

    protected abstract List<Resume> copyAllSorted();

    protected abstract boolean isExist(Object searchKey);

    protected abstract void updateToStorage(Object searchKey, Resume resume);

    protected abstract void saveToStorage(Object searchKey, Resume resume);

    protected abstract void deleteFromStorage(Object searchKey);

    protected abstract Resume getStorage(Object searchKey);

    protected abstract Object getSearchKey(String uuid);

}
