package udacitynano.com.br.cafelegal.service;


import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import udacitynano.com.br.cafelegal.data.Database;
import udacitynano.com.br.cafelegal.data.DatabaseContract;
import udacitynano.com.br.cafelegal.model.Convite;
import udacitynano.com.br.cafelegal.network.NetworkRequests;
import udacitynano.com.br.cafelegal.util.Constant;

public class ConviteService {

    final Context mContext;
    View mView = null;

    public ConviteService(Context context, View view) {

        mContext = context;
        mView = view;

    }

    public int sendConvite(long userId, String areaLocation) throws JSONException {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);
        final Convite convite = new Convite(-1,userId, 0,reportDate, "N", "", "", areaLocation,"","","");
        Log.e("Debug", "server api link " + Constant.SERVER_API_CAFE_LEGAL + Constant.CONVITE_CAFE_LEGAL);
        final JSONObject jsonConvite = new JSONObject(new Gson().toJson(convite));
        Log.e("Debug","jsonConvite "+jsonConvite.toString());

        final NetworkRequests networkRequests = new NetworkRequests(mContext,mView);

        networkRequests.stringRequest(Constant.CONVITE,Request.Method.POST,Constant.SERVER_API_CAFE_LEGAL + Constant.CONVITE_CAFE_LEGAL,jsonConvite,true,convite);

        return 0;

    }

    //Convite provider access
    public int updateConvite(Context context, Convite convite) {

        String[] selectionArgs = {""};
        String selectionClause = "";

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

        Log.e("Debug","Perfil Service pessoaValues "+conviteValues);
        Log.e("Debug","Perfil Service  selectionClause "+selectionClause);
        Log.e("Debug","Perfil Service  selectionArgs "+selectionArgs[0]);

        int updateUri = context.getContentResolver().update(
                DatabaseContract.ConviteEntry.CONTENT_URI,
                conviteValues,
                selectionClause,
                selectionArgs
        );

        return updateUri;

    }

    public long createConvite(Context context, Convite convite) {


        long conviteId = 0;
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


        conviteId = ContentUris.parseId(insertedUri);

        Log.e("Debug","Insert service conviteId "+conviteId);

        return conviteId;
    }

}
