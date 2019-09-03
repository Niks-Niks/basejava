package main.storage;

import main.storage.serializer.SerializationStrategy;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {
    public ObjectStreamFileStorageTest() {
        super(new FileStorage(STORAGE_DIRECTORY, new SerializationStrategy()));
    }
}