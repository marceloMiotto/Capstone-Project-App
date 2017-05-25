package udacitynano.com.br.cafelegal.util;

import java.util.ArrayList;

import udacitynano.com.br.cafelegal.R;

public interface Constant {

    String INTENT_FRAGMENT_TYPE = "fragment_type";
    String CONVITE_FRAGMENT = "convite_fragment";
    String PERFIL_FRAGMENT = "perfil_fragment";

    //Network

    String SERVER_API_CAFE_LEGAL = "https://www.cafelegal.net/webapi/cale";
    String CONVITE_CAFE_LEGAL = "/convite";
    String ADVOGADO = "/pessoa/advogado";
    String ADVOGADOS = "/pessoa/advogados";
    String CLIENTE = "/pessoa/cliente";
    String NOTIFICATION_TOKEN = "/token_notificacao";
    String ABERTOS = "/abertos";
    String ACEITO  = "/aceito";

    //Type volley called
    int PERFIL = 0;
    int CONVITE = 1;
    int LOGIN = 2;
    int ANDROID_SERVICE = 3;
    int CONVITE_ACEITO = 4;

    String FRIENDLY_MSG_LENGTH = "friendly_msg_length";
    String ADVOGADO_ESCOLHIDO = "ADVOGADO_ESCOLHIDO";


    String word_type = "type";
    String word_cliente = "cliente";
    String content_application_json = "application/json; charset=utf-8";
    String content_header = "Content-Type";



}
