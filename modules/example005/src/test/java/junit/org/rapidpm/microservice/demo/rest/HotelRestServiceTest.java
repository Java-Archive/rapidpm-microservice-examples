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

import com.zaxxer.hikari.HikariDataSource;
import org.hsqldb.server.Server;
import org.jboss.resteasy.test.TestPortProvider;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.ddi.DI;
import org.rapidpm.ddi.scopes.provided.JVMSingletonInjectionScope;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.MainUndertow;
import org.rapidpm.microservice.demo.model.HotelDAO;
import org.rapidpm.microservice.demo.rest.HotelRestService;
import org.rapidpm.microservice.persistence.jdbc.JDBCConnectionPools;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HotelRestServiceTest {

  @Inject private JDBCConnectionPools connectionPools;

  private final String restAppPath = MainUndertow.CONTEXT_PATH_REST;
  private final String ressourcePath = "/" + HotelRestService.class.getAnnotation(Path.class).value();
  private Server hsqlServer;
  private String generateURL;

  @Before
  public void setUp() throws Exception {
    System.setProperty(MainUndertow.REST_HOST_PROPERTY, "127.0.0.1");
    System.setProperty(MainUndertow.SERVLET_HOST_PROPERTY, "127.0.0.1");

    DI.registerClassForScope(JDBCConnectionPools.class, JVMSingletonInjectionScope.class.getName());
    DI.activateDI(this);

    hsqlServer = new Server();
    hsqlServer.setLogWriter(null);
    hsqlServer.setSilent(true);
    hsqlServer.setDatabaseName(0, "iva");
    hsqlServer.setDatabasePath(0, "file:target/" + HotelRestServiceTest.class.getSimpleName());
    hsqlServer.start();

    connectionPools
        .addJDBCConnectionPool(HotelDAO.POOLNAME)
        .withAutoCommit(false)
        .withJdbcURL("jdbc:hsqldb:file:target/" + HotelRestServiceTest.class.getSimpleName())
        .withUsername("sa")
        .withPasswd("")
        .done();

    connectionPools.connectPools();

    Main.deploy();

    generateURL = TestPortProvider.generateURL(restAppPath + ressourcePath);
    System.out.println("generateURL = " + generateURL);
  }


  @After
  public void tearDown() throws Exception {
    connectionPools.shutdownPools();
    hsqlServer.stop();
    Main.stop();

    DI.clearReflectionModel();
  }

  @Test
  public void testSaveB001() throws Exception {

    createTableForTest();

    Client client = ClientBuilder.newClient();
    final String uri = generateURL;
    final Response response = client.target(uri)
        .queryParam("hotelname", "hotel").queryParam("price", "9898")
        .request().get();
    Assert.assertEquals(200, response.getStatus());
    client.close();

    //hole aus db
    checkAllValues("hotel", 9898);
  }

  private void createTableForTest() throws SQLException {
    final Connection connection = connectionPools.getDataSource(HotelDAO.POOLNAME).getConnection();
    connection.prepareStatement("drop table hotels if exists;").execute();
    connection.prepareStatement("create table hotels (hotelname varchar(20) not null, price integer NOT NULL );").execute();
    connection.commit();
    connection.close();
  }

  private boolean checkAllValues(String hotelname, int price) {
    Connection connection = null;

    try {
      final HikariDataSource dataSource = connectionPools.getDataSource(HotelDAO.POOLNAME);
      connection = dataSource.getConnection();

      ResultSet rs = connection.createStatement().executeQuery("select * from hotels;");
      int counter = 0;
      while (rs.next()) {
        counter = counter + 1;
        Assert.assertEquals(hotelname, rs.getString("HOTELNAME"));
        Assert.assertEquals(price, rs.getInt("PRICE"));
      }
      Assert.assertEquals(1, counter);
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("e = " + e);
      return false;
    } finally {
      try {
        if (connection != null) connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("e = " + e);
      }
    }
    return true;
  }

  @Test
  public void testSaveA001() throws Exception {

    createTableForTest();

    Client client = ClientBuilder.newClient();
    final String uri = generateURL + "/hotel/9898";
    final Response response = client
        .target(uri)
        .request()
        .get();
    Assert.assertEquals(200, response.getStatus());
    client.close();

    checkAllValues("hotel", 9898);
  }

  @Test
  public void testSaveA002() throws Exception {

    createTableForTest();

    Client client = ClientBuilder.newClient();
    final String uri = generateURL;
    final Response response = client
        .target(uri).path("hotel").path("9898")
        .request().get();
    Assert.assertEquals(200, response.getStatus());
    client.close();

    checkAllValues("hotel", 9898);
  }

  @Test
  public void testLoad001() throws Exception {
    try {

      createTableForTest();
      final Connection connection = connectionPools.getDataSource(HotelDAO.POOLNAME).getConnection();
      connection.prepareStatement("insert into hotels (hotelname, price) values ('hotelHoppel', 9898);").execute();
      connection.commit();
      connection.close();

    } catch (SQLException e2) {
      e2.printStackTrace();
    }

    Client client = ClientBuilder.newClient();
    final Response response = client
        .target(generateURL)
        .path("listAll")
        .request()
        .get();
    Assert.assertEquals(200, response.getStatus());
    client.close();


  }


}
