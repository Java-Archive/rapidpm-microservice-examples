package junit.org.rapidpm.microservice.filestore;

import org.junit.After;
import org.junit.Before;
import org.rapidpm.microservice.Main;

/**
 * Created by b.bosch on 19.10.2015.
 */
public class BaseMicroserviceTest {
    @Before
    public void setUp() throws Exception {
        Main.deploy();
    }

    @After
    public void tearDown() throws Exception {
        Main.stop();
    }
}
