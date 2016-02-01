/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.rapidpm.microservice.example010.nodes.node01;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class Node01 {


  private Node01() {
  }

  public static void main(String[] args) throws IOException {

    final Config config = new Config();
    HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);
    Map<String, byte[]> mapCustomers = instance.getMap("distributed-classloader");

    String url = "org/rapidpm/microservice/example010/api/DemoServiceImplNode01.class";
//    URL classURL = new URL(url);
//    URLConnection classConnection = classURL.openConnection();

    final ClassLoader classLoader = Node01.class.getClassLoader();
    final InputStream resourceAsStream = classLoader.getResourceAsStream(url);
    final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    int data = resourceAsStream.read();
    while (data != -1) {
      buffer.write(data);
      data = resourceAsStream.read();
    }
    resourceAsStream.close();

    byte[] classData = buffer.toByteArray();
    System.out.println("classData = " + classData);

    mapCustomers.put(url, classData);


//    final DemoServiceImplNode01 demoServiceImplNode01 = new DemoServiceImplNode01();
//    Files.readAllBytes(Paths.get(filePath))

//    System.out.println("Customer with key 1: "+ mapCustomers.get(1));
//    System.out.println("Map Size:" + mapCustomers.size());
//
//    Queue<String> queueCustomers = instance.getQueue("customers");
//    queueCustomers.offer("Tom");
//    queueCustomers.offer("Mary");
//    queueCustomers.offer("Jane");
//    System.out.println("First customer: " + queueCustomers.poll());
//    System.out.println("Second customer: "+ queueCustomers.peek());
//    System.out.println("Queue size: " + queueCustomers.size());


  }


}
