package junit.org.rapidpm.ddi;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.junit.*;
import org.rapidpm.ddi.DI;
import org.rapidpm.ddi.MyUIServlet;
import org.rapidpm.ddi.reflections.ReflectionUtils;
import org.rapidpm.microservice.Main;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.util.Set;

/**
 * Created by svenruppert on 10.08.15.
 */
public class MyUITest {

  @Before
  public void setUp() throws Exception {
    DI.activatePackages("junit.org.rapidpm");
    Main.deploy();
  }

  @After
  public void tearDown() throws Exception {
    Main.stop();
  }


  private String url = "http://127.0.0.1:"+Main.DEFAULT_SERVLET_PORT
      + Main.MYAPP
      + MyUIServlet.class.getAnnotation(WebServlet.class).urlPatterns()[0];

  @Test @Ignore
  public void test001() throws Exception {
    final Set<Class<?>> typesAnnotatedWith = DI.getTypesAnnotatedWith(WebServlet.class);
    for (Class<?> aClass : typesAnnotatedWith) {
      Assert.assertEquals(MyUIServlet.class, aClass);
    }
  }


  @Test
  public void test002() throws Exception {
    final boolean b = new ReflectionUtils().checkInterface(MyUIServlet.class, HttpServlet.class);
    Assert.assertTrue(b);
  }

  @Test
  public void test003() throws Exception {
    System.out.println("url = " + url);
    final Content returnContent = Request.Get(url).execute().returnContent();
    final String actual = returnContent.asString();
//    System.out.println("actual = " + actual);
//    Thread.sleep(10_000);
//    Request.Post("http://targethost/login")
//        .bodyForm(Form.form().add("username",  "vip").add("password",  "secret").build())
//        .execute().returnContent();

  }

}
