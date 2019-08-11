package main.storage;

import main.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {

    private Map<String, Resume> storageMap = new HashMap<>();

    @Override
    protected List<Resume> copyAllSorted() {
        return new ArrayList<>(storageMap.values());
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storageMap.containsKey(searchKey);
    }

    @Override
    protected void updateToStorage(Object searchKey, Resume resume) {
        storageMap.replace((String) searchKey, resume);
    }

    @Override
    protected void saveToStorage(Object searchKey, Resume resume) {
        storageMap.put(searchKey.toString(), resume);
    }

    @Override
    protected void deleteFromStorage(Object searchKey) {
        storageMap.remove(searchKey);
    }

    @Override
    protected Resume getStorage(Object searchKey) {
        return storageMap.get(searchKey);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    public void clear() {
        storageMap.clear();
    }

    @Override
    public int size() {
        return storageMap.size();
    }
}

