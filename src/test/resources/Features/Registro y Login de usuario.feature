#language: es

@Todos
  @RegistroYLogin
Característica: Registro
  Validar que se puedan registrar los usuarios


  @Registro
  Escenario: Registro en la tienda virtual con correo electronico
    Dado que necesito registrarme en la tienda virtual
    Cuando ingreso toda la informacion del registro
    Y acepto los termino y condiciones
    Entonces puedo registrarme en el sistema y me genera un mensaje de confirmacion.


  @LoginValido
  Escenario: Inicio de sesion en la tienda virtual con datos validos
    Dado que el usuario se encuentra registrado en la tienda virtual
    Cuando inicia sesion con credenciales validas
    Entonces el sistema permite el ingreso y muestra el primer nombre "Pedro"

  @LoginInvalido
  Escenario: Inicio de sesion en la tienda virtual con datos invalidos
    Dado que el usuario se encuentra registrado en la tienda virtual
    Cuando inicia sesion con credenciales invalidas
    Entonces el sistema no permite el ingreso y muestra un mensaje de error

