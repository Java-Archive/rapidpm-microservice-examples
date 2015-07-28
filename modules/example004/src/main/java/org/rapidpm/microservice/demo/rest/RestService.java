package org.rapidpm.microservice.demo.rest;

import org.rapidpm.microservice.demo.service.BusinessModule;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 * Created by svenruppert on 28.07.15.
 */
@Path("restservice")
public class RestService {

  @Inject BusinessModule businessModule;


  @GET()
  @Produces("text/plain")
  public String workingOn(@QueryParam("txt") String txt) {
    return businessModule.doWork(txt);
  }

}
