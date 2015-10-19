package junit.org.rapidpm.microservice.filestoredemo.storage;

import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.rapidpm.ddi.Produces;
import org.rapidpm.ddi.producer.Producer;
import org.rapidpm.microservice.filestoredemo.api.FileStorage;
import org.rapidpm.microservice.filestoredemo.api.FileStoreServiceMessage;

import java.io.File;
import java.io.FileNotFoundException;


/**
 * Created by b.bosch on 13.10.2015.
 */
public class InMemoryFileStore implements FileStorage {

    static DB db = DBMaker.newHeapDB().closeOnJvmShutdown().make();
    private final BTreeMap<String, byte[]> test = db.getTreeMap("test");

    @Override
    public void archiveFile(FileStoreServiceMessage message) {
        test.put(message.fileName, message.fileContend);
        db.commit();
    }

    @Override
    public boolean isFileArchived(String filename) {
        return test.containsKey(filename);
    }

    @Override
    public String getFileFromArchive(String filename) throws FileNotFoundException {
        if (test.containsKey(filename)) {
            return new String(test.get(filename));
        }
        throw new FileNotFoundException("File not found in storage");

    }

    @Produces(FileStorage.class)
    public static  class FileStorageProducer implements Producer<FileStorage> {

        @Override
        public FileStorage create() {
            InMemoryFileStore inMemoryFileStore = new InMemoryFileStore();
            return inMemoryFileStore;
        }
    }
}
