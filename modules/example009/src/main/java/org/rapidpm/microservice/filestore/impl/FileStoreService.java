/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.rapidpm.microservice.filestore.impl;

import org.rapidpm.microservice.filestore.api.FileStorage;
import org.rapidpm.microservice.filestore.api.FileStoreResponse;
import org.rapidpm.microservice.filestore.api.FileStoreServiceMessage;
import org.rapidpm.microservice.filestore.api.StorageStatus;

import javax.inject.Inject;
import java.io.FileNotFoundException;

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

    private FileStoreResponse checkIfArchived(FileStoreServiceMessage message) {
        FileStoreResponse response = new FileStoreResponse();
        if (fileStorage.isFileArchived(message.fileName)) {
            response.status = StorageStatus.ALREADY_ARCHIVED;
        } else {
            response.status = StorageStatus.NOT_ARCHIVED;
        }
        return response;
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


}
