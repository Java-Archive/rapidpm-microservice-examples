package org.rapidpm.ddi;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import org.rapidpm.ddi.services.Service;

import javax.inject.Inject;
import java.time.LocalDateTime;

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
//    DI.addNewClassLoaderAndMerge(Service.class.getClassLoader());

    //inject
    DI.getInstance().activateDI(this);

    final VerticalLayout layout = new VerticalLayout();
    layout.setMargin(true);
    setContent(layout);

    Button button = new Button("Click Me");
    button.addClickListener(event -> layout.addComponent(new Label("Thank you for clicking " + service.doWork())));
    layout.addComponent(button);

  }


}
