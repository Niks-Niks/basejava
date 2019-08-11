package main.storage;

import main.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {

    private Map<String, Resume> storageMap = new HashMap<>();

    @Override
    public void clear() {
        storageMap.clear();
    }

    @Override
    protected List<Resume> copyAllSorted() {
        return new ArrayList<>(storageMap.values());
    }

    @Override
    public int size() {
        return storageMap.size();
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected void updateToStorage(Object searchKey, Resume resume) {
        storageMap.put(resume.getUuid(), resume);
    }

    @Override
    protected void saveToStorage(Object searchKey, Resume resume) {
        storageMap.put(resume.getUuid(), resume);
    }

    @Override
    protected void deleteFromStorage(Object searchKey) {
        storageMap.remove(((Resume) searchKey).getUuid());
    }

    @Override
    protected Resume getStorage(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return storageMap.get(uuid);
    }
}
