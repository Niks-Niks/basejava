package main.storage;

import main.exception.StorageException;
import main.model.Resume;
import main.storage.serializer.SerializationStrategy;

import java.io.*;
import java.util.*;

public class FileStorage extends AbstractStorage<File> {
    private File directory;
    private SerializationStrategy SerializationStrategy;

    public FileStorage(File directory, SerializationStrategy SerializationStrategy) {
        Objects.requireNonNull(directory, "directory must not be null");
        this.SerializationStrategy = SerializationStrategy;
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }


    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                deleteFromStorage(file);
            }
        }
    }

    @Override
    public int size() {
        String[] list = directory.list();
        if (list == null) {
            throw new StorageException("Directory size error");
        }
        return list.length;
    }

    @Override
    public List<Resume> copyAllSorted() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory listCopy error");
        }
        List<Resume> list = new ArrayList<>(files.length);
        for (File file : files) {
            list.add(getFromStorage(file));
        }
        return list;
    }

    @Override
    public boolean isExist(File file) {
        return file.exists();
    }

    @Override
    public void updateToStorage(File searchFile, Resume resume) {
        try {
            SerializationStrategy.doWrite(resume, new BufferedOutputStream(new FileOutputStream(searchFile)));
        } catch (IOException e) {
            throw new StorageException("File update error", resume.getUuid(), e);
        }
    }

    @Override
    public void saveToStorage(File searchFile, Resume resume) {
        try {
            searchFile.createNewFile();
        } catch (IOException e) {
            throw new StorageException("File create error " + searchFile.getAbsolutePath(), searchFile.getName(), e);
        }
        updateToStorage(searchFile, resume);
    }

    @Override
    public void deleteFromStorage(File searchFile) {
        if (searchFile.delete()) {
            System.out.println("file.txt файл был удален с корневой папки проекта");
        } else {
            System.out.println("Файл file.txt не был найден в корневой папке проекта");
        }
    }

    @Override
    public Resume getFromStorage(File searchFile) {
        try {
            return SerializationStrategy.doRead(new BufferedInputStream(new FileInputStream(searchFile)));
        } catch (IOException e) {
            throw new StorageException("Read get error", e);
        }
    }

    @Override
    public File getSearchKey(String uuid) {
        System.out.println(new File(directory, uuid));
        return new File(directory, uuid);
    }
}