package org.rapidpm.microservice.m2.remote;

import org.rapidpm.microservice.Main;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.time.LocalDateTime;

/**
 * Created by Sven Ruppert on 31.08.15.
 */
public class ServiceImpl implements Service {
  private static final String SERVICE_URL = "http://127.0.0.1:" + Main.DEFAULT_REST_PORT + Main.CONTEXT_PATH_REST + "/demoservice";
  private static final Client CLIENT = ClientBuilder.newClient(); // to expensive

  public ServiceImpl() {
    System.out.println("ServiceImpl.now() = " + LocalDateTime.now());
  }

  @Override
  public int remoteAdd(final int a, final int b) {
    final WebTarget webTarget = CLIENT.target(SERVICE_URL);
    return (Integer) webTarget.path(a + "").path(b + "").request().get().getEntity();
  }


}
