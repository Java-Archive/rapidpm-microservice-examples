package junit.org.rapidpm.microservice.demo;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.junit.*;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.demo.servlet.MessageServlet;
import org.rapidpm.microservice.test.PortUtils;

import javax.servlet.annotation.WebServlet;


/**
 * Created by sven on 27.05.15.
 */
public class ServletTest {


  private static String url;
  private final String USER_AGENT = "Mozilla/5.0";

  @BeforeClass
  public static void setUpClass() {
    final PortUtils portUtils = new PortUtils();
    System.setProperty(Main.REST_PORT_PROPERTY, portUtils.nextFreePortForTest() + "");
    System.setProperty(Main.SERVLET_PORT_PROPERTY, portUtils.nextFreePortForTest() + "");
    url = "http://127.0.0.1:" + System.getProperty(Main.SERVLET_PORT_PROPERTY) + "/"
        + Main.MYAPP
        + MessageServlet.class.getAnnotation(WebServlet.class).urlPatterns()[0];
    Main.deploy();
  }

  @AfterClass
  public static void tearDown() throws Exception {
    Main.stop();
  }

  @Before
  public void setUp() throws Exception {

  }

  @Test
  public void testServletGetReq001() throws Exception {
    final Content returnContent = Request
        .Get(url)
        .execute().returnContent();
    Assert.assertEquals("Hello World CDI Service", returnContent.asString());
  }

  @Test
  public void testServletPostRequest() throws Exception {
    final Content returnContent = Request
        .Post(url)
        .execute().returnContent();
    Assert.assertEquals("Hello World CDI Service", returnContent.asString());
  }


}
