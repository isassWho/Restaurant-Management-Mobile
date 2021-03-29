package com.cod.tablayout_demo.utilities;

public class Utilities {

    /**********  SERVIDOR ************/
    // Servidor
    public static final String SERVER_IP = "http://192.168.3.105";
    //adobes
    //public static final String SERVER_IP = "http://192.168.3.118";
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
    // Query Reservations
    public static final String URL_WS_QUERY_RESERVATIONS = SERVER_IP + ":" + PORT + "/proyectos/Adobes%20Android/WS_V0.2/wsJSONQueryReservations.php";
    // Update Reservation
    public static final String URL_WS_UPDATE_RESERVATION = SERVER_IP + ":" + PORT + "/proyectos/Adobes%20Android/WS_V0.2/wsJSONUpdateReservation.php?";
    // Register Reservation
    public static final String URL_WS_REGISTER_RESERVATION = SERVER_IP + ":" + PORT + "/proyectos/Adobes%20Android/WS_V0.2/wsJSONRegisterReservation.php?";
    // Change Status from tt_waiting_list
    public static final String URL_WS_CANCEL_WAITINGLIST = SERVER_IP + ":" + PORT + "/proyectos/Adobes%20Android/WS_V0.2/wsJSONCancelWaitingList.php?";

    

    /**************** VIBRACION *******************/
    // Vibracion
    public static final int VIBRACION_LONG_CLICK = 200;

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

    // mensaje Cancelaciones
    public static final String MESSAGE_WS_CANCEL = "Cancelando ...";
    public static final String MESSAGE_WS_CANCEL_SUCCESSFULLY = "Se canceló exitosamente.";
    public static final String MESSAGE_WS_CANCEL_FAILED = "No se pudo cancelar.";

    // mensaje registro
    public static final String MESSAGE_WS_REGISTER = "Registrando ...";
    public static final String MESSAGE_WS_REGISTER_SUCCESSFULLY = "Se registró exitosamente.";
    public static final String MESSAGE_WS_REGISTER_FAILED = "No se pudo registrar.";

    /****************** FORMATS *******************/
    public static final String FORMAT_DATE = "yyyy/MM/dd";
    public static final String FORMAT_TIME = "HH:mm:ss";

    /****************** STATUS WAITINGLIST *******************/
    public static final String STATUS_ACTIVE = "ACTIVA";
    public static final String STATUS_CANCELED = "CANCELADA";


}
