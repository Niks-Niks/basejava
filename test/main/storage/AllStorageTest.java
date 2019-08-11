package main.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SortedArrayStorageTest.class,
        ArrayStorageTest.class,
        ListStorageTest.class,
        MapStorageTest.class,
        MapResumeStorageTest.class
})

public class AllStorageTest {

}
