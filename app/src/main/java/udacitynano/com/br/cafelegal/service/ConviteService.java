package udacitynano.com.br.cafelegal.service;


import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import udacitynano.com.br.cafelegal.data.DatabaseContract;
import udacitynano.com.br.cafelegal.model.Advogado;
import udacitynano.com.br.cafelegal.model.Convite;
import udacitynano.com.br.cafelegal.model.Pessoa;
import udacitynano.com.br.cafelegal.singleton.NetworkSingleton;
import udacitynano.com.br.cafelegal.singleton.UserType;
import udacitynano.com.br.cafelegal.util.Constant;

public class ConviteService {

    final Context mContext;

    public ConviteService(Context context) {

        mContext = context;
    }

    public int sendConvite(long userId) throws JSONException {

        //TODO get area location
        Convite convite = new Convite(userId, 0, "", "", "", "");
        Log.e("Debug", "server api link " + Constant.SERVER_API_CAFE_LEGAL + Constant.CONVITE_CAFE_LEGAL);
        JSONObject jsonConvite = new JSONObject(new Gson().toJson(convite));

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Constant.SERVER_API_CAFE_LEGAL + Constant.CONVITE_CAFE_LEGAL, jsonConvite , new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("Debug", "Pessoa id " + response.toString());
                //Snackbar.make(mContext, "Perfil Salvo", Snackbar.LENGTH_SHORT).show();
            }


        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("Debug", "Pessoa error: " + error.getMessage() + String.valueOf(error.networkResponse.statusCode));
                //Snackbar.make(mContext, "Erro ao enviar para o servidor. " + error.getMessage(), Snackbar.LENGTH_SHORT).show();

            }

        }) {


            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        // Access the RequestQueue through your singleton class.
        NetworkSingleton.getInstance(mContext).addJSONToRequestQueue(jsonRequest);


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


        Uri insertedUri = context.getContentResolver().insert(
                DatabaseContract.ConviteEntry.CONTENT_URI,
                conviteValues
        );


        conviteId = ContentUris.parseId(insertedUri);

        Log.e("Debug","Insert service pessoaId "+conviteId);

        return conviteId;
    }

}
