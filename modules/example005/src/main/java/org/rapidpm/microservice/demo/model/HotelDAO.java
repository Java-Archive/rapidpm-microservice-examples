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

package org.rapidpm.microservice.demo.model;

import com.zaxxer.hikari.HikariDataSource;
import org.rapidpm.microservice.persistence.jdbc.JDBCConnectionPools;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HotelDAO {

  @Inject private JDBCConnectionPools connectionPools;

  public static final String POOLNAME = "HOTEL_DAO_POOL";

//  @Inject JDBCConnectionPools connectionPools; //TODO wie realisieren ??
  //TDOD wo wird da initialisiert ? wie in MockModus geschaltet ?

  @PostConstruct
  private void postConstruct(){
    pool = connectionPools.getDataSource(POOLNAME);
  }
  private HikariDataSource pool;


  public Hotel saveHotel(final String hotelname, final int price) {

    final Hotel hotel = new Hotel();
    hotel.setHotelname(hotelname);
    hotel.setPrice(price);
    try {
      final Connection connection = pool.getConnection();
      connection.prepareStatement("insert into hotels (hotelname, price) values ('" + hotelname + "', " + price + ");").execute();
      connection.commit();
      connection.close();
    } catch (SQLException e2) {
      e2.printStackTrace();
    }
    return hotel;
  }

  public List<Hotel> loadAll() {
    System.out.println("loadAll... " + POOLNAME);

    List<Hotel> result = new ArrayList<>();
    Connection connection = null;
    try {
      connection = pool.getConnection();
      ResultSet rs = connection.createStatement().executeQuery("select * from hotels;");
      while (rs.next()) {

        final String hotelname = rs.getString("hotelname");
        final int price = rs.getInt("price");

        final Hotel hotel = new Hotel();
        hotel.setHotelname(hotelname);
        hotel.setPrice(price);
        result.add(hotel);
      }
      connection.close();
    } catch (SQLException e2) {
      e2.printStackTrace();
    } finally {
//      if(pool != null) pool.evictConnection(connection);
    }
    return result;
  }


}
