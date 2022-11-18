package StepDefinitions;
/**
 * Pasos que permiten realizar las acciones y validaciones de los escenarios
 * de Login en el Sitio Falabella.com.co
 * @author Jhonattan villamil zamora
 */

import Functions.SeleniumFunctions;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;

public class MyStepdefsLogin {
    SeleniumFunctions functions = new SeleniumFunctions();

    @Dado("que el usuario se encuentra registrado en la tienda virtual")
    public void queElUsuarioSeEncuentraRegistradoEnLaTiendaVirtual() throws Exception {

        functions.readMain();
        functions.iLoadTheDOMInformation("Registro y Login.json");
        int anuncio = functions.countElement("Cerrar Anuncio");
        if (anuncio != 0) {
            functions.iClicInElement("Cerrar Anuncio");
        }
        functions.iClicInElement("Boton Ingreso");
        functions.attachScreenShot();
    }

    @Cuando("inicia sesion con credenciales validas")
    public void iniciaSesionConCredencialesValidas() throws Exception {
        functions.iSetElementWithKeyValue("Correo", "correo");
        functions.iSetElementWithText("Contrasena", "Asd12345");
        functions.checkIfElementIsPresent("Mostrar Contrasena");
        functions.checkIfElementIsEnable("Boton Ingresar");
        functions.iClicInElement("Boton Ingresar");
        functions.attachScreenShot();
    }

    @Entonces("el sistema permite el ingreso y muestra el primer nombre {string}")
    public void elSistemaLeDaLaBienvenida(String nombre) throws Exception {
        functions.checkPartialTextElementPresent("Nombre Usuario", nombre);

    }

    @Cuando("inicia sesion con credenciales invalidas")
    public void iniciaSesionConCredencialesInvalidas() throws Exception {
        functions.iSetElementWithKeyValue("Correo", "correo");
        functions.iSetElementWithText("Contrasena", "Pruebas123456");
        functions.checkIfElementIsPresent("Mostrar Contrasena");
        functions.checkIfElementIsEnable("Boton Ingresar");
        functions.iClicInElement("Boton Ingresar");
    }

    @Entonces("el sistema no permite el ingreso y muestra un mensaje de error")
    public void elSistemaNoPermiteElIngresoYMuestraUnMensajeDeError() throws Exception {
        functions.checkPartialTextElementPresent("Alerta Error", "¡Lo sentimos! Ha ocurrido un error inesperado. Por favor, vuelve a intentarlo nuevamente.");
    }
}
