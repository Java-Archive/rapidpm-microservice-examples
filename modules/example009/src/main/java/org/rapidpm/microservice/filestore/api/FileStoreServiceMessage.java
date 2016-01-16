package org.rapidpm.microservice.filestore.api;

/**
 * Created by Sven Ruppert on 13.10.2015.
 */
public class FileStoreServiceMessage {
    public byte[] fileContend;
    public String fileName;
    public FileStoreAction action;
}
