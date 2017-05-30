package udacitynano.com.br.cafelegal.adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import udacitynano.com.br.cafelegal.ChatActivity;
import udacitynano.com.br.cafelegal.ChatFragment;
import udacitynano.com.br.cafelegal.R;
import udacitynano.com.br.cafelegal.model.Convite;
import udacitynano.com.br.cafelegal.service.ConviteService;
import udacitynano.com.br.cafelegal.singleton.NetworkSingleton;
import udacitynano.com.br.cafelegal.singleton.UserType;
import udacitynano.com.br.cafelegal.util.Constant;


public class ConvitesAbertosAdapter extends RecyclerView.Adapter<ConvitesAbertosAdapter.ViewHolder> {

    private static List<Convite> mConviteList;
    private static boolean mIsTablet;

    public ConvitesAbertosAdapter(List<Convite> conviteList, boolean isTablet) {
        mConviteList = conviteList;
        mIsTablet = isTablet;
    }


    @Override
    public ConvitesAbertosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_convites_abertos, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ConvitesAbertosAdapter.ViewHolder holder, int position) {
        holder.mConviteTitle.setText(mConviteList.get(position).getDataCriacao());
        Log.e("Debug", "onBindViewHolder " + mConviteList.get(position).getId());
    }

    @Override
    public int getItemCount() {
        if (mConviteList != null) {
            return mConviteList.size();
        }
        return 0;
    }

    @SuppressWarnings("AccessStaticViaInstance")
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mConviteTitle;
        public Button mConviteAceito;

        public ViewHolder(View v) {
            super(v);
            mConviteTitle = (TextView) v.findViewById(R.id.convite_enviado);
            mConviteAceito = (Button) v.findViewById(R.id.convite_aberto_aceito);

            mConviteAceito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    long advogadoId = UserType.getUserId(v.getContext());
                    String apiURL = Constant.SERVER_API_CAFE_LEGAL + Constant.CONVITE_CAFE_LEGAL + "/" + mConviteList.get(getAdapterPosition()).getId() + "/" + advogadoId + Constant.ACEITO;

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.PUT, apiURL, null, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {

                                    Log.e("Debug", "Entrou response adapter");

                                    Convite convite = new Gson().fromJson(response.toString(), Convite.class);
                                    ConviteService conviteService = new ConviteService(v.getContext(), null);
                                    conviteService.createConvite(v.getContext(), convite);

                                    if (mIsTablet) {
                                        Activity activity = (Activity) v.getContext();
                                        FragmentManager fm = activity.getFragmentManager();
                                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                        fragmentTransaction.replace(R.id.fragment_menu_second_panel, ChatFragment.newInstance(
                                                String.valueOf(mConviteList.get(getAdapterPosition()).getId()),
                                                mConviteList.get(getAdapterPosition()).getNomeConvida(),
                                                mConviteList.get(getAdapterPosition()).getNomeAdvogado(),
                                                mConviteList.get(getAdapterPosition()).getAdvogadoOAB()
                                        ));
                                        fragmentTransaction.addToBackStack(null);
                                        fragmentTransaction.commit();


                                    } else {

                                        Intent intent = new Intent(v.getContext(), ChatActivity.class);
                                        intent.putExtra(v.getContext().getString(R.string.adapter_extra_convite), "Convite " + convite.getId());
                                        intent.putExtra(v.getContext().getString(R.string.adapter_extra_nome_convida), convite.getNomeConvida());
                                        intent.putExtra(v.getContext().getString(R.string.adapter_extra_nome_advoagdo), convite.getNomeAdvogado());
                                        intent.putExtra(v.getContext().getString(R.string.adapter_extra_advogado_oab), convite.getAdvogadoOAB());
                                        v.getContext().startActivity(intent);
                                    }
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(v.getContext(), v.getContext().getString(R.string.convite_aviso_aceito), Toast.LENGTH_SHORT).show();

                                }
                            }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<>();
                            headers.put("Content-Type", "application/json; charset=utf-8");
                            return headers;
                        }

                    };
                    // Access the RequestQueue through your singleton class.
                    NetworkSingleton.getInstance(v.getContext()).addJSONToRequestQueue(jsonObjectRequest);

                }
            });

        }
    }
}
