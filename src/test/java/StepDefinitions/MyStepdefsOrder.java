package StepDefinitions;
/**
 * Pasos que permiten realizar las acciones y validaciones de los escenarios
 * de busquedas en el catálogo y acciones en la bolsa de compras del Sitio Falabella.com.co
 * @author Jhonattan villamil zamora
 */
import Functions.SeleniumFunctions;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import org.junit.Assert;

import java.util.List;

public class MyStepdefsOrder {

    SeleniumFunctions functions = new SeleniumFunctions();


    @Dado("que necesito realizar la compra de un producto")
    public void QueNecesitoRealizarLaCompraDeUnProducto() throws Exception {
        functions.readMain();
        functions.iLoadTheDOMInformation("Compras.json");
        int anuncio = functions.countElement("Cerrar Anuncio");
        if (anuncio != 0) {
            functions.iClicInElement("Cerrar Anuncio");
        }
        functions.refreshPage();
    }

    @Cuando("realizo la busqueda con el nombre {string}")
    public void realizoLaBusquedaConElNombre(String arg0) throws Exception {
        functions.iWaitTime(5);
        functions.iClicInElement("Barra Busqueda");
        functions.iSetElementWithText("Barra Busqueda", arg0);
        functions.iClicInElement("Boton Buscar");
        functions.page_has_loaded();
    }

    @Entonces("se visualizan los resultados que coinciden con la busqueda realizada.")
    public void seVisualizanLosResultadosQueCoincidenConLaBusquedaRealizada() throws Exception {
        functions.waitForElementPresent("Ordenar Por");
        functions.checkIfElementIsPresent("Resultado Busqueda",false);
        functions.scrollToElement("Ordenar Por");
        functions.attachScreenShot();
    }

    @Dado("que necesito ordenar los resultados de busqueda")
    public void queNecesitoOrdenarLosResultadosDeBusqueda() throws Exception {
        functions.scrollToElement("Ordenar Por");

    }

    @Cuando("ordeno los productos por {string}")
    public void ordenoLosProductosPor(String arg0) throws Exception {
        functions.iClicInElement("Ordenar Por");
        functions.attachScreenShot();
        functions.iSelectContainsText("Lista", arg0);
    }

    @Entonces("se muestran los productos con menor precio primero.")
    public void seMuestranLosProductosConMenorPrecioPrimero() throws Exception {
        //  Validar que el orden de los 5 primeros productos sea de menor a mayor.
        functions.waitForElementNotPresent("Spinner");
        functions.attachScreenShot();
        int b = 0;
        for (int i = 1; i < 6; i++) {
            int a;
            String value = functions.GetTextElement("(//*/ol/li[1]/div/span)[" + i + "]");
            value = value.replace("$ ", "");
            value = value.replace(".", "");
            int fin = value.indexOf(" ");
            if (fin != -1) {
                value = value.substring(0, fin);
            }
            a = Integer.parseInt(value);

            System.out.println(a);
            boolean esMayor = a > b;
            Assert.assertTrue("No se esta mostrando el orden correcto", esMayor);
            b = a;

        }


    }

    @Dado("que necesito agregar un producto a la bolsa de compras")
    public void necesitoAgregarUnProductoALaBolsaDeCompras() {
    }

    @Cuando("agrego el producto")
    public void agregoElProducto() throws Exception {
        functions.scrollToElement("Boton Agregar a la Bolsa");
        functions.attachScreenShot();
        functions.iClicInElement("Boton Agregar a la Bolsa");
        functions.waitForElementNotPresent("Spinner");
    }

    @Entonces("el sistema confirma que el producto fue agregado")
    public void elSistemaConfirmaQueElProductoFueAgregado() throws Exception {
        functions.checkPartialTextElementPresent("Titulo Modal", "Producto(s) agregado(s) a la bolsa de compras");
        functions.iWaitTime(3);
        functions.iClicInElement("Boton Cerrar Modal");
    }


    @Dado("que tengo un producto en la bolsa de compras")
    public void queNecesitoVerElCarritoDeCompras() throws Exception {
        functions.scrollToElement("Bolsa con un Elemento");
        functions.checkIfElementIsPresent("Bolsa con un Elemento");
    }

    @Cuando("selecciono la bolsa")
    public void seleccionoLaBolsa() throws Exception {
        functions.iClicInElement("Bolsa de Compras");

    }

    @Entonces("se visualiza el resumen de la orden con los productos agregados.")
    public void seVisualizaElResumenDeLaOrdenConLosProductosAgregados() throws Exception {
        functions.checkPartialTextElementPresent("Cantidad Productos", "1");
    }

    @Dado("que voy a comprar los productos agregados en la bolsa")
    public void queVoyAComprarLosProductosAgregadosEnLaBolsa() throws Exception {
        functions.scrollToElement("Boton Ir a Comprar");
        functions.iClicInElement("Boton Ir a Comprar");
        functions.waitForElementPresent("Correo");
        functions.iSetElementWithKeyValue("Correo", "correo");
        functions.iClicInElement("Boton Continuar");
        functions.waitForElementNotPresent("Spinner");

    }

    @Cuando("ingreso la informacion de la direccion del despacho")
    public void ingresoLaInformacionDeLaDireccionDelDespacho() throws Exception {
        functions.iWaitTime(3);
        functions.selectOptionDropdownByText("Departamento", "TOLIMA");
        functions.iWaitTime(3);
        functions.selectOptionDropdownByText("Ciudad", "IBAGUE");
        functions.iWaitTime(3);
        functions.selectOptionDropdownByText("Barrio", "IBAGUE");
        functions.iWaitTime(2);
        functions.iClicInElement("Boton Continuar");
        functions.waitForElementPresent("Direccion");
        functions.scrollToElement("Direccion");
        functions.iSetElementWithText("Direccion", "Kr 8 17 71");
        functions.iSetElementWithText("Torre", "25");
        functions.attachScreenShot();
    }

    @Y("puedo elegir el metodo de pago")
    public void ingresoLaInformacionDelPagoPorPSE() {
        // No se implementa debido a que requiere inicio de session.
    }

    @Dado("necesito cancelar la orden de la bolsa de compras")
    public void queNecesitoCancelarOrdenDeLaBolsaDeCompras() throws Exception {
        functions.iClicInElement("Bolsa de Compras");
        functions.attachScreenShot();
    }

    @Cuando("elimino el producto")
    public void eliminoElProducto() throws Exception {

        functions.scrollToElement("Eliminar Producto");
        functions.iClicInElement("Eliminar Producto");
        functions.waitForElementNotPresent("Spinner");

    }

    @Entonces("la bolsa de compras se muestra vacia.")
    public void laBolsaDeComprasSeMuestraVacia() throws Exception {
        functions.iWaitTime(3);
        functions.scrollToElement("Bolsa Vacia");
        functions.checkIfElementIsPresent("Bolsa Vacia");
    }


    @Cuando("realizo la busqueda por la categoria {string} y subcategoria {string}")
    public void realizoLaBusquedaPorLaCategoriaYSub(String categoria, String subcategoria) throws Exception {
        functions.iClicInElement("Menu Categorias");
        functions.iClicInElement("//div[contains(@class,'menuItem')]//p[contains(.,'" + categoria + "')]");
        functions.iWaitTime(1);
        functions.attachScreenShot();
        functions.iClicInElement("//div[contains(@class,'Menu')]//a[contains(.,'" + subcategoria + "')]");
        functions.iWaitTime(1);

    }

    @Cuando("realizo la busqueda por la categoria {string}")
    public void realizoLaBusquedaPorLaCategoria(String categoria) throws Exception {
        functions.iClicInElement("Menu Categorias");
        functions.attachScreenShot();
        functions.iClicInElement("//div[contains(@class,'menuItem')]//p[contains(.,'" + categoria + "')]");
        functions.iWaitTime(1);


    }

    @Entonces("puedo ver todas las siguientes subcategorias.")
    public void puedoVerTodasLasSiguientesSubcategorias(DataTable subcategorias) throws Exception {
        List<String> todos = subcategorias.values();

        for (String seccion : todos) {

            if (seccion != null) {
                functions.checkIfElementIsPresent("//div[contains(@class,'Menu')]//a[contains(.,'" + seccion + "')]", false);

            }
        }

        functions.attachScreenShot();

    }

    @Dado("que quiero conocer las productos de una catogoria")
    public void queQuieroConocerLasProductosDeUnaCatogoria() throws Exception {
        QueNecesitoRealizarLaCompraDeUnProducto();
    }
}
