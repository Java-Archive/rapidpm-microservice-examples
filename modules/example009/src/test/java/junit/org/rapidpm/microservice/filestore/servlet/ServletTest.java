package junit.org.rapidpm.microservice.filestoredemo.servlet;

import junit.org.rapidpm.microservice.filestoredemo.BaseMicroserviceTest;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.filestoredemo.api.FileStoreAction;
import org.rapidpm.microservice.filestoredemo.api.FileStoreResponse;
import org.rapidpm.microservice.filestoredemo.api.FileStoreServiceMessage;
import org.rapidpm.microservice.filestoredemo.api.StorageStatus;
import org.rapidpm.microservice.filestoredemo.impl.RequestEncodingHelper;
import org.rapidpm.microservice.filestoredemo.servlet.ServletService;

import javax.servlet.annotation.WebServlet;
import javax.xml.bind.JAXB;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * Created by b.bosch on 13.10.2015.
 */
public class ServletTest extends BaseMicroserviceTest{

    private final String url = "http://127.0.0.1:"
            + Main.DEFAULT_SERVLET_PORT + "/"
            + Main.MYAPP
            + ServletService.class.getAnnotation(WebServlet.class).urlPatterns()[0];



    @Test
    public void testServlet001() throws Exception {
        String request = String.format("%s?xml=%s", url, getBase64ArchiveXml());
        final Content returnContent = Request.Get(request).execute().returnContent();

        String response = returnContent.asString();
        StringReader reader = new StringReader(response);
        FileStoreResponse fileStoreResponse = JAXB.unmarshal(reader, FileStoreResponse.class);
        Assert.assertEquals(StorageStatus.ARCHIVED, fileStoreResponse.status);
    }

    @Test
    public void testServlet002() throws Exception {
        String request = String.format("%s?xml=%s", url, getBase64CheckIfArchiveXml());
        final Content returnContent = Request.Get(request).execute().returnContent();

        String response = returnContent.asString();
        StringReader reader = new StringReader(response);
        FileStoreResponse fileStoreResponse = JAXB.unmarshal(reader, FileStoreResponse.class);
        Assert.assertEquals(StorageStatus.ALREADY_ARCHIVED, fileStoreResponse.status);
    }

    @Test
    public void testServlet003() throws Exception {
        String request = String.format("%s?xml=%s", url, getBase64RestoreFromArchiveXml());
        final Content returnContent = Request.Get(request).execute().returnContent();

        String response = returnContent.asString();
        StringReader reader = new StringReader(response);
        FileStoreResponse fileStoreResponse = JAXB.unmarshal(reader, FileStoreResponse.class);
        Assert.assertEquals(StorageStatus.RESTORED, fileStoreResponse.status);
        String fileContentAsString = new String(Base64.getDecoder().decode(fileStoreResponse.base64File));
        Assert.assertEquals(fileContentAsString, "Hello World");
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

    private String getBase64CheckIfArchiveXml() throws UnsupportedEncodingException {
        FileStoreServiceMessage message = new FileStoreServiceMessage();
        message.action = FileStoreAction.CHECK;
        message.fileName = "test.xml";
        final String messageToXml = RequestEncodingHelper.serializeMessageToXml(message);
        final String encodedXml = RequestEncodingHelper.encodeIntoBase64(messageToXml);
        return encodedXml;
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
