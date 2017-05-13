package udacitynano.com.br.cafelegal.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{

    private Context mContext;
    private static final int DATABASE_VERSION = 9;

    static final String DATABASE_NAME = "cafelegal.db";

    public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            mContext = context;
    }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            final String SQL_CREATE_PESSOA_TABLE = "CREATE TABLE " + DatabaseContract.PessoaEntry.TABLE_NAME + " (" +
                    DatabaseContract.PessoaEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DatabaseContract.PessoaEntry.COLUMN_ID_SERVER + " INTEGER, " +
                    DatabaseContract.PessoaEntry.COLUMN_NOME + " TEXT, " +
                    DatabaseContract.PessoaEntry.COLUMN_NOME_MEIO + " TEXT, " +
                    DatabaseContract.PessoaEntry.COLUMN_SOBRENOME + " TEXT, " +
                    DatabaseContract.PessoaEntry.COLUMN_EMAIL + " TEXT, " +
                    DatabaseContract.PessoaEntry.COLUMN_CEP + " INTEGER, " +
                    DatabaseContract.PessoaEntry.COLUMN_ENDERECO + " TEXT, " +
                    DatabaseContract.PessoaEntry.COLUMN_NUMERO + " TEXT, " +
                    DatabaseContract.PessoaEntry.COLUMN_COMPLEMENTO + " TEXT, " +
                    DatabaseContract.PessoaEntry.COLUMN_BAIRRO + " TEXT, " +
                    DatabaseContract.PessoaEntry.COLUMN_CIDADE + " TEXT, " +
                    DatabaseContract.PessoaEntry.COLUMN_ESTADO + " TEXT, " +
                    DatabaseContract.PessoaEntry.COLUMN_PAIS + " TEXT, " +
                    DatabaseContract.PessoaEntry.COLUMN_SEXO + " TEXT, " +
                    DatabaseContract.PessoaEntry.COLUMN_NUMERO_INSC_OAB + " TEXT, " +
                    DatabaseContract.PessoaEntry.COLUMN_SECCIONAL + " TEXT, " +
                    DatabaseContract.PessoaEntry.COLUMN_TIPO_INSCRICAO + " TEXT, " +
                    DatabaseContract.PessoaEntry.COLUMN_FONE_COMERCIAL + " TEXT, " +
                    DatabaseContract.PessoaEntry.COLUMN_CONFIRMADO_CNA + " TEXT, " +
                    DatabaseContract.PessoaEntry.COLUMN_TWITTER + " TEXT, " +
                    DatabaseContract.PessoaEntry.COLUMN_LINKEDIN + " TEXT, " +
                    DatabaseContract.PessoaEntry.COLUMN_ESPECIALISTA_UM + " TEXT, " +
                    DatabaseContract.PessoaEntry.COLUMN_ESPECIALISTA_DOIS + " TEXT, " +
                    DatabaseContract.PessoaEntry.COLUMN_NOTIFICATION_REGISTRADO + " TEXT, " +
                    DatabaseContract.PessoaEntry.COLUMN_LATITUTE + " REAL, " +
                    DatabaseContract.PessoaEntry.COLUMN_LONGITUDE + " REAL " +
                    " );";


            final String SQL_CREATE_CONVITE_TABLE = "CREATE TABLE " + DatabaseContract.ConviteEntry.TABLE_NAME + " (" +
                    DatabaseContract.ConviteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DatabaseContract.ConviteEntry.COLUMN_ID_CONVITE_SERVER + " INTEGER, " +
                    DatabaseContract.ConviteEntry.COLUMN_CONVIDA_ID + " TEXT, " +
                    DatabaseContract.ConviteEntry.COLUMN_RESPONDE_ID + " TEXT, " +
                    DatabaseContract.ConviteEntry.COLUMN_DATA_CONVITE + " TEXT, " +
                    DatabaseContract.ConviteEntry.COLUMN_CONVITE_ACEITO + " TEXT, " +
                    DatabaseContract.ConviteEntry.COLUMN_CHAT_FIREBASE  + " TEXT, " +
                    DatabaseContract.ConviteEntry.COLUMN_ESPECIALIDADE  + " TEXT, " +
                    DatabaseContract.ConviteEntry.COLUMN_AREA_LOCATION  + " TEXT, " +
                    DatabaseContract.ConviteEntry.COLUMN_NOME_CONVIDA + " TEXT, " +
                    DatabaseContract.ConviteEntry.COLUMN_NOME_ADVOGADO + " TEXT, " +
                    DatabaseContract.ConviteEntry.COLUMN_ADVOGADO_OAB  + " TEXT " +
            " );";

            sqLiteDatabase.execSQL(SQL_CREATE_PESSOA_TABLE);
            sqLiteDatabase.execSQL(SQL_CREATE_CONVITE_TABLE);
            Log.e("Debug","Debug02 "+SQL_CREATE_PESSOA_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            //TODO only for tests
            String SQL_DROP_TABLE_PESSOA =
                    "DROP TABLE IF EXISTS " + DatabaseContract.PessoaEntry.TABLE_NAME;


            String SQL_DROP_TABLE_CONVITE =
                    "DROP TABLE IF EXISTS " + DatabaseContract.ConviteEntry.TABLE_NAME;


            sqLiteDatabase.execSQL(SQL_DROP_TABLE_PESSOA);
            sqLiteDatabase.execSQL(SQL_DROP_TABLE_CONVITE);

            onCreate(sqLiteDatabase);

        }

}
