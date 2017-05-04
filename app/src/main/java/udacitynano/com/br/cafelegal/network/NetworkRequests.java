package udacitynano.com.br.cafelegal.network;


import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

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
    private int mRequestMethod;

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
        mRequestMethod = requestMethod;

        StringRequest stringRequest = new StringRequest(requestMethod, restApiURL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Debug", "Convite id " + response.toString());
                if (mShowSnack) {
                }
                Snackbar.make(mView, "Salvo", Snackbar.LENGTH_SHORT).show();

                switch (mTypeCalled){

                    case Constant.PERFIL:
                        PerfilService perfilService = new PerfilService(mContext, mView);
                        if (UserType.getInstance(mContext).getUserId() <= 0) {
                            perfilService.setSharedId(Long.valueOf(response.toString()));
                        }

                        if (mRequestMethod == Request.Method.POST) {
                            perfilService.createUserOnSQLite((Pessoa) object);
                        } else {
                            perfilService.updateUserOnSQLite((Pessoa) object);
                        }

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



}
