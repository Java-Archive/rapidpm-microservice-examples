package junit.org.rapidpm.microservice.m2;

import org.rapidpm.ddi.producer.Producer;
import org.rapidpm.microservice.m2.remote.Service;
import org.rapidpm.microservice.m2.remote.ServiceAdapterBuilder;

import javax.inject.Produces;

/**
 * Created by svenruppert on 02.09.15.
 */
@Produces(Service.class)
public class ServiceMockProducer implements Producer<Service> {
  @Override
  public Service create() {
    return ServiceAdapterBuilder.newBuilder()
        .setOriginal(null)
        .withRemoteAdd((a, b) -> a+b)
        .buildForTarget(Service.class);
  }
}
