package main.storage;

import main.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private List<Resume> storageList = new ArrayList<>();

    @Override
    protected List<Resume> copyAllSorted() {
        return new ArrayList<>(storageList);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected Resume getStorage(Object searchKey) {
        return storageList.get((Integer) searchKey);
    }

    @Override
    protected void updateToStorage(Object searchKey, Resume resume) {
        storageList.set((Integer) searchKey, resume);
    }

    @Override
    protected void saveToStorage(Object searchKey, Resume resume) {
        storageList.add(resume);
    }

    @Override
    protected void deleteFromStorage(Object searchKey) {
        storageList.remove((int) searchKey);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (uuid.equals(storageList.get(i).getUuid())) {
                return i;
            }
        }
        return null;
    }

    @Override
    public void clear() {
        storageList.clear();
    }

    @Override
    public int size() {
        return storageList.size();
    }
}
