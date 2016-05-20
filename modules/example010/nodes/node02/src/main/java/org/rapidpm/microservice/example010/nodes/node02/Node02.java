package org.rapidpm.microservice.example010.nodes.node02;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.rapidpm.microservice.example010.api.DemoService;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 *  Copyright (C) 2012 RapidPM
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * Created by RapidPM - Team on 15.01.16.
 */
public class Node02 {

  private static final Method DEFINE_CLASS_METHOD;
  private static final JavaCompiler JAVA_COMPILER;

  private Node02() {
  }

  static {
    try {
      DEFINE_CLASS_METHOD = Proxy.class.getDeclaredMethod("defineClass0", ClassLoader.class, String.class, byte[].class, int.class, int.class);
      DEFINE_CLASS_METHOD.setAccessible(true);
    } catch (NoSuchMethodException e) {
      throw new ExceptionInInitializerError(e);
    }
    JAVA_COMPILER = ToolProvider.getSystemJavaCompiler();
    if (JAVA_COMPILER == null) {
      throw new UnsupportedOperationException(
          "Cannot find java compiler! " +
              "Probably only JRE installed.");
    }
  }

  public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {

//    final Config config = new Config();
//    HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);
//    Map<String, byte[]> mapCustomers = instance.getMap("distributed-classloader");

    String url = "org/rapidpm/microservice/example010/api/DemoServiceImplNode01.class";


    final Class aClass = new DistributedClassLoader().loadClass(url);
    final DemoService demoService = (DemoService) aClass.newInstance();
    final String hallo = demoService.doWork("Hallo");
    System.out.println("hallo = " + hallo);


  }


  public static class DistributedClassLoader extends ClassLoader {

    private final Config config = new Config();
    private HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);
    private Map<String, byte[]> mapCustomers = instance.getMap("distributed-classloader");


    public Class loadClass(String name) throws ClassNotFoundException {
      final byte[] classData = mapCustomers.get(name);

      try {
        return (Class) DEFINE_CLASS_METHOD.invoke(null, DistributedClassLoader.class.getClassLoader(), null, classData, 0, classData.length);
      } catch (IllegalAccessException | InvocationTargetException e) {
        e.printStackTrace();
      }

      return null;
    }
  }


}
