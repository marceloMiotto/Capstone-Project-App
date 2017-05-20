package udacitynano.com.br.cafelegal.service;


import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import com.android.volley.Request;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import udacitynano.com.br.cafelegal.R;
import udacitynano.com.br.cafelegal.data.DatabaseContract;
import udacitynano.com.br.cafelegal.model.Convite;
import udacitynano.com.br.cafelegal.network.NetworkRequests;
import udacitynano.com.br.cafelegal.util.Constant;

@SuppressWarnings("unused")
public class ConviteService {

    private final Context mContext;
    private View mView = null;

    public ConviteService(Context context, View view) {

        mContext = context;
        mView = view;

    }

    public void sendConvite(long userId, String areaLocation) throws JSONException {
        DateFormat df = new SimpleDateFormat(mContext.getString(R.string.date_format));
        Date today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);
        final Convite convite = new Convite(-1,userId, 0,reportDate, "N", "", "", areaLocation,"","","");
        final JSONObject jsonConvite = new JSONObject(new Gson().toJson(convite));

        final NetworkRequests networkRequests = new NetworkRequests(mContext,mView);

        networkRequests.stringRequest(Constant.CONVITE,Request.Method.POST,Constant.SERVER_API_CAFE_LEGAL + Constant.CONVITE_CAFE_LEGAL,jsonConvite,true,convite);

    }

    //Convite provider access
    public int updateConvite(Context context, Convite convite) {

        String[] selectionArgs = {""};
        String selectionClause;

        ContentValues conviteValues = new ContentValues();

        conviteValues.put(DatabaseContract.ConviteEntry.COLUMN_ID_CONVITE_SERVER, convite.getId());
        conviteValues.put(DatabaseContract.ConviteEntry.COLUMN_CONVIDA_ID, convite.getConvidaId());
        conviteValues.put(DatabaseContract.ConviteEntry.COLUMN_RESPONDE_ID, convite.getRespondeId());
        conviteValues.put(DatabaseContract.ConviteEntry.COLUMN_DATA_CONVITE, String.valueOf(convite.getDataCriacao()));
        conviteValues.put(DatabaseContract.ConviteEntry.COLUMN_CONVITE_ACEITO, convite.getAceito());
        conviteValues.put(DatabaseContract.ConviteEntry.COLUMN_CHAT_FIREBASE, convite.getChatFirebase());
        conviteValues.put(DatabaseContract.ConviteEntry.COLUMN_ESPECIALIDADE , convite.getEspecialidade());
        conviteValues.put(DatabaseContract.ConviteEntry.COLUMN_AREA_LOCATION , convite.getAreaLocation());
        conviteValues.put(DatabaseContract.ConviteEntry.COLUMN_NOME_CONVIDA, convite.getNomeConvida());
        conviteValues.put(DatabaseContract.ConviteEntry.COLUMN_NOME_ADVOGADO , convite.getNomeAdvogado());
        conviteValues.put(DatabaseContract.ConviteEntry.COLUMN_ADVOGADO_OAB, convite.getAdvogadoOAB());


        selectionArgs[0] = String.valueOf(convite.getId());
        selectionClause = DatabaseContract.ConviteEntry.TABLE_NAME+"."+DatabaseContract.ConviteEntry.COLUMN_ID_CONVITE_SERVER + " = ?";

        return context.getContentResolver().update(
                DatabaseContract.ConviteEntry.CONTENT_URI,
                conviteValues,
                selectionClause,
                selectionArgs
        );

    }

    public void createConvite(Context context, Convite convite) {



        ContentValues conviteValues = new ContentValues();

        conviteValues.put(DatabaseContract.ConviteEntry.COLUMN_ID_CONVITE_SERVER, convite.getId());
        conviteValues.put(DatabaseContract.ConviteEntry.COLUMN_CONVIDA_ID, convite.getConvidaId());
        conviteValues.put(DatabaseContract.ConviteEntry.COLUMN_RESPONDE_ID, convite.getRespondeId());
        conviteValues.put(DatabaseContract.ConviteEntry.COLUMN_DATA_CONVITE, String.valueOf(convite.getDataCriacao()));
        conviteValues.put(DatabaseContract.ConviteEntry.COLUMN_CONVITE_ACEITO, convite.getAceito());
        conviteValues.put(DatabaseContract.ConviteEntry.COLUMN_CHAT_FIREBASE, convite.getChatFirebase());
        conviteValues.put(DatabaseContract.ConviteEntry.COLUMN_ESPECIALIDADE , convite.getEspecialidade());
        conviteValues.put(DatabaseContract.ConviteEntry.COLUMN_AREA_LOCATION , convite.getAreaLocation());
        conviteValues.put(DatabaseContract.ConviteEntry.COLUMN_NOME_CONVIDA, convite.getNomeConvida());
        conviteValues.put(DatabaseContract.ConviteEntry.COLUMN_NOME_ADVOGADO , convite.getNomeAdvogado());
        conviteValues.put(DatabaseContract.ConviteEntry.COLUMN_ADVOGADO_OAB, convite.getAdvogadoOAB());


        Uri insertedUri = context.getContentResolver().insert(
                DatabaseContract.ConviteEntry.CONTENT_URI,
                conviteValues
        );

        ContentUris.parseId(insertedUri);

    }

}
