package org.rapidpm.microservice.filestore.api;


import java.io.FileNotFoundException;

/**
 * Created by Sven Ruppert on 13.10.2015.
 */
public interface FileStorage {

    void archiveFile(FileStoreServiceMessage message);

    boolean isFileArchived(String filename);

    String getFileFromArchive(String filename) throws FileNotFoundException;
}


