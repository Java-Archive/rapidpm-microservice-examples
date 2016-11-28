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

import com.google.gson.Gson;
import org.jboss.resteasy.test.TestPortProvider;
import org.junit.*;
import org.rapidpm.dependencies.core.net.PortUtils;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.MainUndertow;
import org.rapidpm.microservice.demo.model.Duel;
import org.rapidpm.microservice.demo.model.Fighter;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

public class RestTest {

  public static final String REST_DOJO_PATH = "/rest/dojo/whoWins";
  private Gson gson;

  @BeforeClass
  public static void setUpClass() {
    final PortUtils portUtils = new PortUtils();
    System.setProperty(MainUndertow.REST_PORT_PROPERTY, portUtils.nextFreePortForTest() + "");
    System.setProperty(MainUndertow.SERVLET_PORT_PROPERTY, portUtils.nextFreePortForTest() + "");
  }

  @Before
  public void setUp() throws Exception {
    Main.deploy();
    gson = new Gson();
  }

  @After
  public void tearDown() throws Exception {
    Main.stop();
  }

  @Test
  public void testWhoWins001() throws Exception {
    Fighter chuck = new Fighter("chuck", 100, 10);
    Fighter rob = new Fighter("rob", 120, 9);

    Duel duel = new Duel(chuck, rob);
    String json = gson.toJson(duel);

    String winner = getWinner(REST_DOJO_PATH, json);
    Assert.assertEquals("chuck", winner);
  }

  @Test
  public void testWhoWins002() throws Exception {
    Fighter roy = new Fighter("roy", 80, 17);
    Fighter hank = new Fighter("hank", 150, 7);

    Duel duel = new Duel(roy, hank);
    String json = gson.toJson(duel);

    String winner = getWinner(REST_DOJO_PATH, json);
    Assert.assertEquals("roy", winner);
  }

  private String getWinner(String resourcePath, String json) {
    Client client = ClientBuilder.newClient();
    final String generateURL = TestPortProvider.generateURL(resourcePath);
    String winner = client
        .target(generateURL)
        .request()
        .post(Entity.entity(json, MediaType.APPLICATION_JSON))
        .readEntity(String.class);
    client.close();
    return winner;
  }


}
