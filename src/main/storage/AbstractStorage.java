package main.storage;

import main.exception.ExistStorageException;
import main.exception.NotExistStorageException;
import main.model.Resume;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

    @Override
    public void update(Resume resume) {
        SK searchKey = getExistSearchKey(resume.getUuid());
        updateToStorage(searchKey, resume);
    }

    @Override
    public void save(Resume resume) throws IOException {
        SK searchKey = getNotExistSearchKey(resume.getUuid());
        saveToStorage(searchKey, resume);
    }

    @Override
    public void delete(String uuid) {
        SK searchKey = getExistSearchKey(uuid);
        deleteFromStorage(searchKey);
    }

    @Override
    public Resume get(String uuid) throws IOException {
        SK searchKey = getExistSearchKey(uuid);
        return getFromStorage(searchKey);
    }

    private SK getExistSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    @Override
    public List<Resume> getAllSorted() throws IOException {
        List<Resume> sortedList = copyAllSorted();
        Collections.sort(sortedList);
        return sortedList;
    }

    protected abstract List<Resume> copyAllSorted() throws IOException;

    protected abstract boolean isExist(SK searchKey);

    protected abstract void updateToStorage(SK searchKey, Resume resume);

    protected abstract void saveToStorage(SK searchKey, Resume resume) throws IOException;

    protected abstract void deleteFromStorage(SK searchKey);

    protected abstract Resume getFromStorage(SK searchKey) throws IOException;

    protected abstract SK getSearchKey(String uuid);

}
