package udacitynano.com.br.cafelegal.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import udacitynano.com.br.cafelegal.ChatActivity;
import udacitynano.com.br.cafelegal.R;
import udacitynano.com.br.cafelegal.model.Convite;



public class ConvitesAbertosAdapter extends RecyclerView.Adapter<ConvitesAbertosAdapter.ViewHolder>{

    List<Convite> mConviteList;
    static Context mContext;
    static  String mPosition;


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
        holder.mConviteTitle.setText(mConviteList.get(position).getChatFirebase() +" - "+ String.valueOf(mConviteList.get(position).getId()));

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
                    int position = getAdapterPosition();
                    Log.e("Debug1","click() "+position);
                    Intent intent = new Intent(mContext,ChatActivity.class);
                    intent.putExtra("convite","Convite "+position);
                    mContext.startActivity(intent);

                    //TODO update convite aceitando

                    //open chat com o convite id

                }
            });

        }

    }

}
