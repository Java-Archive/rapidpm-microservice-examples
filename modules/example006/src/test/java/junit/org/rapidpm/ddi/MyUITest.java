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

package junit.org.rapidpm.ddi;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.junit.*;
import org.rapidpm.ddi.DI;
import org.rapidpm.ddi.MyUIServlet;
import org.rapidpm.ddi.reflections.ReflectionUtils;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.MainUndertow;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.util.Set;

public class MyUITest {

  private final String url = "http://127.0.0.1:" + MainUndertow.DEFAULT_SERVLET_PORT
      + MainUndertow.MYAPP
      + MyUIServlet.class.getAnnotation(WebServlet.class).urlPatterns()[0];

  @Before
  public void setUp() throws Exception {
    System.setProperty(MainUndertow.REST_HOST_PROPERTY, "127.0.0.1");
    System.setProperty(MainUndertow.SERVLET_HOST_PROPERTY, "127.0.0.1");


    DI.activatePackages("junit.org.rapidpm");
    Main.deploy();
  }

  @After
  public void tearDown() throws Exception {
    Main.stop();
  }

  @Test
  @Ignore
  public void test001() throws Exception {
    final Set<Class<?>> typesAnnotatedWith = DI.getTypesAnnotatedWith(WebServlet.class);
    for (Class<?> aClass : typesAnnotatedWith) {
      Assert.assertEquals(MyUIServlet.class, aClass);
    }
  }


  @Test
  public void test002() throws Exception {
    final boolean b = new ReflectionUtils().checkInterface(MyUIServlet.class, HttpServlet.class);
    Assert.assertTrue(b);
  }

  @Test
  public void test003() throws Exception {
    System.out.println("url = " + url);
    final Content returnContent = Request.Get(url).execute().returnContent();
    final String actual = returnContent.asString();
//    System.out.println("actual = " + actual);
//    Thread.sleep(10_000);
//    Request.Post("http://targethost/login")
//        .bodyForm(Form.form().add("username",  "vip").add("password",  "secret").build())
//        .execute().returnContent();

  }

}
