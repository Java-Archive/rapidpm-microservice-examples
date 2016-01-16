package org.rapidpm.microservice.demo.rest;

import com.google.gson.Gson;
import org.rapidpm.microservice.demo.model.Hotel;
import org.rapidpm.microservice.demo.model.HotelDAO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Sven Ruppert on 30.07.15.
 */
@Path("hotelrestservice")
public class HotelRestService {

  @Inject HotelDAO hotelDAO;

  @GET()
  @Produces("application/json")
  public Response saveB(@QueryParam("hotelname") String hotelname, @QueryParam("price") int price) {
    final Hotel hotel = hotelDAO.saveHotel(hotelname, price);
    return Response.ok().build();
  }


  @Path("{h}/{p}")
  @GET()
  @Produces("application/json")
  public Response saveA(@PathParam("h") String hotelname, @PathParam("p") int price) {
    final Hotel hotel = hotelDAO.saveHotel(hotelname, price);
    return Response.ok().build();
  }


  @Path("listAll")
  @GET()
  @Produces("application/json")
  public Response listAll() {
    final List<Hotel> hotels = hotelDAO.loadAll();
    final Gson gson = new Gson();
    final String result = gson.toJson(hotels);
    return Response.status(200).entity(result).build();
  }


}
