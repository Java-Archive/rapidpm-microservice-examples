package junit.org.rapidpm.microservice.demo.servlet;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.demo.service.ServiceImplA;
import org.rapidpm.microservice.demo.servlet.ServletService;

import javax.servlet.annotation.WebServlet;

/**
 * Created by svenruppert on 28.07.15.
 */
public class ServletServiceTest001 {

  private String url = "http://127.0.0.1:"
      + Main.DEFAULT_SERVLET_PORT + "/"
      + Main.MYAPP
      + ServletService.class.getAnnotation(WebServlet.class).urlPatterns()[0];

  @Before
  public void setUp() throws Exception {
    DI.clearReflectionModel();
    Main.deploy();
    System.out.println("url = " + url);
  }


  @After
  public void tearDown() throws Exception {
    Main.stop();
    DI.clearReflectionModel();
  }


  @Test
  public void testServletGetReq002() throws Exception {
    final Content returnContent = Request.Get(url + "?txt=hello").execute().returnContent();
    Assert.assertEquals("hello" + ServiceImplA.class.getSimpleName(), returnContent.asString());
  }


}
