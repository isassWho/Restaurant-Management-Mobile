package com.cod.tablayout_demo.utilities;

public class Utilities {

    /**********  SERVIDOR ************/
    // Servidor
    public static final String IP_SERVIDOR = "http://192.168.0.101";
    // Puerto
    public static final String PUERTO = "8080";

    /************* WEB SERVICES********************/
     // Url de WS
     // consultar lista de mesas
    public static final String LOCATION_WS_CONSULTAR_LISTA_MESAS = "/proyectos/Adobes%20Android/wsJSONConsultarListaMesas.php";
    public static final String URL_CONSULTAR_LISTA_MESAS = IP_SERVIDOR + ":" + PUERTO + LOCATION_WS_CONSULTAR_LISTA_MESAS;

    // consultar lista de comandas
    public static final String LOCATION_WS_CONSULTAR_LISTA_COMANDAS = "/proyectos/Adobes%20Android/wsJSONConsultarListaComandas.php";
    public static final String URL_CONSULTAR_LISTA_COMANDAS = IP_SERVIDOR + ":" + PUERTO + LOCATION_WS_CONSULTAR_LISTA_COMANDAS;

    // consultar lista de espera
    public static final String LOCATION_WS_CONSULTAR_LISTA_DE_ESPERA = "/proyectos/Adobes%20Android/wsJSONConsultarLista.php";
    public static final String URL_CONSULTAR_LISTA_DE_ESPERA = IP_SERVIDOR + ":" + PUERTO + LOCATION_WS_CONSULTAR_LISTA_DE_ESPERA;

    // editar mesa
    //http://localhost:8080/proyectos/Adobes%20Android/wsJSONActualizacionMesa.php
    public static final String LOCATION_WS_ACTUALIZAR_MESA = "/proyectos/Adobes%20Android/wsJSONActualizacionMesa.php?";
    public static final String URL_ACTUALIZAR_MESA = IP_SERVIDOR + ":" + PUERTO + LOCATION_WS_ACTUALIZAR_MESA;

    // eliminar mesa
    //http://localhost:8080/proyectos/Adobes%20Android/wsJSONEliminarMesa.php?id=12
    public static final String LOCATION_WS_ELIMINAR_MESA = "/proyectos/Adobes%20Android/wsJSONEliminarMesa.php?";
    public static final String URL_WS_ELIMINAR_MESA = IP_SERVIDOR + ":" + PUERTO + LOCATION_WS_ELIMINAR_MESA;

    /****************** TABLAS ******************/
    // Tabla ListaDeEspera
    public static final String TABLA_LISTA_DE_ESPERA = "usuario";
    // Campos tabla
    public static final String LISTA_DE_ESPERA_CAMPO_ID = "id";
    public static final String LISTA_DE_ESPERA_CAMPO_NOMBRE = "nombre";
    public static final String LISTA_DE_ESPERA_CAMPO_PROFESION = "profesion";

    // Tabla Comandas
    public static final String TABLA_COMANDAS = "comanda";
    // Campos tabla
    public static final String COMANDAS_CAMPO_ID = "id";
    public static final String COMANDAS_CAMPO_PROPIETARIO = "propietario";
    public static final String COMANDAS_CAMPO_PERSONAS = "personas";

    // Tabla Mesas
    public static final String TABLA_MESAS = "mesa";
    // Campos tabla
    public static final String MESAS_CAMPO_ID = "id";
    public static final String MESAS_CAMPO_NOMBRE = "nombre";
    public static final String MESAS_CAMPO_PERSONAS = "personas";

    /**************** VIBRACION *******************/
    // Vibracion
    public static final int VIBRACION_LONG_CLICK = 100;

    /************** MENSAJES ****************/

    // Mensajes para WebService
    public static final String MENSAJE_WS_CONSULTA = "Consultando ...";
    public static final String MENSAJE_WS_ERROR_RESPONSE = "No se puede conectar.";
    public static final String MENSAJE_WS_CONNECTION_FAILED = "No se ha podido establecer la conexión con el servidor.";

    // mensaje Actualizaciones
    public static final String MENSAJE_WS_ACTUALIZACION = "Actualizando ...";
    public static final String MENSAJE_WS_ACTUALIZACION_EXITOSA = "Se actualizó exitosamente.";
    public static final String MENSAJE_WS_ACTUALIZACION_FALLIDA = "No se pudo actualizar.";

    // mensaje Eliminaciones
    public static final String MENSAJE_WS_ELIMINACION = "Eliminando ...";
    public static final String MENSAJE_WS_ELIMINACION_EXITOSA = "Se eliminó exitosamente.";
    public static final String MENSAJE_WS_ELIMINACION_FALLIDA = "No se pudo eliminar.";


}
