package org.rapidpm.ddi.services;


import org.rapidpm.proxybuilder.objectadapter.annotations.dynamicobjectadapter.DynamicObjectAdapterBuilder;

/**
 * Created by svenruppert on 10.08.15.
 */
@DynamicObjectAdapterBuilder
public interface Service {

  String doWork();
}
