package main.storage;

import main.exception.StorageException;
import main.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {

    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must be not null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected List<Resume> copyAllSorted() {
        List<Resume> list = new ArrayList<>();
        try {
            for (File read : directory.listFiles()) {
                list.add(doRead(read));
            }
        } catch (IOException e) {
            throw new StorageException("Directory read error", "in_getAll", e);
        }

        return null;
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void updateToStorage(File file, Resume resume) {
        try {
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("Directory write error", file.getName(), e);
        }
    }

    @Override
    protected void saveToStorage(File file, Resume resume) {
        try {
            file.createNewFile();
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("Directory write error", file.getName(), e);
        }
    }

    @Override
    protected void deleteFromStorage(File file) {
        if (file == null){
            throw new StorageException("Nothing to delete", "Error deleteFromFile");
        } else {
            file.delete();
        }
    }

    @Override
    protected Resume getFromStorage(File file) {
        Resume resume;
        try {
            resume = doRead(file);
        } catch (IOException e) {
            throw new StorageException("Directory read error", file.getName(), e);
        }
        return resume;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    public void clear() {
        directory.delete();
    }

    @Override
    public int size() {
        if (directory.list() == null) {
            throw new StorageException("Size == null", "Error size(File)");
        } else {
            return directory.list().length;
        }
    }

    protected abstract void doWrite(Resume resume, File file) throws IOException;

    protected abstract Resume doRead(File file) throws IOException;
}
