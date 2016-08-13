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

package junit.org.rapidpm.microservice.demo.rest;

import org.jboss.resteasy.test.TestPortProvider;
import org.junit.*;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.MainUndertow;
import org.rapidpm.microservice.demo.service.ServiceImplA;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class RestServiceTest001 {

  @BeforeClass
  public static void setUpClass() {
    final PortUtils portUtils = new PortUtils();
    System.setProperty(MainUndertow.REST_PORT_PROPERTY, portUtils.nextFreePortForTest() + "");
    System.setProperty(MainUndertow.SERVLET_PORT_PROPERTY, portUtils.nextFreePortForTest() + "");
    System.setProperty(MainUndertow.REST_HOST_PROPERTY, "127.0.0.1");
    System.setProperty(MainUndertow.SERVLET_HOST_PROPERTY, "127.0.0.1");

  }

  @Before
  public void setUp() throws Exception {
    Main.deploy();
  }


  @After
  public void tearDown() throws Exception {
    Main.stop();
  }

  @Test
  public void testApplicationPath() throws Exception {
    Client client = ClientBuilder.newClient();
    //MicroRestApp Path = /base
    //Resource Path = /test

    final String restAppPath = "/rest";
    final String ressourcePath = "/restservice";
    final String generateURL = TestPortProvider.generateURL(restAppPath + ressourcePath);
    System.out.println("generateURL = " + generateURL);
    String val = client
        .target(generateURL)
        .queryParam("txt", "hello")
        .request()
        .get(String.class);
    Assert.assertEquals("hello" + ServiceImplA.class.getSimpleName(), val);
    client.close();
  }

}
