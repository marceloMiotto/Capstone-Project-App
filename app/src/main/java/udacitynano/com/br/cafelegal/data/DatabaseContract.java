package udacitynano.com.br.cafelegal.data;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {


        public static final String CONTENT_AUTHORITY = "udacitynano.com.br.cafelegal.app";
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

        public static final String PATH_PESSOA = "pessoa";
        public static final String PATH_CONVITE = "convite";


        public static final class PessoaEntry implements BaseColumns {
            public static final String TABLE_NAME = "pessoas";

            public static final String COLUMN_ID_SERVER = "id_server";
            public static final String COLUMN_NOME = "nome";
            public static final String COLUMN_NOME_MEIO = "nome_meio";
            public static final String COLUMN_SOBRENOME = "sobrenome";
            public static final String COLUMN_EMAIL = "email";
            public static final String COLUMN_CEP = "cep";
            public static final String COLUMN_ENDERECO = "endereco";
            public static final String COLUMN_NUMERO = "numero";
            public static final String COLUMN_COMPLEMENTO = "complemento";
            public static final String COLUMN_BAIRRO = "bairro";
            public static final String COLUMN_CIDADE = "cidade";
            public static final String COLUMN_ESTADO = "estado";
            public static final String COLUMN_PAIS = "pais";
            public static final String COLUMN_SEXO = "sexo";
            public static final String COLUMN_NUMERO_INSC_OAB = "inscricao_oab";
            public static final String COLUMN_SECCIONAL = "seccional";
            public static final String COLUMN_TIPO_INSCRICAO = "tipo_inscricao";
            public static final String COLUMN_FONE_COMERCIAL = "fone_comercial";
            public static final String COLUMN_CONFIRMADO_CNA = "confirmado_cna";
            public static final String COLUMN_TWITTER = "twitter";
            public static final String COLUMN_LINKEDIN = "linked_in";
            public static final String COLUMN_ESPECIALISTA_UM = "especialista_um";
            public static final String COLUMN_ESPECIALISTA_DOIS = "especialista_dois";
            public static final String COLUMN_NOTIFICATION_REGISTRADO = "notification_registrado";
            public static final String COLUMN_LATITUTE = "latitude";
            public static final String COLUMN_LONGITUDE = "longitude";


            public static final Uri CONTENT_URI =
                    BASE_CONTENT_URI.buildUpon().appendPath(PATH_PESSOA).build();

            public static final String CONTENT_TYPE =
                    ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PESSOA;
            public static final String CONTENT_ITEM_TYPE =
                    ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PESSOA;

            public static Uri buildUserUri(long id) {
                return ContentUris.withAppendedId(CONTENT_URI, id);
            }

        }
            public static final class ConviteEntry implements BaseColumns {
                public static final String TABLE_NAME = "convites";

                public static final String COLUMN_ID_CONVITE_SERVER   = "id_convite_server";
                public static final String COLUMN_CONVIDA_ID          = "convida_id";
                public static final String COLUMN_RESPONDE_ID         = "responde_id";
                public static final String COLUMN_DATA_CONVITE        = "data_convite";
                public static final String COLUMN_CONVITE_ACEITO      = "convite_transmitido";



                public static final Uri CONTENT_URI =
                        BASE_CONTENT_URI.buildUpon().appendPath(PATH_CONVITE).build();

                public static final String CONTENT_TYPE =
                        ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CONVITE;
                public static final String CONTENT_ITEM_TYPE =
                        ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CONVITE;

                public static Uri buildUserUri(long id) {
                    return ContentUris.withAppendedId(CONTENT_URI, id);
                }

        }

}





