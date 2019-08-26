package main.storage;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {

    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIRECTORY.getAbsolutePath(), new SerializationStrategy()));
    }
}