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

package junit.org.rapidpm.microservice.filestore.servlet;

import junit.org.rapidpm.microservice.filestore.BaseMicroserviceTest;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.junit.Assert;
import org.junit.Test;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.filestore.api.FileStoreAction;
import org.rapidpm.microservice.filestore.api.FileStoreResponse;
import org.rapidpm.microservice.filestore.api.FileStoreServiceMessage;
import org.rapidpm.microservice.filestore.api.StorageStatus;
import org.rapidpm.microservice.filestore.impl.RequestEncodingHelper;
import org.rapidpm.microservice.filestore.servlet.ServletService;

import javax.servlet.annotation.WebServlet;
import javax.xml.bind.JAXB;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class ServletTest extends BaseMicroserviceTest{

    @Test
    public void testServlet001() throws Exception {
      String request = String.format("%s?xml=%s", createURL(), getBase64ArchiveXml());
        final Content returnContent = Request.Get(request).execute().returnContent();

        String response = returnContent.asString();
        StringReader reader = new StringReader(response);
        FileStoreResponse fileStoreResponse = JAXB.unmarshal(reader, FileStoreResponse.class);
        Assert.assertEquals(StorageStatus.ARCHIVED, fileStoreResponse.status);
    }

  private String createURL() {
    return "http://127.0.0.1:"
        + System.getProperty(Main.SERVLET_PORT_PROPERTY) + "/"
        + Main.MYAPP
        + ServletService.class.getAnnotation(WebServlet.class).urlPatterns()[0];
  }

  private String getBase64ArchiveXml() throws UnsupportedEncodingException {
    FileStoreServiceMessage message = new FileStoreServiceMessage();
    message.action = FileStoreAction.ARCHIVE;
    message.fileName = "test.xml";

    message.fileContend = Base64.getEncoder().encode("Hello World".getBytes());
    final String messageToXml = RequestEncodingHelper.serializeMessageToXml(message);
    final String encodedXml = RequestEncodingHelper.encodeIntoBase64(messageToXml);
    return encodedXml;

  }

    @Test
    public void testServlet002() throws Exception {
      String request = String.format("%s?xml=%s", createURL(), getBase64CheckIfArchiveXml());
        final Content returnContent = Request.Get(request).execute().returnContent();

        String response = returnContent.asString();
        StringReader reader = new StringReader(response);
        FileStoreResponse fileStoreResponse = JAXB.unmarshal(reader, FileStoreResponse.class);
        Assert.assertEquals(StorageStatus.ALREADY_ARCHIVED, fileStoreResponse.status);
    }

  private String getBase64CheckIfArchiveXml() throws UnsupportedEncodingException {
    FileStoreServiceMessage message = new FileStoreServiceMessage();
    message.action = FileStoreAction.CHECK;
    message.fileName = "test.xml";
    final String messageToXml = RequestEncodingHelper.serializeMessageToXml(message);
    final String encodedXml = RequestEncodingHelper.encodeIntoBase64(messageToXml);
    return encodedXml;
  }

    @Test
    public void testServlet003() throws Exception {
      String request = String.format("%s?xml=%s", createURL(), getBase64RestoreFromArchiveXml());
        final Content returnContent = Request.Get(request).execute().returnContent();

        String response = returnContent.asString();
        StringReader reader = new StringReader(response);
        FileStoreResponse fileStoreResponse = JAXB.unmarshal(reader, FileStoreResponse.class);
        Assert.assertEquals(StorageStatus.RESTORED, fileStoreResponse.status);
        String fileContentAsString = new String(Base64.getDecoder().decode(fileStoreResponse.base64File));
        Assert.assertEquals(fileContentAsString, "Hello World");
    }

    private String getBase64RestoreFromArchiveXml() throws UnsupportedEncodingException {
        FileStoreServiceMessage message = new FileStoreServiceMessage();
        message.action = FileStoreAction.RESTORE;
        message.fileName = "test.xml";
        final String messageToXml = RequestEncodingHelper.serializeMessageToXml(message);
        final String encodedXml = RequestEncodingHelper.encodeIntoBase64(messageToXml);
        return encodedXml;
    }

}
