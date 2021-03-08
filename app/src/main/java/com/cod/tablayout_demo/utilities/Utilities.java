package com.cod.tablayout_demo.utilities;

public class Utilities {

    /**********  SERVIDOR ************/
    // Servidor
    //public static final String SERVER_IP = "http://192.168.0.103";
    public static final String SERVER_IP = "http://192.168.3.108";
    // Puerto
    public static final String PORT = "8080";

    /************* WEB SERVICES********************/
    // Url de WS
    // Query WaitingList
    public static final String URL_WS_QUERY_WAITINGLIST = SERVER_IP + ":" + PORT + "/proyectos/Adobes%20Android/WS_V0.2/wsJSONQueryWaitingList.php";
    // Register WaitingList
    public static final String URL_WS_REGISTER_WAITINGLIST = SERVER_IP + ":" + PORT + "/proyectos/Adobes%20Android/WS_V0.2/wsJSONRegisterWaitingList.php?" ;
    // Update WaitingList
    public static final String URL_WS_UPDATE_WAITINGLIST = SERVER_IP + ":" + PORT + "/proyectos/Adobes%20Android/WS_V0.2/wsJSONUpdateWaitingList.php?";

     // consultar lista de mesas
    public static final String LOCATION_WS_CONSULTAR_LISTA_MESAS = "/proyectos/Adobes%20Android/wsJSONConsultarListaMesas.php";
    public static final String URL_CONSULTAR_LISTA_MESAS = SERVER_IP + ":" + PORT + LOCATION_WS_CONSULTAR_LISTA_MESAS;

    // consultar lista de comandas
    public static final String LOCATION_WS_CONSULTAR_LISTA_COMANDAS = "/proyectos/Adobes%20Android/wsJSONConsultarListaComandas.php";
    public static final String URL_CONSULTAR_LISTA_COMANDAS = SERVER_IP + ":" + PORT + LOCATION_WS_CONSULTAR_LISTA_COMANDAS;

    // editar mesa
    public static final String LOCATION_WS_ACTUALIZAR_MESA = "/proyectos/Adobes%20Android/wsJSONActualizacionMesa.php?";
    public static final String URL_ACTUALIZAR_MESA = SERVER_IP + ":" + PORT + LOCATION_WS_ACTUALIZAR_MESA;

    // eliminar mesa
    public static final String LOCATION_WS_ELIMINAR_MESA = "/proyectos/Adobes%20Android/wsJSONEliminarMesa.php?";
    public static final String URL_WS_ELIMINAR_MESA = SERVER_IP + ":" + PORT + LOCATION_WS_ELIMINAR_MESA;

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
    public static final String MESSAGE_WS_QUERY = "Consultando ...";

    public static final String MESSAGE_WS_ERROR_RESPONSE = "No se puede conectar.";
    public static final String MESSAGE_WS_CONNECTION_FAILED = "No se ha podido establecer la conexión con el servidor.";

    // mensaje Actualizaciones
    public static final String MESSAGE_WS_UPDATE = "Actualizando ...";
    public static final String MESSAGE_WS_UPDATE_SUCCESSFULLY = "Se actualizó exitosamente.";
    public static final String MESSAGE_WS_UPDATE_FAILED = "No se pudo actualizar.";

    // mensaje Eliminaciones
    public static final String MESSAGE_WS_DELETE = "Eliminando ...";
    public static final String MESSAGE_WS_DELETE_SUCCESSFULLY = "Se eliminó exitosamente.";
    public static final String MESSAGE_WS_DELETE_FAILED = "No se pudo eliminar.";

    // mensaje registro
    public static final String MESSAGE_WS_REGISTER = "Registrando ...";
    public static final String MESSAGE_WS_REGISTER_SUCCESSFULLY = "Se registró exitosamente.";
    public static final String MESSAGE_WS_REGISTER_FAILED = "No se pudo registrar.";

    /****************** FORMATS *******************/
    public static final String FORMAT_DATE = "yyyy/MM/dd";
    public static final String FORMAT_TIME = "HH:mm:ss";


}
