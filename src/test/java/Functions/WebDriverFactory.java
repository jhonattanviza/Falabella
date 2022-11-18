package Functions;
/**
 * Esta clase se encarga de instanciar el Driver según el navegador en el cual
 * se va a ejecutar la prueba, es importante tener en cuenta que se da prioridad
 * al nombre del navegador enviado por línea de comando.
 * @author Jhonattan Villamil Zamora
 */

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WebDriverFactory {

    private static Properties prop = new Properties();
    private static InputStream in = CreateDriver.class.getResourceAsStream("../test.properties");
    private static String resourceFolder;
    private static WebDriverFactory instance = null;

    private WebDriverFactory() {

    }

    public static WebDriverFactory getInstance() {
        if (instance == null) {
            instance = new WebDriverFactory();
        }
        return instance;
    }

    public static WebDriver createNewWebDriver(String browser) throws IOException {

        WebDriver driver = null;
        prop.load(in);
        resourceFolder = prop.getProperty("resourceFolder");

        if ("FIREFOX".equalsIgnoreCase(browser)) {
            System.setProperty("webdriver.gecko.driver", resourceFolder + "/geckodriver.exe");
            driver = new FirefoxDriver();
        } else if ("CHROME".equalsIgnoreCase(browser)) {
            System.setProperty("webdriver.chrome.driver", resourceFolder + "/chromedriver.exe");

            driver = new ChromeDriver();
        }
        return driver;
    }
}
