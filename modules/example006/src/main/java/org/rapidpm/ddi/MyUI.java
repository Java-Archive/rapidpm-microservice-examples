package org.rapidpm.ddi;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import org.rapidpm.ddi.services.Service;

import javax.inject.Inject;
import java.time.LocalDateTime;

/**
 *
 */
@Theme("valo")
public class MyUI extends UI {

  public MyUI() {
    System.out.println("MyUI() - LocalDateTime.now() = " + LocalDateTime.now());
  }

  public MyUI(final Component content) {
    super(content);
    System.out.println("MyUI(content) - LocalDateTime.now() = " + LocalDateTime.now());
  }


  @Inject Service service;

  @Override
  protected void init(VaadinRequest vaadinRequest) {

//    final ServletContext servletContext = VaadinServlet.getCurrent().getServletContext();
//    final Collection<URL> urlsWebInfLib = ClasspathHelper.forWebInfLib(servletContext);
//    final Iterator<URL> iterator = urlsWebInfLib.iterator();
//    while (iterator.hasNext()) {
//      URL next = iterator.next();
//      if ( ! next.toString().contains("rapidpm")) iterator.remove();
//    }
//    DI.activatePackages("org.rapidpm", urlsWebInfLib);

    DI.activatePackages("org.rapidpm");
    //inject
    DI.activateDI(this);

    final VerticalLayout layout = new VerticalLayout();
    layout.setMargin(true);
    setContent(layout);

    Button button = new Button("Click Me");
    button.addClickListener(event -> layout.addComponent(new Label("Thank you for clicking " + service.doWork())));
    layout.addComponent(button);

  }


}
