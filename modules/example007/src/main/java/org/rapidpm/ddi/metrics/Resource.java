package org.rapidpm.ddi.metrics;


import com.google.gson.Gson;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

//import javax.inject.Inject;

/**
 * Created by Sven Ruppert on 27.05.15.
 */
@Path("/metrics")
public class Resource {


  @GET()
  @Produces("text/plain")
  public String get() {
    return
        new Gson().toJson(
            Metrics.getInstance().data);
  }


}
