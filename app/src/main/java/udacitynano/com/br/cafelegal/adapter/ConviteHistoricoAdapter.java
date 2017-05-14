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
import udacitynano.com.br.cafelegal.model.Convite;
import udacitynano.com.br.cafelegal.singleton.UserType;

public class ConviteHistoricoAdapter extends RecyclerView.Adapter<ConviteHistoricoAdapter.ViewHolder>{

    private static List<Convite> mConviteList;
    private static Context mContext;
    private static  String mPosition;


    public ConviteHistoricoAdapter(Context context, List<Convite> conviteList) {
        mConviteList = conviteList;
        mContext = context;
    }


    @Override
    public ConviteHistoricoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_convite_historico, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ConviteHistoricoAdapter.ViewHolder holder, int position) {
        holder.mConviteTitle.setText(mConviteList.get(position).getDataCriacao());
        if(UserType.isAdvogado()){
            holder.mConviteSolicita.setText(mConviteList.get(position).getNomeConvida());
        }else{
            holder.mConviteSolicita.setText(mConviteList.get(position).getNomeAdvogado()+ " - OAB: " + mConviteList.get(position).getAdvogadoOAB());
        }

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
        public TextView mConviteSolicita;

        public ViewHolder(View v) {
            super(v);
            mConviteTitle = (TextView) v.findViewById(R.id.item_convite_historico_data);
            mConviteSolicita = (TextView) v.findViewById(R.id.item_convite_historico_nome);


            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"Test",Toast.LENGTH_SHORT).show();
                    int position = getAdapterPosition();
                    Log.e("Debug1","click() "+position);
                    Intent intent = new Intent(mContext,ChatActivity.class);

                    Log.e("Debug54","Convite Id: "+mConviteList.get(getAdapterPosition()).getId());
                    Log.e("Debug54","Nome Convida: "+mConviteList.get(getAdapterPosition()).getNomeConvida());
                    Log.e("Debug54","Nome Advogado: "+mConviteList.get(getAdapterPosition()).getNomeAdvogado());
                    Log.e("Debug54","Advogado OAB: "+mConviteList.get(getAdapterPosition()).getAdvogadoOAB());

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
