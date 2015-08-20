package org.rapidpm.ddi;

import javax.servlet.annotation.WebListener;
import java.time.LocalDateTime;

/**
 * Created by svenruppert on 20.08.15.
 */
@WebListener
public class MyListener extends in.virit.WidgetSet {

  public MyListener() {
    System.out.println("LocalDateTime.now() = " + LocalDateTime.now());
  }
}
