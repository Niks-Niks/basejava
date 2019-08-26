package main.storage;

import main.exception.StorageException;
import main.model.Resume;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;
    private SerializationStrategy SerializationStrategy;

    public PathStorage(String directory, SerializationStrategy SerializationStrategy) {
        this.SerializationStrategy = SerializationStrategy;
        Objects.requireNonNull(directory, "directory must not be null");
        this.directory = Paths.get(directory);
        if (!Files.isDirectory(this.directory) || !Files.isWritable(this.directory)) {
            throw new IllegalArgumentException(directory + " is not directory or is not writable");
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::deleteFromStorage);
        } catch (IOException e) {
            throw new StorageException("Directory clear error", e);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Directory size error", e);
        }
    }

    @Override
    protected List<Resume> copyAllSorted() {
        try {
            return Files.list(directory).map(this::getFromStorage).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("List get error", e);
        }
    }

    @Override
    protected boolean isExist(Path searchPath) {
        return Files.exists(searchPath);
    }

    @Override
    protected void updateToStorage(Path searchPath, Resume resume) {
        try {
            SerializationStrategy.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(searchPath)));
        } catch (IOException e) {
            throw new StorageException("Path update error", searchPath.toAbsolutePath().toString(), e);
        }
    }

    @Override
    protected void saveToStorage(Path searchPath, Resume resume) {
        try {
            Files.createFile(searchPath);
        } catch (IOException e) {
            throw new StorageException("Path save error", searchPath.toAbsolutePath().toString(), e);
        }
        updateToStorage(searchPath, resume);
    }

    @Override
    protected void deleteFromStorage(Path searchPath) {
        try {
            Files.delete(searchPath);
        } catch (IOException e) {
            throw new StorageException("Path delete error", searchPath.toAbsolutePath().toString(), e);
        }
    }

    @Override
    protected Resume getFromStorage(Path searchPath) {
        try {
            return SerializationStrategy.doRead(new BufferedInputStream(Files.newInputStream(searchPath)));
        } catch (IOException e) {
            throw new StorageException("Path get error", searchPath.toAbsolutePath().toString(), e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }
}