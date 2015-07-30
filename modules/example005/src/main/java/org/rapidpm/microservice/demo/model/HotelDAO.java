package org.rapidpm.microservice.demo.model;

import com.zaxxer.hikari.HikariDataSource;
import org.rapidpm.microservice.persistence.jdbc.JDBCConnectionPools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by svenruppert on 30.07.15.
 */
public class HotelDAO {


  public static final String POOLNAME = "HOTEL_DAO_POOL";

//  @Inject JDBCConnectionPools connectionPools; //TODO wie realisieren ??
  //TDOD wo wird da initialisiert ? wie in MockModus geschaltet ?

  private HikariDataSource pool = JDBCConnectionPools.instance().getDataSource(POOLNAME);


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
