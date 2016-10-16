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

package junit.org.rapidpm.microservice.m1;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.MainUndertow;
import org.rapidpm.microservice.m1.DemoService;
import org.rapidpm.microservice.test.RestUtils;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

public class DemoServiceTest {

  @Before
  public void setUp() throws Exception {
    System.setProperty(MainUndertow.REST_HOST_PROPERTY, "127.0.0.1");
    System.setProperty(MainUndertow.SERVLET_HOST_PROPERTY, "127.0.0.1");


    DI.activatePackages("org.rapidpm");
    DI.activatePackages("junit.org.rapidpm");
    Main.deploy();
  }

  @After
  public void tearDown() throws Exception {
    Main.stop();
  }

  @Test
  public void testAddValues() throws Exception {

    final String generateBasicReqURL = new RestUtils().generateBasicReqURL(DemoService.class, MainUndertow.CONTEXT_PATH_REST);
    Client client = ClientBuilder.newClient();
    final Builder request = client
        .target(generateBasicReqURL)
        .path("1")
        .path("2")
        .request();
    final Response response = request.get();
    Assert.assertEquals(200, response.getStatus());
    final StatusType statusInfo = response.getStatusInfo();
    final String reasonPhrase = statusInfo.getReasonPhrase();
    Assert.assertEquals("OK", reasonPhrase);
    Integer result = response.readEntity(Integer.class);

    Assert.assertNotNull(result);
    Assert.assertEquals(Integer.valueOf(3), result);
    client.close();


  }
}