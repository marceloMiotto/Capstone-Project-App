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




}
