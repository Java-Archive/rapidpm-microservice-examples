package org.rapidpm.ddi;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import java.time.LocalDateTime;

/**
 * Created by svenruppert on 11.08.15.
 */
@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = false, displayName = "Exampl006")
@WebInitParam(name = "Resources", value = "http://virit.in/dawn/11")  //.addInitParam("Resources", "http://virit.in/dawn/11"))
@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
public class MyUIServlet extends VaadinServlet {

  public MyUIServlet() {
    System.out.println("MyUIServlet - LocalDateTime.now() = " + LocalDateTime.now());
  }


}
