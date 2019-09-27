package main.storage;

import main.Config;

public class SqlStorageTest extends AbstractStorageTest{

    public SqlStorageTest() {
        super(Config.get().getStorage());
    }
}