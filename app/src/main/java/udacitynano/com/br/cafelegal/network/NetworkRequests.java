package udacitynano.com.br.cafelegal.network;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import org.json.JSONException;
import org.json.JSONObject;

import udacitynano.com.br.cafelegal.R;
import udacitynano.com.br.cafelegal.model.Advogado;
import udacitynano.com.br.cafelegal.model.Cliente;
import udacitynano.com.br.cafelegal.model.Convite;
import udacitynano.com.br.cafelegal.model.Pessoa;
import udacitynano.com.br.cafelegal.service.ConviteService;
import udacitynano.com.br.cafelegal.service.PerfilService;
import udacitynano.com.br.cafelegal.singleton.NetworkSingleton;
import udacitynano.com.br.cafelegal.singleton.UserType;
import udacitynano.com.br.cafelegal.util.Constant;


public class NetworkRequests {

    private Context mContext;
    private View mView;
    private boolean mShowSnack;
    private int mTypeCalled;

    public NetworkRequests(){

    }

    public NetworkRequests(Context context){
        mContext = context;
    }

    public NetworkRequests(Context context,View view){
        mContext = context;
        mView = view;
    }

    public void stringRequest(int typeCalled, int requestMethod, String restApiURL, JSONObject jsonBody, boolean showSnack, final Object object){

        final JSONObject jsonObjectBody = jsonBody;
        mShowSnack = showSnack;
        mTypeCalled = typeCalled;

        StringRequest stringRequest = new StringRequest(requestMethod, restApiURL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Debug", "Convite id " + response.toString());
                if (mShowSnack) {
                    Snackbar.make(mView, "Salvo", Snackbar.LENGTH_SHORT).show();
                }

                switch (mTypeCalled){

                    case Constant.PERFIL:
                        break;
                    case Constant.CONVITE:
                        Convite convite = (Convite) object;
                        ConviteService conviteService = new ConviteService(mContext,mView);
                        convite.setId(Long.valueOf(response.toString()));

                        conviteService.createConvite(mContext,convite);

                        break;
                    case Constant.LOGIN:
                        Log.e("Debug","Token save "+response.toString());
                        break;
                    case Constant.ANDROID_SERVICE:
                        Log.e("Debug","Token save "+response.toString());
                        break;

                    case Constant.CONVITE_ACEITO:
                        Log.e("Debug","Convite Aceito "+response.toString());
                        break;
                    }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("Debug", "Network error: " + error.getMessage() + String.valueOf(error.networkResponse.statusCode));
                if(mShowSnack){
                    Snackbar.make(mView,"Erro ao enviar",Snackbar.LENGTH_SHORT).show();
                }

            }

        }

        ) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                    return jsonObjectBody.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        // Access the RequestQueue through your singleton class.
        NetworkSingleton.getInstance(mContext).addStringRequestQueue(stringRequest);

    }


    public void jsonRequest(int typeCalled, int requestMethod, String restApiURL, JSONObject jsonBody, boolean showSnack) {


        mShowSnack = showSnack;
        mTypeCalled = typeCalled;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (requestMethod, restApiURL, jsonBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Debug", "Pessoa return " + response.toString());
                        if (mShowSnack) {
                            Snackbar.make(mView, "Salvo", Snackbar.LENGTH_SHORT).show();
                        }

                        switch (mTypeCalled){

                            case Constant.PERFIL:
                                PerfilService perfilService = new PerfilService(mContext, mView);
                                UserType userType = UserType.getInstance(mContext);
                                Log.e("Debug","Login activity user type "+ userType.getAppUserType());
                                Pessoa pessoa;
                                if(userType.getAppUserType().equals(mContext.getString(R.string.preference_user_type_advogado))) {
                                    pessoa = new Gson().fromJson(response.toString(),Advogado.class);
                                }else{
                                    pessoa = new Gson().fromJson(response.toString(),Cliente.class);
                                }

                                if (UserType.getInstance(mContext).getUserId() <= 0) {
                                    perfilService.setSharedId(pessoa.getId());
                                }

                                 perfilService.updateCreateUserOnSQLite(pessoa);

                                SharedPreferences sharedPref = mContext.getSharedPreferences(
                                        mContext.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();

                                try {
                                    if(response.getString("type").equals("cliente")){
                                        editor.putString(mContext.getString(R.string.preference_user_type_key), mContext.getString(R.string.preference_user_type_cliente));
                                    }else{
                                        editor.putString(mContext.getString(R.string.preference_user_type_key), mContext.getString(R.string.preference_user_type_advogado));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                editor.commit();

                                Log.e("Debug","networkRequests activity user type "+ userType.getAppUserType());
                                break;

                            case Constant.LOGIN:
                                Log.e("Debug","Token save "+response.toString());
                                break;
                            case Constant.ANDROID_SERVICE:
                                Log.e("Debug","Token save "+response.toString());
                                break;

                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Debug", "Network error: " + error.getMessage() + String.valueOf(error.networkResponse.statusCode));
                        if(mShowSnack){
                            Snackbar.make(mView,"Erro ao enviar",Snackbar.LENGTH_SHORT).show();
                        }

                    }
                });


        // Access the RequestQueue through your singleton class.
        NetworkSingleton.getInstance(mContext).addJSONToRequestQueue(jsObjRequest);


    }
}
