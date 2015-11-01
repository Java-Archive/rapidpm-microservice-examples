package org.rapidpm.microservice.filestoredemo.impl;

import org.rapidpm.microservice.filestoredemo.api.FileStoreResponse;
import org.rapidpm.microservice.filestoredemo.api.FileStoreServiceMessage;
import org.rapidpm.microservice.filestoredemo.api.StorageStatus;
import org.rapidpm.microservice.filestoredemo.api.FileStorage;

import javax.inject.Inject;
import java.io.FileNotFoundException;

/**
 * Created by b.bosch on 13.10.2015.
 */
public class FileStoreService {

    @Inject
    FileStorage fileStorage;

    public FileStoreResponse handleMessage(FileStoreServiceMessage message) throws UnknownActionException {
        FileStoreResponse fileStoreResponse;
        switch (message.action) {
            case ARCHIVE:
                fileStoreResponse = checkAndArchive(message);
                break;
            case CHECK:
                fileStoreResponse = checkIfArchived(message);
                break;
            case RESTORE:
                fileStoreResponse = restore(message);
                break;
            default:
                throw new UnknownActionException(String.format("Action <%s> Unknown to service <%s>", message.action, this.getClass().getSimpleName()));
        }
        return fileStoreResponse;
    }

    private FileStoreResponse restore(FileStoreServiceMessage message) {
        FileStoreResponse response = new FileStoreResponse();
        try {
            response.base64File = fileStorage.getFileFromArchive(message.fileName);
            response.status = StorageStatus.RESTORED;
        } catch (FileNotFoundException e) {
            response.status = StorageStatus.NOT_ARCHIVED;
        }

        return response;
    }

    private FileStoreResponse checkIfArchived(FileStoreServiceMessage message) {
        FileStoreResponse response = new FileStoreResponse();
        if (fileStorage.isFileArchived(message.fileName)) {
            response.status = StorageStatus.ALREADY_ARCHIVED;
        } else {
            response.status = StorageStatus.NOT_ARCHIVED;
        }
        return response;
    }

    private FileStoreResponse checkAndArchive(FileStoreServiceMessage message) {
        FileStoreResponse response = new FileStoreResponse();
        if (!fileStorage.isFileArchived(message.fileName)) {
            fileStorage.archiveFile(message);
            response.status = StorageStatus.ARCHIVED;
        } else {
            response.status = StorageStatus.ALREADY_ARCHIVED;
        }
        return response;
    }


}
