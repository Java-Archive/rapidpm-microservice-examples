package org.rapidpm.microservice.demo.service;

import javax.inject.Inject;

/**
 * Created by Sven Ruppert on 28.07.15.
 */
public class BusinessModule {

  @Inject Service service;

  public String doWork(String txt) {
    return service.workOn(txt);
  }

}
