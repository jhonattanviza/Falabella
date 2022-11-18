package StepDefinitions;
/**
 * Pasos que permiten realizar las acciones y validaciones de los escenarios
 * de Registro en el Sitio Falabella.com.co
 * @author Jhonattan villamil zamora
 */
import Functions.SeleniumFunctions;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;

public class MyStepdefsRegister {

    SeleniumFunctions functions = new SeleniumFunctions();

    @Dado("que necesito registrarme en la tienda virtual")
    public void queNecesitoRegistrarmeEnLaTiendaVirtual() throws Exception {
        functions.readMain();
        functions.iLoadTheDOMInformation("Registro y Login.json");
        int anuncio = functions.countElement("Cerrar Anuncio");
        if (anuncio != 0) {
            functions.iClicInElement("Cerrar Anuncio");
        }
        functions.iClicInElement("Boton Ingreso");
        functions.iClicInElement("Registrate");
        functions.iWaitTime(5);
        functions.attachScreenShot();

    }

    @Cuando("ingreso toda la informacion del registro")
    public void ingresoTodaLaInformacionDelRegistro() throws Exception {
        functions.iSetElementWithText("Nombre", "Pedro");
        functions.iSetElementWithText("Primer Apellido", "Morales");
        functions.iClicInElement("Tipo de Documento");
        functions.iSelectContainsText("Lista", "Cédula de Ciudadanía");
        long numer = System.currentTimeMillis();
        functions.iSetElementWithText("Numero de Documento", String.valueOf(numer));
        functions.iSetElementWithText("Celular", String.valueOf(numer - 500));
        functions.iSetElementWithKeyValue("Correo", "correo");
        functions.iSetElementWithKeyValue("Contrasena", "contrasena");
        functions.iWaitTime(2);
        functions.attachScreenShot();

    }

    @Y("acepto los termino y condiciones")
    public void aceptoLosTerminoYCondiciones() throws Exception {
        functions.iClicInElement("CheckBox Terminos y Condiciones");
        functions.iWaitTime(2);

    }

    @Entonces("puedo registrarme en el sistema y me genera un mensaje de confirmacion.")
    public void puedoRegistrarmeElSistemaYMeGeneraUnMensajeDeConfirmacion() throws Exception {
        functions.iClicInElement("CheckBox CMR Puntos");
        functions.checkIfElementIsEnable("Boton Registrarme");
        functions.iClicInElement("Boton Registrarme");
        functions.iWaitTime(5);
        functions.attachScreenShot();
    }
}
