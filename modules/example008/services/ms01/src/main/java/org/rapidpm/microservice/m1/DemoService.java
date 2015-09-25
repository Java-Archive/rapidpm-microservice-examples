package org.rapidpm.microservice.m1;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;


/**
 * Created by svenruppert on 31.08.15.
 */

@Path("/demoservice")
public class DemoService {

  @GET()
  @Path("{a}/{b}")
  @Produces("text/plain")
  public int addValues(@PathParam("a") int a, @PathParam("b") int b) {
    return a + b;
  }
}
