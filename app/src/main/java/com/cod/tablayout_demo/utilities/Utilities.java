package com.cod.tablayout_demo.utilities;

public class Utilities {

    // Servidor
    public static final String IP_SERVIDOR = "http://192.168.0.101";
    // Puerto
    public static final String PUERTO = "8080";

    // Url de WS
    public static final String LOCATION_WS_CONSULTAR_LISTA_MESAS = "/proyectos/Adobes%20Android/wsJSONConsultarListaMesas.php";
    public static final String URL_CONSULTAR_LISTA_MESAS = IP_SERVIDOR + ":" + PUERTO + LOCATION_WS_CONSULTAR_LISTA_MESAS;

    public static final String LOCATION_WS_CONSULTAR_LISTA_COMANDAS = "/proyectos/Adobes%20Android/wsJSONConsultarListaComandas.php";
    public static final String URL_CONSULTAR_LISTA_COMANDAS = IP_SERVIDOR + ":" + PUERTO + LOCATION_WS_CONSULTAR_LISTA_COMANDAS;


    // TABLAS
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

    // Vibracion
    public static final int VIBRACION_LONG_CLICK = 100;

    // Mensajes para WebService
    public static final String MENSAJE_WS_CONSULTA = "Consultando ...";
    public static final String MENSAJE_WS_ERROR_RESPONSE = "No se puede conectar.";
    public static final String MENSAJE_WS_CONNECTION_FAILED = "No se ha podido establecer la conexión con el servidor.";



}
