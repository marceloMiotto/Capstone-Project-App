package udacitynano.com.br.cafelegal.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import udacitynano.com.br.cafelegal.AdvogadoDetailsActivity;
import udacitynano.com.br.cafelegal.ChatActivity;
import udacitynano.com.br.cafelegal.R;
import udacitynano.com.br.cafelegal.model.Advogado;
import udacitynano.com.br.cafelegal.model.Convite;
import udacitynano.com.br.cafelegal.service.ConviteService;


public class ListaAdvogadosAdapter extends RecyclerView.Adapter<ListaAdvogadosAdapter.ViewHolder>{
    private static List<Advogado> mAdvogadoList;
    private static Context mContext;
    private static  String mPosition;


public ListaAdvogadosAdapter(Context context, List<Advogado> advogadoList) {
        mAdvogadoList = advogadoList;
        mContext = context;
        }


@Override
public ListaAdvogadosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_lista_advogados, parent, false);

        ViewHolder vh = new ViewHolder(v);


        return vh;
        }

@Override
public void onBindViewHolder(ListaAdvogadosAdapter.ViewHolder holder, int position) {
        holder.mAdvogadoNome.setText(mAdvogadoList.get(position).getNome() +" - "+  mAdvogadoList.get(position).getSobrenome());

        mPosition = String.valueOf(position);
        Log.e("Debug2","position "+position);


        }

@Override
public int getItemCount() {
        return mAdvogadoList.size();
        }

public static class ViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    public TextView mAdvogadoNome;
    public TextView mAdvogadoEspecialidade;
    public TextView mAdvogadoEndereco;


    public ViewHolder(View v) {
        super(v);
        mAdvogadoNome = (TextView) v.findViewById(R.id.lista_advogados_nome);
        mAdvogadoEspecialidade =(TextView) v.findViewById(R.id.lista_advogados_especialidade);
        mAdvogadoEndereco =(TextView) v.findViewById(R.id.lista_advogados_endereco);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"Test",Toast.LENGTH_SHORT).show();
                int position = getAdapterPosition();
                Log.e("Debug1","click() "+position);
                Intent intent = new Intent(mContext,AdvogadoDetailsActivity.class);
                intent.putExtra("ADVOGADO_SELECIOANDO",mAdvogadoList.get(position) );
                Log.e("Debug1","ADVOGADO_SELECIOANDO id "+ mAdvogadoList.get(position).getId());
                mContext.startActivity(intent);
            }
        });



    }
}

}