package org.rapidpm.ddi.vaadin;

import com.vaadin.server.VaadinSession;

import java.util.Map;

/**
 * Created by svenruppert on 20.08.15.
 */
public class SessionUtils {

  public void printSessionAttributes() {
    final VaadinSession session = VaadinSession.getCurrent();
    try {
      final java.lang.reflect.Field attributesField = VaadinSession.class.getDeclaredField("attributes");
      final boolean accessible = attributesField.isAccessible();
      attributesField.setAccessible(true);
      final Object o = attributesField.get(session);
      //Map<String, Object
      if (o instanceof Map) {
        Map attributes = (Map) o;
        attributes.forEach((key, value) -> {
          System.out.println("key = " + key);
          System.out.println("value = " + value);
        });
      }
      attributesField.setAccessible(accessible);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }
}
