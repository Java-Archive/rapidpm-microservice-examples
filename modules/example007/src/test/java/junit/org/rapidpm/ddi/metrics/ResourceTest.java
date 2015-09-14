package junit.org.rapidpm.ddi.metrics;

import org.jboss.resteasy.test.TestPortProvider;
import org.junit.*;
import org.rapidpm.microservice.Main;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;

/**
 * Created by svenruppert on 20.08.15.
 */
public class ResourceTest {
  @Before
  public void setUp() throws Exception {
    Main.deploy();
  }


  @After
  public void tearDown() throws Exception {
    Main.stop();
  }



  @Test @Ignore
  public void testGet001() throws Exception {
    Client client = ClientBuilder.newClient();
    //MicroRestApp Path = /base
    //Resource Path = /test

    final String restAppPath = "/rest";
    final String ressourcePath = "/metrics";
    final String generateURL = TestPortProvider.generateURL(restAppPath + ressourcePath);
    System.out.println("generateURL = " + generateURL);
    String val = client
        .target(generateURL)
        .request()
        .get(String.class);
    System.out.println("val = " + val);
    client.close();
  }
}