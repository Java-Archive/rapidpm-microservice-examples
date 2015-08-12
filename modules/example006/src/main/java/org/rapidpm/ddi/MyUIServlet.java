package org.rapidpm.ddi;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

import javax.servlet.annotation.WebServlet;
import java.time.LocalDateTime;

/**
 * Created by svenruppert on 11.08.15.
 */
@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = false)
@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
public class MyUIServlet extends VaadinServlet {

  public MyUIServlet() {
    System.out.println("MyUIServletInternal - LocalDateTime.now() = " + LocalDateTime.now());
  }
}
