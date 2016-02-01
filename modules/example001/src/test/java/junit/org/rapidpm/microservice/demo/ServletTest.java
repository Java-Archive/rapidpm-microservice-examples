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

package junit.org.rapidpm.microservice.demo;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.junit.*;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.demo.servlet.MessageServlet;
import org.rapidpm.microservice.test.PortUtils;

import javax.servlet.annotation.WebServlet;


public class ServletTest {


  private static String url;
  private final String USER_AGENT = "Mozilla/5.0";

  @BeforeClass
  public static void setUpClass() {
    final PortUtils portUtils = new PortUtils();
    System.setProperty(Main.REST_PORT_PROPERTY, portUtils.nextFreePortForTest() + "");
    System.setProperty(Main.SERVLET_PORT_PROPERTY, portUtils.nextFreePortForTest() + "");
    url = "http://127.0.0.1:" + System.getProperty(Main.SERVLET_PORT_PROPERTY) + "/"
        + Main.MYAPP
        + MessageServlet.class.getAnnotation(WebServlet.class).urlPatterns()[0];
    Main.deploy();
  }

  @AfterClass
  public static void tearDown() throws Exception {
    Main.stop();
  }

  @Before
  public void setUp() throws Exception {

  }

  @Test
  public void testServletGetReq001() throws Exception {
    final Content returnContent = Request
        .Get(url)
        .execute().returnContent();
    Assert.assertEquals("Hello World CDI Service", returnContent.asString());
  }

  @Test
  public void testServletPostRequest() throws Exception {
    final Content returnContent = Request
        .Post(url)
        .execute().returnContent();
    Assert.assertEquals("Hello World CDI Service", returnContent.asString());
  }


}
