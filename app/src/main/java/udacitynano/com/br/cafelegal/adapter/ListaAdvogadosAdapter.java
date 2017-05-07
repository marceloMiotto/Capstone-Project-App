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

import java.util.List;

import udacitynano.com.br.cafelegal.ChatActivity;
import udacitynano.com.br.cafelegal.R;
import udacitynano.com.br.cafelegal.model.Advogado;



public class ListaAdvogadosAdapter extends RecyclerView.Adapter<ListaAdvogadosAdapter.ViewHolder>{

        List<Advogado> mAdvogadoList;
static Context mContext;
static  String mPosition;


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
                Intent intent = new Intent(mContext,ChatActivity.class);
                intent.putExtra("convite","Convite "+position);
                mContext.startActivity(intent);
            }
        });

    }

}

}