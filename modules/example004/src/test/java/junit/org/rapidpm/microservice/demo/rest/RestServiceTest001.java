package junit.org.rapidpm.microservice.demo.rest;

import org.jboss.resteasy.test.TestPortProvider;
import org.junit.*;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.demo.service.ServiceImplA;
import org.rapidpm.microservice.test.PortUtils;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * Created by svenruppert on 28.07.15.
 */
public class RestServiceTest001 {

  @BeforeClass
  public static void setUpClass() {
    final PortUtils portUtils = new PortUtils();
    System.setProperty(Main.REST_PORT_PROPERTY, portUtils.nextFreePortForTest() + "");
    System.setProperty(Main.SERVLET_PORT_PROPERTY, portUtils.nextFreePortForTest() + "");
  }

  @Before
  public void setUp() throws Exception {
    Main.deploy();
  }


  @After
  public void tearDown() throws Exception {
    Main.stop();
  }

  @Test
  public void testApplicationPath() throws Exception {
    Client client = ClientBuilder.newClient();
    //MicroRestApp Path = /base
    //Resource Path = /test

    final String restAppPath = "/rest";
    final String ressourcePath = "/restservice";
    final String generateURL = TestPortProvider.generateURL(restAppPath + ressourcePath);
    System.out.println("generateURL = " + generateURL);
    String val = client
        .target(generateURL)
        .queryParam("txt", "hello")
        .request()
        .get(String.class);
    Assert.assertEquals("hello" + ServiceImplA.class.getSimpleName(), val);
    client.close();
  }

}
