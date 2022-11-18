package Functions;
/**
 * Esta clase se encarga de inicializar el Driver
 *
 */

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CreateDriver {
    private static String browser;
    private static String logLevel;

    private static Properties prop = new Properties();
    private static InputStream in = CreateDriver.class.getResourceAsStream("../test.properties");
    private static CreateDriver instance = null;
    private static Logger log = Logger.getLogger(CreateDriver.class);

    private CreateDriver() throws IOException {
        CreateDriver.initConfig();
    }

    public static WebDriver initConfig() throws IOException {
        WebDriver driver;

        try {
            log.info("***********************************************************************************************************");
            log.info("[ POM Configuration ] - Read the basic properties configuration from: ../test.properties");
            prop.load(in);
            //
            browser = CreateDriver.defaultBrowser();
            logLevel = prop.getProperty("logLevel");

        } catch (IOException e) {
            log.error("initConfig Error", e);
        }

        /******** POM Information ********/
        log.info("[ POM Configuration ] - | Browser: " + browser + " |");
        log.info("[ POM Configuration ] - Logger Level: " + logLevel);
        log.info("***********************************************************************************************************");

        /****** Load the driver *******/

        driver = WebDriverFactory.createNewWebDriver(browser);


        return driver;
    }

    public static String defaultBrowser() throws IOException {


        String browser = System.getProperty("browser");

        if (browser == null) {
            browser = prop.getProperty("browser");
        }
        return browser;
    }

    public static String defaultAmbiente() throws IOException {

        String ambiente = System.getProperty("environment");
        if (ambiente == null) {
            ambiente = prop.getProperty("environment");
        }
        return ambiente;
    }

}
