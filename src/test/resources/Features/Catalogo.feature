#language: es

@Todos
@Compras
Característica: Realizar ordenes
  Validar que se puedan realizar ordenes


  @BuscarPorTextoOrdenandoPorMenorPrecio
  Escenario: Realizar la orden de un computador utilizando el cuadro de busqueda y ordenando para seleccionar el de menor precio.

    Dado que necesito realizar la compra de un producto
    Cuando realizo la busqueda con el nombre "Computador"
    Entonces se visualizan los resultados que coinciden con la busqueda realizada.

    Dado que necesito ordenar los resultados de busqueda
    Cuando ordeno los productos por "Precio de menor a mayor"
    Entonces se muestran los productos con menor precio primero.

    Dado que necesito agregar un producto a la bolsa de compras
    Cuando agrego el producto
    Entonces el sistema confirma que el producto fue agregado

    Dado que tengo un producto en la bolsa de compras
    Cuando selecciono la bolsa
    Entonces se visualiza el resumen de la orden con los productos agregados.


    Dado que voy a comprar los productos agregados en la bolsa
    Cuando ingreso la informacion de la direccion del despacho
    Entonces puedo elegir el metodo de pago


  @BuscarPorCategoriaYEliminarProductoDelaBolsa
  Escenario: Realizar la orden de un producto de una categoria y eliminarlo de la Bolsa

    Dado que necesito realizar la compra de un producto
    Cuando realizo la busqueda por la categoria "Electrodomésticos" y subcategoria "Freidoras"
    Entonces se visualizan los resultados que coinciden con la busqueda realizada.

    Dado que necesito agregar un producto a la bolsa de compras
    Cuando agrego el producto
    Entonces el sistema confirma que el producto fue agregado

    Dado que tengo un producto en la bolsa de compras
    Y necesito cancelar la orden de la bolsa de compras
    Cuando elimino el producto
    Entonces la bolsa de compras se muestra vacia.

  @ConsultarCategoriasTecnologia
  Escenario: Validar que se muestren correctamente todas las subcategorias de una categoria
    Dado que quiero conocer las productos de una catogoria
    Cuando realizo la busqueda por la categoria "Tecnología"
    Entonces puedo ver todas las siguientes subcategorias.
      | TV                        | Gaming                 | Cámaras               | Audífonos                          |
      | Accesorios TV             | Accesorios videojuegos | Accesorios fotografía | Bluetooth                          |
      | Barras de sonido          | Audífonos gaming       | Deportivas            | Con cable                          |
      | Proyectores y video beams | Computadores gaming    | Drones y Accesorios   | Deportivos                         |
      | Soportes                  | Consolas               | Instantáneas          | Gaming                             |
      | Streaming                 | Nintendo               | Profesionales         | Hogar inteligente                  |
      | Teatros en casa           | Sillas Gaming          | Semiprofesionales     | Asistente de voz                   |
      | Televisores               | Playstation            | Videocámaras          | Cámaras y Alarmas Inteligentes     |
      | Computadores              | Videojuegos            | Parlantes             | Cerraduras y Sensores Inteligentes |
      | Accesorios computación    | Xbox                   | Barras de sonido      | Electrodomésticos                  |
      | De Mesa                   | Smartwatch             | Bluetooth             | Iluminación                        |
      | Gaming                    | Apple watch            | Equipos de sonido     | Otros dispositivos                 |
      | Impresoras                | Samsung watch          | Inteligentes          | Routers                            |
      | Monitores                 | Huawei                 | de Fiesta             | Switches y plugs                   |
      | Portátiles                | Xiaomi                 | Portátiles            |                                    |
      | Routers y conectividad    | Sonido para carros     |                       |                                    |
      | Software                  | Teatros en casa        |                       |                                    |
      | Tablets                   |                        |                       |                                    |