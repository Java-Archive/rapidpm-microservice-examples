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

package junit.org.rapidpm.microservice.filestore.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.org.rapidpm.microservice.filestore.BaseMicroserviceTest;
import org.jboss.resteasy.test.TestPortProvider;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.rapidpm.microservice.filestore.api.FileStoreAction;
import org.rapidpm.microservice.filestore.api.FileStoreResponse;
import org.rapidpm.microservice.filestore.api.FileStoreServiceMessage;
import org.rapidpm.microservice.filestore.api.StorageStatus;
import org.rapidpm.microservice.filestore.impl.RequestEncodingHelper;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestTest extends BaseMicroserviceTest{


    private final String FILE_CONTENT = "Test Hallo";

    @Test
    public void testRest001() throws Exception {
        String jsonBase64 = RequestEncodingHelper.encodeIntoBase64(getRestoreJson());
        String response = testJsonRequest(jsonBase64);
        final FileStoreResponse fileStoreResponse = RequestEncodingHelper.parseJsonToMessage(response, FileStoreResponse.class);
        Assert.assertEquals(StorageStatus.NOT_ARCHIVED, fileStoreResponse.status);

    }

    private String getRestoreJson() throws JsonProcessingException {
        FileStoreServiceMessage message = new FileStoreServiceMessage();

        message.action = FileStoreAction.RESTORE;
        message.fileName = "TEST.XML";

        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(message);

    }

    private String testJsonRequest(String jsonBase64) {
        Client client = ClientBuilder.newClient();
        final String restAppPath = "/rest";
        final String ressourcePath = "/filestore";
        final String generateURL = TestPortProvider.generateURL(restAppPath + ressourcePath);
        System.out.println("generateURL = " + generateURL);

        String val = client
                .target(generateURL)
                .queryParam("json", jsonBase64)
                .request()
                .get(String.class);
        Assert.assertNotNull(val);
        client.close();
        return val;
    }

    @Test
    public void testRest002() throws Exception {
        final String jsonBase64 = RequestEncodingHelper.encodeIntoBase64(getInsertJson());
        String response = testJsonRequest(jsonBase64);
        final FileStoreResponse fileStoreResponse = RequestEncodingHelper.parseJsonToMessage(response, FileStoreResponse.class);
        Assert.assertEquals(StorageStatus.ARCHIVED, fileStoreResponse.status);
    }

    private String getInsertJson() throws JsonProcessingException {
        FileStoreServiceMessage message = new FileStoreServiceMessage();

        message.action = FileStoreAction.ARCHIVE;
        message.fileName = "TEST.XML";
        message.fileContend = RequestEncodingHelper.encodeIntoBase64(FILE_CONTENT).getBytes();

        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(message);

    }

    @Test
    public void testRest003() throws JsonProcessingException {
        String jsonBase64 = RequestEncodingHelper.encodeIntoBase64(getRestoreJson());
        String response = testJsonRequest(jsonBase64);
        final FileStoreResponse fileStoreResponse = RequestEncodingHelper.parseJsonToMessage(response, FileStoreResponse.class);
        Assert.assertEquals(StorageStatus.RESTORED, fileStoreResponse.status);
        Assert.assertEquals(FILE_CONTENT, RequestEncodingHelper.decodeFromBase64(fileStoreResponse.base64File));
    }

    @Test
    public void testRest004() throws Exception {
        String jsonBase64 = RequestEncodingHelper.encodeIntoBase64(getCheckJson());
        String response = testJsonRequest(jsonBase64);
        final FileStoreResponse fileStoreResponse = RequestEncodingHelper.parseJsonToMessage(response, FileStoreResponse.class);
        Assert.assertEquals(StorageStatus.ALREADY_ARCHIVED, fileStoreResponse.status);
    }

    private String getCheckJson() throws JsonProcessingException {
        FileStoreServiceMessage message = new FileStoreServiceMessage();

        message.action = FileStoreAction.CHECK;
        message.fileName = "TEST.XML";


        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(message);

    }
}
