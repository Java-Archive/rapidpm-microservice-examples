package org.rapidpm.microservice.m2.remote;


import org.rapidpm.proxybuilder.objectadapter.annotations.dynamicobjectadapter.DynamicObjectAdapterBuilder;

/**
 * Created by Sven Ruppert on 31.08.15.
 */
@DynamicObjectAdapterBuilder
public interface Service {
  int remoteAdd(int a, int b);
}
