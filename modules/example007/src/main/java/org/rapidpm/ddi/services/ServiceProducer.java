package org.rapidpm.ddi.services;

import org.rapidpm.ddi.producer.Producer;

import javax.inject.Produces;
import java.time.LocalDateTime;

/**
 * Created by svenruppert on 10.08.15.
 */
@Produces(Service.class)
public class ServiceProducer implements Producer<Service> {

  @Override
  public Service create() {
    return ServiceAdapterBuilder
        .newBuilder()
        .setOriginal(null)
        .withDoWork(() -> "mockedHoppel-" + LocalDateTime.now())
        .buildForTarget(Service.class);
  }
}
