package junit.org.rapidpm.microservice.filestoredemo.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.resteasy.test.TestPortProvider;
import org.junit.*;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.filestoredemo.api.FileStoreAction;
import org.rapidpm.microservice.filestoredemo.api.FileStoreServiceMessage;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.xml.bind.DatatypeConverter;
import java.util.Base64;

/**
 * Created by b.bosch on 13.10.2015.
 */
public class RestTest {

    @Before
    public void setUp() throws Exception {
        Main.deploy();
    }


    @After
    public void tearDown() throws Exception {
        Main.stop();
    }

    @Test
    public void testFirstInstert() throws Exception {
        String jsonBase64 = Base64.getEncoder().encodeToString(getInsertJson().getBytes());

        String response = testJsonRequest(jsonBase64);
        Assert.assertNotNull(response);
    }
    @Test
    public void testIfInsertWorked() throws Exception {
        String jsonBase64 = Base64.getEncoder().encodeToString(getCheckJson().getBytes());

        String response = testJsonRequest(jsonBase64);
        Assert.assertNotNull(response);
    }

    @Test
    public void testRestore() throws Exception {
        String jsonBase64 = Base64.getEncoder().encodeToString(getRestoreJson().getBytes());

        String response = testJsonRequest(jsonBase64);
        Assert.assertNotNull(response);
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

    private String getInsertJson() throws JsonProcessingException {
        FileStoreServiceMessage message = new FileStoreServiceMessage();

        message.action = FileStoreAction.ARCHIVE;
        message.fileName = "TEST.XML";
        message.fileContend = DatatypeConverter.printBase64Binary("Test Hallo".getBytes()).getBytes();

        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(message);

    }

    private String getCheckJson() throws JsonProcessingException {
        FileStoreServiceMessage message = new FileStoreServiceMessage();

        message.action = FileStoreAction.CHECK;
        message.fileName = "TEST.XML";


        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(message);

    }

    private String getRestoreJson() throws JsonProcessingException {
        FileStoreServiceMessage message = new FileStoreServiceMessage();

        message.action = FileStoreAction.RESTORE;
        message.fileName = "TEST.XML";

        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(message);

    }
}
