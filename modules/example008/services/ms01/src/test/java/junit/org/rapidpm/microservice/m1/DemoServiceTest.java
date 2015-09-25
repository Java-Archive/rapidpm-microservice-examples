package junit.org.rapidpm.microservice.m1;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.m1.DemoService;
import org.rapidpm.microservice.test.RestUtils;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;

/**
 * Created by svenruppert on 31.08.15.
 */
public class DemoServiceTest {

  @Before
  public void setUp() throws Exception {
    DI.activatePackages("org.rapidpm");
    DI.activatePackages("junit.org.rapidpm");
    Main.deploy();
  }

  @After
  public void tearDown() throws Exception {
    Main.stop();
  }

  @Test
  public void testAddValues() throws Exception {

    final String generateBasicReqURL = new RestUtils().generateBasicReqURL(DemoService.class, Main.CONTEXT_PATH_REST);
    Client client = ClientBuilder.newClient();
    final Invocation.Builder request = client
        .target(generateBasicReqURL)
        .path("1")
        .path("2")
        .request();
    final Response response = request.get();
    Assert.assertEquals(200, response.getStatus());
    final Response.StatusType statusInfo = response.getStatusInfo();
    final String reasonPhrase = statusInfo.getReasonPhrase();
    Assert.assertEquals("OK", reasonPhrase);
    Integer result = response.readEntity(Integer.class);

    Assert.assertNotNull(result);
    Assert.assertEquals(Integer.valueOf(3), result);
    client.close();


  }
}