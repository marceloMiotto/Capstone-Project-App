package udacitynano.com.br.cafelegal.data;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;

import java.util.ArrayList;
import java.util.List;

import udacitynano.com.br.cafelegal.R;
import udacitynano.com.br.cafelegal.model.Advogado;
import udacitynano.com.br.cafelegal.model.Convite;
import udacitynano.com.br.cafelegal.network.NetworkRequests;
import udacitynano.com.br.cafelegal.util.Constant;

public class DataFromServer {

    private Context mContext;

    private List<Convite> convites = new ArrayList<>();

    public DataFromServer(Context context){
        this.mContext = context;

    }

    public void getConvitesAbertos(){

        SharedPreferences sharedPref = mContext.getSharedPreferences(
                mContext.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor;
        long advogadoId = sharedPref.getLong(mContext.getString(R.string.preference_user_firebase_email),-1);
        String apiURL = Constant.SERVER_API_CAFE_LEGAL+Constant.CONVITE+"/"+advogadoId+Constant.ABERTOS;
        Log.e("Debug","server api link "+ apiURL);

        final NetworkRequests networkRequests = new NetworkRequests(mContext);

        networkRequests.jsonRequest(Constant.CONVITES_ABERTOS, Request.Method.GET,apiURL,null,true);

    }


}
