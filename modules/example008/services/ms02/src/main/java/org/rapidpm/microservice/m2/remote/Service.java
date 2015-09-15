package org.rapidpm.microservice.m2.remote;

import org.rapidpm.proxybuilder.dynamicobjectadapter.DynamicObjectAdapterBuilder;

/**
 * Created by svenruppert on 31.08.15.
 */
@DynamicObjectAdapterBuilder
public interface Service {
  int remoteAdd(int a,int b);
}
