package junit.org.rapidpm.microservice.demo;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.demo.servlet.MessageServlet;

import javax.servlet.annotation.WebServlet;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by sven on 27.05.15.
 */
public class ServletTest {

  private final String USER_AGENT = "Mozilla/5.0";
  private String url = "http://127.0.0.1:" + Main.DEFAULT_SERVLET_PORT
      + Main.MYAPP
      + MessageServlet.class.getAnnotation(WebServlet.class).urlPatterns()[0];

  @Before
  public void setUp() throws Exception {
    Main.deploy();
  }

  @After
  public void tearDown() throws Exception {
    Main.stop();
  }

  @Test
  public void testServletGetReq001() throws Exception {
    URL obj = new URL(url);
    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
    con.setRequestMethod("GET");
    con.setRequestProperty("User-Agent", USER_AGENT);

    int responseCode = con.getResponseCode();
    System.out.println("\nSending 'GET' request to URL : " + url);
    System.out.println("Response Code : " + responseCode);

    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    String inputLine;
    StringBuffer response = new StringBuffer();

    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();
//    System.out.println("response = " + response);
    Assert.assertEquals("Hello World CDI Service", response.toString());
  }

  @Test
  public void testServletGetReq002() throws Exception {
    final Content returnContent = Request.Get(url).execute().returnContent();
    Assert.assertEquals("Hello World CDI Service", returnContent.asString());
//    Request.Post("http://targethost/login")
//        .bodyForm(Form.form().add("username",  "vip").add("password",  "secret").build())
//        .execute().returnContent();

  }


  @Test
  public void testServletPostRequest() throws Exception {
    URL obj = new URL(url);
    HttpURLConnection con = (HttpURLConnection) obj.openConnection();

    //add reuqest header
    con.setRequestMethod("POST");
    con.setRequestProperty("User-Agent", USER_AGENT);
    con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

    String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

    // Send post request
    con.setDoOutput(true);
    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
    wr.writeBytes(urlParameters);
    wr.flush();
    wr.close();

    int responseCode = con.getResponseCode();
    System.out.println("\nSending 'POST' request to URL : " + url);
    System.out.println("Post parameters : " + urlParameters);
    System.out.println("Response Code : " + responseCode);

    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    String inputLine;
    StringBuffer response = new StringBuffer();

    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();

    //print result
    Assert.assertEquals("Hello World CDI Service", response.toString());


  }


}
