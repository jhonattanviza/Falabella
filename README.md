> ## Automatización Falabella
>
> Prueba técnica Front.


### Pre-requisitos 📋

JDK 17 o superior

APACHE-MAVEN
- - -
### Ejecución por línea de comandos.  ⚙️

La ejecución de las pruebas se puede realizar por línea de comandos de la siguiente manera:

```
mvn clean test -Dcucumber.filter.tags="@Todos"
```

Para ejecutar un escenario en específico reemplazar el tag como en el ejemplo siguiente:

```
mvn clean test -Dcucumber.filter.tags="@BuscarPorTextoOrdenandoPorMenorPrecio"
```
- - -
### Variables

Enviar variables es opcional recuerde agregar -D antes del nombre de la variable.

Se permite recibir las siguientes variables por consola:

```
browser (Chrome,Firefox) Nombre del navegador Chrome esta por defecto.
environment (String) Nombre del ambiente en el que se realizará la ejecución.
```
Ejemplo:

```
mvn clean test -Dcucumber.filter.tags="@Todos" -Dbrowser=Chrome
```
- - -
### Reportes de ejecución

Para visualizar los reportes, luego de ejecutar las pruebas se debe acceder a la siguiente ruta.

```
/target/test-output/ExtendReport/Index.html
```
- - -

### Casos de pruebas y tags 🔩

Para ejecutar todos los escenarios utilizar el tag **@todos**

Para ejecutar solo los escenarios de compra utilizar el tag **@compras**

Para ejecutar solo los escenarios de registro y login utilizar el tag **@RegistroYLogin**



| Tag | Escenario |
- - -
* @Registro - _Registro en la tienda virtual con correo electrónico_
- - -
* @LoginValido - _Inicio de sesión en la tienda virtual con datos válidos_
- - -
* @LoginInvalido - _Inicio de sesión en la tienda virtual con datos inválidos_
- - -
* @BuscarPorTextoOrdenandoPorMenorPrecio - _Realizar la orden de un computador utilizando el cuadro de búsqueda y ordenando para seleccionar el de menor precio._
- - -
* @BuscarPorCategoriaYEliminarProductoDelaBolsa - _Realizar la orden de un producto de una categoría y eliminarlo de la Bolsa_


## Construido con 🛠️

Java

Maven

Selenium Web Driver

Junit

Cucumber

El patrón de diseño utilizado es Page Object Model, para la escritura de los test se utilizó la librería de Cucumber con el fin de escribir los escenarios en lenguaje gherkin utilizando la metodología BDD.

Para la generación de reportes se utilizó ExtendReport y adicional la generación de reportes por defecto de Cucumber.


## Autor ✒️

Jhonattan Villamil Zamora
[LinkedIn](https://www.linkedin.com/in/jhonattanviza/)
