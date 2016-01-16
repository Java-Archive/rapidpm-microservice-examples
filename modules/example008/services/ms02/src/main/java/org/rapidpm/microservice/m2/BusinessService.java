package org.rapidpm.microservice.m2;

/**
 * Created by Sven Ruppert on 31.08.15.
 */

import org.rapidpm.ddi.Proxy;
import org.rapidpm.microservice.m2.remote.Service;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.time.LocalDateTime;

@Path("/businesservice")
public class BusinessService {

  @Inject @Proxy(virtual = true) Service service;

  @GET()
  @Path("{a}/{b}")
  @Produces("text/plain")
  public int add(@PathParam("a") int a, @PathParam("b") int b) {
    System.out.println("add called = " + LocalDateTime.now());
    return service.remoteAdd(a, b);
  }
}
