package udacitynano.com.br.cafelegal.util;

public interface Constant {

    String INTENT_FRAGMENT_TYPE = "fragment_type";
    String CONVITE_FRAGMENT = "convite_fragment";
    String HISTORICO_CONVITES_FRAGMENT = "historico_convites_fragment";
    String LISTA_ADVOGADOS_FRAGMENT = "lista_advogados_fragment";
    String LISTA_CONVITES_ABERTOS_FRAGMENT = "lista_convites_abertos_fragment";
    String MAPA_ADVOGADOS_PROXIMOS_FRAGMENT = "mapa_advogados_proximos_fragment";
    String PERFIL_FRAGMENT = "perfil_fragment";

    //Network


    String SERVER_API_CAFE_LEGAL = "https://www.cafelegal.net/webapi/cale";
    //String PESSOA_CAFE_LEGAL  = "/pessoa";
    String CONVITE_CAFE_LEGAL = "/convite";
    String ADVOGADO = "/pessoa/advogado";
    String ADVOGADOS = "/pessoa/advogados";
    String CLIENTE = "/pessoa/cliente";
    String NOTIFICATION_TOKEN = "/token_notificacao";
    String ABERTOS = "/abertos";

    //Type volley called
    int PERFIL = 0;
    int CONVITE = 1;
    int LOGIN = 2;
    int ANDROID_SERVICE = 3;
    int CONVITES_ABERTOS = 4;


    static final String INSTANCE_ID_TOKEN_RETRIEVED = "iid_token_retrieved";
    static final String FRIENDLY_MSG_LENGTH = "friendly_msg_length";




}
