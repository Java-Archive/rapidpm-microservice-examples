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

package junit.org.rapidpm.microservice.filestore.storage;

import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.rapidpm.ddi.Produces;
import org.rapidpm.ddi.producer.Producer;
import org.rapidpm.microservice.filestore.api.FileStorage;
import org.rapidpm.microservice.filestore.api.FileStoreServiceMessage;

import java.io.FileNotFoundException;


public class InMemoryFileStore implements FileStorage {

    static DB db = DBMaker.memoryDB().make();
    private final BTreeMap<String, byte[]> test = (BTreeMap<String, byte[]>) db.treeMap("test").createOrOpen();


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
