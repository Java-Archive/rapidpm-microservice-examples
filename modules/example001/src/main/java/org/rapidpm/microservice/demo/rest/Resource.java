package org.rapidpm.microservice.demo.rest;


import org.rapidpm.microservice.demo.service.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

//import javax.inject.Inject;

/**
 * Created by sven on 27.05.15.
 */
@Path("/test")
public class Resource {

  public Service service = new Service();

  @GET()
  @Produces("text/plain")
  public String get() {
    return "Hello Rest World " + service.doWork();
  }


}
