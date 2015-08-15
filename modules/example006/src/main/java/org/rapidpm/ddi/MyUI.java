package org.rapidpm.ddi;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import org.rapidpm.ddi.services.Service;
import org.reflections.util.ClasspathHelper;

import javax.inject.Inject;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 *
 */
@Theme("mytheme")
@Widgetset("org.rapidpm.ddi.MyAppWidgetset")
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
    final Collection<URL> urlsWebInfLib = ClasspathHelper.forWebInfLib(VaadinServlet.getCurrent().getServletContext());

//    final Iterator<URL> iterator = urlsWebInfLib.iterator();
//    while (iterator.hasNext()) {
//      URL next = iterator.next();
//      if ( ! next.toString().contains("rapidpm")) iterator.remove();
//    }

    System.out.println("LocalDateTime.now() = " + LocalDateTime.now());
    DI.activatePackages("org.rapidpm", urlsWebInfLib);
    System.out.println("LocalDateTime.now() = " + LocalDateTime.now());

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
