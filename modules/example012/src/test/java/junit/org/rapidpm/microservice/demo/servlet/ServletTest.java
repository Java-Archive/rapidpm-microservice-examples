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

package junit.org.rapidpm.microservice.demo.servlet;

import junit.org.rapidpm.microservice.demo.BaseTest;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.jboss.resteasy.test.TestPortProvider;
import org.junit.*;
import org.rapidpm.dependencies.core.net.PortUtils;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.MainUndertow;
import org.rapidpm.microservice.demo.servlet.HelloServlet;

import javax.servlet.annotation.WebServlet;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class ServletTest extends BaseTest{


  private static String url;

  @Before
  public void setUp() throws Exception {
    super.setUp();
    url = "http://127.0.0.1:" + System.getProperty(MainUndertow.SERVLET_PORT_PROPERTY)
        + MainUndertow.MYAPP
        + HelloServlet.class.getAnnotation(WebServlet.class).urlPatterns()[0];
    System.out.println("generateURL = " + url);
  }

  @Test
  public void testServletGetRequest() throws Exception {
    Client client = ClientBuilder.newClient();

    Response response = client
        .target(url)
        .queryParam("name", "marvin")
        .request()
        .get();
    Assert.assertEquals("hello marvin", response.readEntity(String.class));
    response.close();
    client.close();
  }

  @Test
  public void testServletPostRequest() throws Exception {
    Client client = ClientBuilder.newClient();

    Response response = client
        .target(url)
        .queryParam("name", "marvin")
        .request()
        .post(null);
    Assert.assertEquals("hello marvin", response.readEntity(String.class));
    response.close();
    client.close();
  }


}
