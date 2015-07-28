package org.rapidpm.microservice.demo.service;

/**
 * Created by svenruppert on 28.07.15.
 */
public class ServiceImplA implements Service {
  @Override
  public String workOn(final String txt) {
    return txt + this.getClass().getSimpleName();
  }
}
