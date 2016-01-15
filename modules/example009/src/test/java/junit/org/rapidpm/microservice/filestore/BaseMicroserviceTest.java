package junit.org.rapidpm.microservice.filestore;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.test.PortUtils;


/**
 * Created by b.bosch on 19.10.2015.
 */
public class BaseMicroserviceTest {

    @BeforeClass
    public static void setUpClass() {
        final PortUtils portUtils = new PortUtils();
        System.setProperty(Main.REST_PORT_PROPERTY, portUtils.nextFreePortForTest() + "");
        System.setProperty(Main.SERVLET_PORT_PROPERTY, portUtils.nextFreePortForTest() + "");
    }

    @Before
    public void setUp() throws Exception {
        Main.deploy();
    }

    @After
    public void tearDown() throws Exception {
        Main.stop();
    }
}
