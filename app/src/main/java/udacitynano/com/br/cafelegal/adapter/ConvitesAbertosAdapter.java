package udacitynano.com.br.cafelegal.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import udacitynano.com.br.cafelegal.ChatActivity;
import udacitynano.com.br.cafelegal.R;
import udacitynano.com.br.cafelegal.model.Convite;
import udacitynano.com.br.cafelegal.network.NetworkRequests;
import udacitynano.com.br.cafelegal.service.ConviteService;
import udacitynano.com.br.cafelegal.singleton.NetworkSingleton;
import udacitynano.com.br.cafelegal.singleton.UserType;
import udacitynano.com.br.cafelegal.util.Constant;


public class ConvitesAbertosAdapter extends RecyclerView.Adapter<ConvitesAbertosAdapter.ViewHolder>{

    private static  List<Convite> mConviteList;
    private static Context mContext;
    private static  String mPosition;
    private int convitePosition;


    public ConvitesAbertosAdapter(Context context, List<Convite> conviteList) {
        mConviteList = conviteList;
        mContext = context;
    }


    @Override
    public ConvitesAbertosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_convites_abertos, parent, false);

        ConvitesAbertosAdapter.ViewHolder vh = new ConvitesAbertosAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ConvitesAbertosAdapter.ViewHolder holder, int position) {
        holder.mConviteTitle.setText(mConviteList.get(position).getDataCriacao());

        mPosition = String.valueOf(position);
        Log.e("Debug2","position "+position);

    }

    @Override
    public int getItemCount() {
        if(mConviteList != null){
            return mConviteList.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mConviteTitle;
        public Button mConviteAceito;

        public ViewHolder(View v) {
            super(v);
            mConviteTitle = (TextView) v.findViewById(R.id.convite_enviado);
            mConviteAceito = (Button) v.findViewById(R.id.convite_aberto_aceito);

            mConviteAceito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"Test",Toast.LENGTH_SHORT).show();
                    Log.e("Debug2","view ");
                    Log.e("Debug1","click() convite id "+ mConviteList.get(getAdapterPosition()).getId() );
                    //TODO update convite aceitando
                    long advogadoId = UserType.getUserId();
                    String apiURL = Constant.SERVER_API_CAFE_LEGAL+Constant.CONVITE_CAFE_LEGAL+"/"+mConviteList.get(getAdapterPosition()).getId() +"/"+advogadoId+Constant.ACEITO;
                    Log.e("Debug2"," aceito url: "+apiURL);
                    StringRequest stringRequest = new StringRequest(Request.Method.PUT, apiURL, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            Log.e("Debug", "convite aceito " + response.toString());
                            //cria o convite

                            ConviteService conviteService = new ConviteService(mContext,null);
                            conviteService.createConvite(mContext,mConviteList.get(getAdapterPosition()));
                            Log.e("Debug11","click2() convite id "+ mConviteList.get(getAdapterPosition()).getId() );

                        }

                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            Log.e("Debug", "Network error: " + error.getMessage() + String.valueOf(error.networkResponse.statusCode));
                            Toast.makeText(mContext,"Este convite foi aceito por outro Advogado(a). Faça um refresh para acessar os convites em aberto.",Toast.LENGTH_SHORT).show();
                        }

                    }

                    ) {

                        /**
                         * Passing some request headers
                         */
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json; charset=utf-8");
                            return headers;
                        }

                    };

                    // Access the RequestQueue through your singleton class.
                    NetworkSingleton.getInstance(mContext).addStringRequestQueue(stringRequest);

                    //open chat com o convite id
                    Intent intent = new Intent(mContext,ChatActivity.class);
                    intent.putExtra("convite","Convite "+ mConviteList.get(getAdapterPosition()).getId());
                    intent.putExtra("nome_convida",mConviteList.get(getAdapterPosition()).getNomeConvida());
                    intent.putExtra("nome_advogado",mConviteList.get(getAdapterPosition()).getNomeAdvogado());
                    intent.putExtra("advogado_oab",mConviteList.get(getAdapterPosition()).getAdvogadoOAB());
                    mContext.startActivity(intent);
                }
            });

        }

    }

}
