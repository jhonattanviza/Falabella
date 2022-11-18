package Functions;
/**
 * En esta clase se crean todos los metodos utilizando las diferentes
 * librerias como Junit y Selenium para llevar a cabo la automatización
 *  requerida en la prueba técnica de Fallabela, solo se crearon aquellos
 * necesarios para ejecutar las pruebas.
 * @author Jhonattan Villamil Zamora
 */

import StepDefinitions.Hooks;
import io.cucumber.java.Scenario;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class SeleniumFunctions {

    static WebDriver driver;

    public SeleniumFunctions() {

        driver = Hooks.driver;
    }

    /**
     * Constante que permite definir el tiempo de espera en algunos metodos.
     */
    public static final Duration EXPLICIT_TIMEOUT = Duration.ofSeconds(30);
    /**
     * Constante que permite definir el tiempo maximo de espera en algunos metodos.
     */
    public static final Duration EXPLICIT_TIMEOUTMAX = Duration.ofSeconds(180);
    public static boolean isDisplayed = Boolean.parseBoolean(null);
    public static boolean isEnable = Boolean.parseBoolean(null);

    Scenario scenario = null;

    public void scenario(Scenario scenario) {
        this.scenario = scenario;
    }

    /**
     * Mapa estatico que guarda las variables iniciales y obtenidas durante la ejecución
     * de las pruebas automatizadas
     */
    public static Map<String, String> ScenaryData = new HashMap<>();
    /**
     * Mapa estatico que guarda los datos de la sesion en el navegador
     * para poder interactuar entre pestañas.
     */
    public static Map<String, String> HandleMyWindows = new HashMap<>();
    /**
     * Mapa estatico que guarda los datos de los ScreenShots capturados durante la
     * ejecución de las pruebas, para anexarlos a los reportes
     */
    public static Map<String, byte[]> StepScreenShots = new HashMap<String, byte[]>();

    public static Properties prop = new Properties();
    public static InputStream in = SeleniumFunctions.class.getResourceAsStream("../test.properties");

    /**
     * Metodo que permite obtener informacion de una propiedad guardada en el test.properties
     * @param property
     * @return
     * @throws IOException
     */
    public static String readProperties(String property) throws IOException {
        prop.load(in);
        return prop.getProperty(property);
    }

    private static Logger log = Logger.getLogger(SeleniumFunctions.class);

    public static String Environment = "";

    public String ElementText = "";

    // Lectura de los elementos de las paginas.

    public static String FileName = "";
    public static String PagesFilePath = "src/test/resources/Pages/";

    public static String GetFieldBy = "";
    public static String ValueToFind = "";


    public static Object readJson() throws Exception {
        FileReader reader = new FileReader(PagesFilePath + FileName);
        try {

            if (reader != null) {
                JSONParser jsonParser = new JSONParser();
                return jsonParser.parse(reader);
            } else {
                return null;
            }
        } catch (FileNotFoundException | NullPointerException e) {
            log.error("ReadEntity: No existe el archivo " + FileName);
            throw new IllegalStateException("ReadEntity: No existe el archivo " + FileName, e);
        }

    }

    /**
     * Permite obtener la informacion de un elemento guardado en el JSON
     * los elementos corresponden a los obtenidos del DOM del sitio que se
     * esta automatizando
     * @param element
     * @return
     * @throws Exception
     */
    public static JSONObject ReadEntity(String element) throws Exception {
        JSONObject Entity = null;

        JSONObject jsonObject = (JSONObject) readJson();
        try {
            Entity = (JSONObject) jsonObject.get(element);
            log.info(Entity.toJSONString());
        } catch (NullPointerException e) {
            log.info("El elemento " + element + " no existe en el JSON");
        }


        return Entity;

    }

    /**
     * Metodo que lee la informacion del archivo .json donde guardan los
     * elementos del DOM.
     * @param file
     * @throws Exception
     */
    public void iLoadTheDOMInformation(String file) throws Exception {

        SeleniumFunctions.FileName = file;
        SeleniumFunctions.readJson();
        log.info("initialize file: " + file);

    }

    /**
     * Permite obtener la informacion detalla de un elemento y lo devuelve
     * de tipo By para que sea utilizado directamente por Selenium
     * Adicional este metodo identifica si el elemento no existe y en ese
     * caso retorna el dato recibido como un Xpath para intentar
     * ejecutar accion con el.
     * @param element   nombre del elemento que se debe consultar en el JSON
     * @return
     * @throws Exception
     */
    public static By getCompleteElement(String element) throws Exception {
        By result = null;
        try {
            JSONObject Entity = ReadEntity(element);
            GetFieldBy = (String) Entity.get("GetFieldBy");
            ValueToFind = (String) Entity.get("ValueToFind");
        } catch (NullPointerException e) {
            // aqui coloco que si no encuentra el elemento en el JSON, entonces lo busque como un localizador xpath.
            log.info("Se utilizo  " + element + " como un localizador Xpath");
            GetFieldBy = "Xpath";
            ValueToFind = element;
        }

        if ("className".equalsIgnoreCase(GetFieldBy)) {
            result = By.className(ValueToFind);
        } else if ("cssSelector".equalsIgnoreCase(GetFieldBy)) {
            result = By.cssSelector(ValueToFind);
        } else if ("id".equalsIgnoreCase(GetFieldBy)) {
            result = By.id(ValueToFind);
        } else if ("linkText".equalsIgnoreCase(GetFieldBy)) {
            result = By.linkText(ValueToFind);
        } else if ("name".equalsIgnoreCase(GetFieldBy)) {
            result = By.name(ValueToFind);
        } else if ("link".equalsIgnoreCase(GetFieldBy)) {
            result = By.partialLinkText(ValueToFind);
        } else if ("tagName".equalsIgnoreCase(GetFieldBy)) {
            result = By.tagName(ValueToFind);
        } else if ("xpath".equalsIgnoreCase(GetFieldBy)) {
            result = By.xpath(ValueToFind);
        }

        log.info(result);
        return result;
    }

    /**
     * Este metodo almacena en un map un dato para que sea utilizado
     * en la ejecucion de las pruebas
     * @param key    nombre llave que se le dara a la variable
     * @param value    contenido de la variable
     * @throws Exception
     */
    public void SaveInScenarioVar(String key, String value) throws Exception {

        String text = value;

        if (!this.ScenaryData.containsKey(key)) {
            this.ScenaryData.put(key, text);
            log.info(String.format("Se guarda la variable: %s con el valor: %s ", key, text));
        } else {
            this.ScenaryData.replace(key, text);
            log.info(String.format("Se actualiza la variable: %s con el valor: %s ", key, text));
        }

    }

    /**
     * Este metodo identifica las variables que están almacenadas en el test.properties
     * identificandolas segun el ambiente en el que se están ejecutando las pruebas
     * @throws Exception
     */
    public void RetriveTestDatas() throws Exception {
        Environment = CreateDriver.defaultAmbiente();
        prop.load(in);
        System.out.println("Se obtienen del properties las variables del ambiente " + Environment);
        prop.keySet().forEach(x -> {
            String propiedad = x.toString();
            if (propiedad.contains(Environment)) {
                try {
                    int mid = propiedad.indexOf(".");
                    String key = propiedad.substring(0, mid);
                    SaveInScenarioVar(key, readProperties(propiedad));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Identifica desde el test.properties cual es la url principal de las pruebas
     * para abrir la url en el navegador.
     */
    public void readMain() {

        boolean exist = this.ScenaryData.containsKey("url");
        if (exist) {
            String url = this.ScenaryData.get("url");
            log.info("Abriendo la pagina: " + url);
            open(url);
            HandleMyWindows.put("Principal", driver.getWindowHandle());
            page_has_loaded();
        } else {
            Assert.assertTrue(String.format("La variable %s no existe en el escenario"), this.ScenaryData.containsKey("url"));
        }

    }

    /**
     * Permite a traves de selenium realizar la acción de dar clic sobre un
     * determinado elemento, el elemento se buscará en el JSON, es necesario haber
     * precargado el json donde están los elementos.
     * @param element  Nombre del elemento guardado en el JSON o Xpath.
     * @throws Exception
     */
    public void iClicInElement(String element) throws Exception {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);

        for (int retry = 0; retry < 3; retry++) {
            try {
                driver.findElement(SeleniumElement).click();
                log.info("Clic en el elemento " + element + " utilizando " + ValueToFind);
                retry = 3;
            } catch (Exception e) {
                log.info("No fue posible dar clic al elemento " + element + " intentando por " + (retry + 2) + " vez...");
                Thread.sleep(5000 * (retry + 2));

                if (retry == 2) {
                    driver.findElement(SeleniumElement).click();
                }
            }
        }

    }

    /**
     * Permite abrir un sitio web determinado en cualquier momento luego de que se ha
     * inicializado el driver.
     * @param url  url del sitio web que se desea abrir
     */
    public void navigateTo(String url) {

        log.info("Abriendo la pagina: " + url);
        open(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        HandleMyWindows.put("Principal", driver.getWindowHandle());
        page_has_loaded();

    }

    public void open(String url) {
        driver.get(url);
    }

    /**
     * Permite identificar a través de javascript que el sitio web haya cargado completamente
     */
    public void page_has_loaded() {
        String GetActual = driver.getCurrentUrl();
        log.info(String.format("Validando que la pagina %s ha cargado...", GetActual));
        new WebDriverWait(driver, EXPLICIT_TIMEOUT).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Permite asignar un texto a un elemento del dom a través de Selenium, el texto
     * @param element   Nombre del elemento guardado en el JSON o Xpath.
     * @param text    Texto que se asignará en el elemento.
     * @throws Exception
     */
    public void iSetElementWithText(String element, String text) throws Exception {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        driver.findElement(SeleniumElement).sendKeys(text);
        log.info(String.format("Se ingreso al elemento %s el texto %s", element + " utilizando " + ValueToFind, text));
    }

    /**
     * Permite asignar el valor de una variable del escenario a un elemento del dom
     * a través de Selenium, la variable debe estar almacenada previamente.
     * @param element  Nombre del elemento guardado en el JSON o Xpath.
     * @param key    Nombre de la variable previamente guardada en el escenario
     * @throws Exception
     */
    public void iSetElementWithKeyValue(String element, String key) throws Exception {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        boolean exist = this.ScenaryData.containsKey(key);
        if (exist) {
            String text = this.ScenaryData.get(key);
            driver.findElement(SeleniumElement).sendKeys(text);
            log.info(String.format("Se ingreso al elemento %s el texto %s", element + " of " + ValueToFind, text));
        } else {
            Assert.assertTrue(String.format("La variable %s no existe en el escenario", key), this.ScenaryData.containsKey(key));
        }

    }

    /**
     * Permite desplazarse al inicio, al final o cierta cantidad de píxeles hacia abajo
     * en la página.
     * @param to  se debe enviar "top" para el inicio, "end" para el final, o el número de píxeles.
     * @throws Exception
     */
    public void scrollPage(String to) throws Exception {

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        if (to.equals("top")) {
            log.info("Scrolling to the top of the page");
            jse.executeScript("scroll(0, -250);");
            Thread.sleep(500);

        } else if (to.equals("end")) {
            log.info("Scrolling to the end of the page");
            jse.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            Thread.sleep(500);
        } else {
            int px = Integer.parseInt(to);
            log.info("Scrolling " + to + " px down in the page");
            jse.executeScript("scroll(0, " + px + ");");
            Thread.sleep(500);
        }
    }

    /**
     * Permite dar un tiempo de espera, pausando la ejecución de la prueba determinados segundos.
     * @param time   Tiempo en segundos de la pausa.
     * @throws InterruptedException
     */
    public void iWaitTime(int time) throws InterruptedException {
        log.info("Esperando " + time + " segundos..");
        Thread.sleep(time * 1000);

    }

    /**
     * Captura un pantallazo del sitio para y lo guarda en el map de capturas
     * de pantalla para visualizarlos en el reporte
     * @return
     * @throws IOException
     */
    public byte[] attachScreenShot() throws IOException {
        log.info("Adjuntando pantallazo");
        // Nombre que se le dara a la foto.
        long numer = System.currentTimeMillis();
        DateFormat simple = new SimpleDateFormat("dd-MMM-yyyy-HH-mm-ss");
        Date result = new Date(numer);
        long namedate = System.currentTimeMillis();
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

        this.StepScreenShots.put(simple.format(result), screenshot);
        return screenshot;
    }

    /**
     * Cuenta la cantidad de elementos que se encuentran en la página.
     * @param element  Nombre del elemento guardado en el JSON o Xpath.
     * @return
     * @throws Exception
     */
    public int countElement(String element) throws Exception {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        List<WebElement> links = driver.findElements(SeleniumElement);

        int linkCount = links.size();

        System.out.println("El total de elementos en la pagina es = " + linkCount);

        return linkCount;
    }

    /**
     * Permite seleccionar un elemento que contenga cierto texto utilizando la funcion
     * contains de Xpath, si el elemento en el JSON tiene la funcion contains vacia
     * la reemplaza agregandole el texto buscado, si no tiene la funcion contains
     * se la agrega al final.
     * @param element Nombre del elemento guardado en el JSON o Xpath.
     * @param text  Texto que debe contener el elemento.
     * @throws Exception
     */
    public void iSelectContainsText(String element, String text) throws Exception {

        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        System.out.println(SeleniumElement.toString());
        String ValuetoFinder = SeleniumElement.toString();
        int check = ValuetoFinder.indexOf("[contains(.,'')]");
        String elementEdited = "";
        elementEdited = ValuetoFinder.replace("By.xpath: ", "");
        if (check > -1) {
            elementEdited = elementEdited.replace("contains(.,'')", "contains(.,'" + text + "')");

            System.out.println("El elemento tenia contains se reemplaza: " + elementEdited);
        } else {
            elementEdited = elementEdited + "[contains(.,'" + text + "')]";
            System.out.println("El elemento no tenia el contains sea agrego: " + elementEdited);
        }

        iClicInElement(elementEdited);
    }

    /**
     * Permite validar que un elemento esté visible en el sitio
     * @param element Nombre del elemento guardado en el JSON o Xpath.
     * @param time Tiempo máximo de espera para que se visualice el elemento.
     * @return
     * @throws Exception
     */
    public boolean isElementDisplayed(String element, Duration time) throws Exception {

        try {
            By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
            log.info(String.format("Esperando elemento usando %s ", ValueToFind));
            WebDriverWait wait = new WebDriverWait(driver, time);
            isDisplayed = wait.until(ExpectedConditions.presenceOfElementLocated(SeleniumElement)).isDisplayed();
        } catch (NoSuchElementException | TimeoutException e) {
            isDisplayed = false;
            log.info(e);
        }
        log.info(String.format("La visibilidad del elemento %s es: %s  ", ValueToFind, isDisplayed));
        return isDisplayed;
    }

    /**
     * Permite validar que un elemento este habilitado en la página.
     * @param element Nombre del elemento guardado en el JSON o Xpath.
     * @return
     * @throws Exception
     */
    public boolean isElementEnable(String element) throws Exception {

        try {
            By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
            log.info(String.format("Waiting Element: %s", element + " of " + ValueToFind));
            WebDriverWait wait = new WebDriverWait(driver, EXPLICIT_TIMEOUT);
            isEnable = wait.until(ExpectedConditions.presenceOfElementLocated(SeleniumElement)).isEnabled();
        } catch (NoSuchElementException | TimeoutException e) {
            isEnable = false;
            log.info(e);
        }
        log.info(String.format("%s enable is: %s", element + " of " + ValueToFind, isEnable));
        return isEnable;
    }

    /**
     * Realiza un Assert con JUnit para validar que un elemento este habilitado.
     * @param element Nombre del elemento guardado en el JSON o Xpath.
     * @throws Exception
     */
    public void checkIfElementIsEnable(String element) throws Exception {


        boolean isEnable = isElementEnable(element);
        attachScreenShot();
        Assert.assertTrue("El elemento: " + element + " no esta habilitado.", isEnable);
    }

    /**
     * Realiza un Assert con JUnit para validar que un elemento esté deshabilitado.
     * @param element Nombre del elemento guardado en el JSON o Xpath.
     * @throws Exception
     */
    public void checkIfElementIsDisable(String element) throws Exception {


        boolean isEnable = isElementEnable(element);
        attachScreenShot();
        Assert.assertFalse("El elemento: " + element + " no esta deshabilitado.", isEnable);
    }

    /**
     * Realiza un Assert con JUnit para validar que un elemento esté presente.
     * @param element Nombre del elemento guardado en el JSON o Xpath.
     * @throws Exception
     */
    public void checkIfElementIsPresent(String element) throws Exception {

        boolean isDisplayed = isElementDisplayed(element, Duration.ofSeconds(15));
        attachScreenShot();
        Assert.assertTrue("El elemento esperado: " + element + " no esta presente: ", isDisplayed);

    }

    /**
     * Realiza un Assert con JUnit para validar que un elemento esté presente.
     * @param element Nombre del elemento guardado en el JSON o Xpath
     * @param screenshot Se requiere o no captura de pantalla de esta validation.
     * @throws Exception
     */
    public void checkIfElementIsPresent(String element, boolean screenshot) throws Exception {

        boolean isDisplayed = isElementDisplayed(element, Duration.ofSeconds(15));
        if (screenshot) {
            attachScreenShot();
        }
        Assert.assertTrue("El elemento esperado: " + element + " no esta presente: ", isDisplayed);

    }

    /**
     * Realiza un Assert con JUnit para validar que un elemento no esté presente.
     * @param element Nombre del elemento guardado en el JSON o Xpath
     * @throws Exception
     */
    public void checkIfElementIsNotPresent(String element) throws Exception {

        boolean isDisplayed = isElementDisplayed(element, Duration.ofSeconds(10));
        attachScreenShot();
        Assert.assertFalse("El elemento " + element + " no deberia estar presente: ", isDisplayed);

    }

    /**
     * Permite a través de Selenium capturar el texto de un elemento del DOM
     * @param element Nombre del elemento guardado en el JSON o Xpath
     * @return
     * @throws Exception
     */
    public String GetTextElement(String element) throws Exception {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        WebDriverWait wait = new WebDriverWait(driver, EXPLICIT_TIMEOUT);
        wait.until(ExpectedConditions.presenceOfElementLocated(SeleniumElement));
        log.info(String.format("Esperando el elemento: %s", element + " of " + ValueToFind));

        ElementText = driver.findElement(SeleniumElement).getText();

        if (ElementText.isEmpty()) {
            log.info(String.format("Texto vacio se intenta buscar el valor del elemento"));
            ElementText = driver.findElement(SeleniumElement).getAttribute("value");
        }

        log.info(String.format("El texto del elemento es: " + ElementText));

        return ElementText;

    }

    /**
     * Permite a través de JavaScript hacer un scroll en la página para
     * desplazarse hacia un elemento determinado.
     * @param element Nombre del elemento guardado en el JSON o Xpath
     * @throws Exception
     */
    public void scrollToElement(String element) throws Exception {

        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        log.info("Scrolling to element: " + element + " of " + ValueToFind);
        jse.executeScript("arguments[0].scrollIntoView(true);" + "window.scrollBy(0,-100);", driver.findElement(SeleniumElement));
        Thread.sleep(1000);
    }

    /**
     * Permite esperar a que un elemento no este presente en el DOM para continuar
     * la ejecución, se esperará el tiempo máximo asignado a la constante EXPLICIT_TIMEOUTMAX
     * si superado el tiempo el elemento no ha desaparecido la ejecución se detiene.
     * @param element Nombre del elemento guardado en el JSON o Xpath
     * @throws Exception
     */
    public void waitForElementNotPresent(String element) throws Exception {

        try {
            By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
            WebDriverWait w = new WebDriverWait(driver, EXPLICIT_TIMEOUTMAX);
            log.info("Waiting for the element: " + element + " of " + ValueToFind + " to be not present");
            WebElement elemento = driver.findElement((SeleniumElement));
            w.until(ExpectedConditions.stalenessOf(elemento));
        } catch (NoSuchElementException e) {
            log.info("Never was element: " + element + " of " + ValueToFind + " present");
        }

    }

    /**
     * Permite esperar a que un elemento no este presente en el DOM para continuar
     * la ejecución, se esperará el tiempo enviado como parametro en segundos
     * si superado el tiempo el elemento no ha desaparecido la ejecución se detiene.
     * @param element  Nombre del elemento guardado en el JSON o Xpath
     * @param seconds Tiempo en segundos de espera para que el elemento no este presente.
     * @throws Exception
     */
    public void waitForElementNotPresent(String element, int seconds) throws Exception {

        try {
            By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
            WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(seconds));
            log.info("Waiting for the element: " + element + " of " + ValueToFind + " to be not present in " + seconds + " seconds.");
            WebElement elemento = driver.findElement((SeleniumElement));
            w.until(ExpectedConditions.stalenessOf(elemento));
        } catch (NoSuchElementException e) {
            log.info("Never was element: " + element + " of " + ValueToFind + " present");
        }

    }

    /**
     * Permite esperar a que un elemento este presente en el DOM para continuar
     * la ejecución, se esperará el tiempo enviado como parametro en segundos
     * si superado el tiempo el elemento no ha aparecido la ejecución se detiene.
     * @param element Nombre del elemento guardado en el JSON o Xpath
     * @throws Exception
     */
    public void waitForElementPresent(String element) throws Exception {

        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        WebDriverWait w = new WebDriverWait(driver, EXPLICIT_TIMEOUTMAX);
        log.info("Waiting for the element: " + element + " of " + ValueToFind + " to be present");
        w.until(ExpectedConditions.presenceOfElementLocated(SeleniumElement));
    }

    /**
     * Validar que un elemento del DOM contenga cierto texto si no se encuentran
     * coincidencias la ejecución se detiene.
     * @param element Nombre del elemento guardado en el JSON o Xpath
     * @param text  Texto que debe estar presente en el elemento.
     * @throws Exception
     */
    public void checkPartialTextElementPresent(String element, String text) throws Exception {

        checkIfElementIsPresent(element);

        ElementText = GetTextElement(element);

        boolean isFound = ElementText.indexOf(text) != -1 ? true : false;
        attachScreenShot();
        Assert.assertTrue("El texto esperado no esta presente en el elemento: " + element + " el texto actual es: " + ElementText, isFound);

    }

    /**
     * Permite seleccionar de una lista una determinada opcion.
     * @param element Nombre del elemento guardado en el JSON o Xpath
     * @param option Texto de la opcion que se requiere seleccionar.
     * @throws Exception
     */
    public void selectOptionDropdownByText(String element, String option) throws Exception {

        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        log.info(String.format("Waiting Element: %s", element + " of " + ValueToFind));

        Select opt = new Select(driver.findElement(SeleniumElement));
        log.info("Select option: " + option + "by text");
        opt.selectByVisibleText(option);
    }


    /**
     * Permite refrescar la página actual del navegador.
     * @throws Exception
     */
    public void refreshPage() throws Exception {
        driver.navigate().refresh();
    }

}
