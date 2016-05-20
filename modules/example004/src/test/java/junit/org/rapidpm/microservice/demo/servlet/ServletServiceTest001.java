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

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.demo.service.ServiceImplA;
import org.rapidpm.microservice.demo.servlet.ServletService;

import javax.servlet.annotation.WebServlet;

public class ServletServiceTest001 {

  private final String url = "http://127.0.0.1:"
      + Main.DEFAULT_SERVLET_PORT + "/"
      + Main.MYAPP
      + ServletService.class.getAnnotation(WebServlet.class).urlPatterns()[0];

  @Before
  public void setUp() throws Exception {
    System.setProperty(Main.REST_HOST_PROPERTY, "127.0.0.1");
    System.setProperty(Main.SERVLET_HOST_PROPERTY, "127.0.0.1");

    DI.clearReflectionModel();
    Main.deploy();
    System.out.println("url = " + url);
  }


  @After
  public void tearDown() throws Exception {
    Main.stop();
    DI.clearReflectionModel();
  }


  @Test
  public void testServletGetReq002() throws Exception {
    final Content returnContent = Request.Get(url + "?txt=hello").execute().returnContent();
    Assert.assertEquals("hello" + ServiceImplA.class.getSimpleName(), returnContent.asString());
  }


}
