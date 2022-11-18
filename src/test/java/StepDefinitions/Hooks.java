package StepDefinitions;
/**
 * Esta clase contiene el codigo que se ejecutara antes y despues de ejecutar
 * cada una de los escenarios automatizados que ejecuten, adicional
 * por cada de uno de los pasos obtendra las caputuras de pantalla y las agregará
 * al reporte
 */

import Functions.CreateDriver;
import Functions.SeleniumFunctions;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import java.util.Map;

public class Hooks {

    public static WebDriver driver;
    Logger log = Logger.getLogger(Hooks.class);
    Scenario scenario = null;
    SeleniumFunctions functions = new SeleniumFunctions();

    /**
     * Inicialización del Driver y lectura de las variables del test.properties
     * @param scenario
     * @throws Exception
     */
    @Before
    public void before(Scenario scenario) throws Exception {
        log.info("***********************************************************************************************************");
        log.info("[ Configuration ] - Initializing driver configuration");
        log.info("***********************************************************************************************************");
        driver = CreateDriver.initConfig();



        this.scenario = scenario;
        log.info("***********************************************************************************************************");
        log.info("[ Scenario ] - " + scenario.getName());
        log.info("***********************************************************************************************************");

        driver.manage().window().setSize(new Dimension(1366, 768));
        functions.RetriveTestDatas();
    }

    /**
     * Cierra el navegador
     * @param scenario
     * @throws Exception
     */
    @After
    public void after(Scenario scenario) throws Exception {
        driver.quit();
    }

    /**
     * Agrega cada screenshot guardado a cada paso del reporte.
     * @param scenario
     */
    @AfterStep
    public void addScreenShot(Scenario scenario){

        if(SeleniumFunctions.StepScreenShots.size()!=0) {

            for (Map.Entry<String, byte[]> entry : SeleniumFunctions.StepScreenShots.entrySet()) {
                System.out.println("Key = " + entry.getKey() +
                        ", Value = " + entry.getValue());
                scenario.attach(entry.getValue(), "image/png",entry.getKey());
            }
        }
        SeleniumFunctions.StepScreenShots.clear();
    }


}
