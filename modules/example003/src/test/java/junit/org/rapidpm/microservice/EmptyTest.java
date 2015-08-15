package junit.org.rapidpm.microservice;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.microservice.Main;

/**
 * Created by svenruppert on 10.07.15.
 */
public class EmptyTest {

  @Before
  public void setUp() throws Exception {
    Main.deploy();
  }


  @After
  public void tearDown() throws Exception {
    Main.stop();
  }


  @Test
  public void testEmptyTest() throws Exception {


  }
}
