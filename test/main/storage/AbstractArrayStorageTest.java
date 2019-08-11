    package main.storage;

    import main.exception.StorageException;
    import main.model.Resume;
    import org.junit.Assert;
    import org.junit.Test;

    public class AbstractArrayStorageTest extends AbstractStorageTest {

        protected AbstractArrayStorageTest(Storage storage) {
            super(storage);
        }

        @Test(expected = StorageException.class)
        public void saveOverflow() throws Exception {
            storage.clear();
            try {
                for (int i = 1; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
                    storage.save(new Resume("fullName"));
                }
            } catch (StorageException e) {
                Assert.fail("Save overflow " + storage.size());
            }
            storage.save(new Resume("fullName"));
        }
    }
