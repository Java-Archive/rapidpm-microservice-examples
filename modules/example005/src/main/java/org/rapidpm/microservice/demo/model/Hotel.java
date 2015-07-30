package org.rapidpm.microservice.demo.model;

/**
 * Created by svenruppert on 30.07.15.
 */
public class Hotel {

  private String hotelname;
  private int price;


  public String getHotelname() {
    return hotelname;
  }

  public void setHotelname(final String hotelname) {
    this.hotelname = hotelname;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(final int price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "Hotel{" +
        "hotelname='" + hotelname + '\'' +
        ", price=" + price +
        '}';
  }
}
