package junit.org.rapidpm.microservice.demo;

import com.google.gson.Gson;
import org.jboss.resteasy.test.TestPortProvider;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.demo.model.DataHolder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * Created by svenruppert on 07.07.15.
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
  public void testApplicationPath() throws Exception {
    Client client = ClientBuilder.newClient();
    //MicroRestApp Path = /base
    //Resource Path = /test

    final String restAppPath = "/rest";
    final String ressourcePath = "/pojo";
    final String generateURL = TestPortProvider.generateURL(restAppPath + ressourcePath);
    System.out.println("generateURL = " + generateURL);

    String val = client
        .target(generateURL)
        .request()
        .get(String.class);

    client.close();

    Assert.assertNotNull(val);
    Assert.assertFalse(val.isEmpty());
    Assert.assertEquals("{\"txtA\":\"A\",\"txtb\":\"B\"}", val);

    Gson gson = new Gson();
    final DataHolder dataHolder = gson.fromJson(val, DataHolder.class);
    Assert.assertNotNull(dataHolder);
    Assert.assertEquals("A", dataHolder.getTxtA());
    Assert.assertEquals("B", dataHolder.getTxtb());
  }





}
