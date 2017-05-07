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

public class ConviteAdapter  extends RecyclerView.Adapter<ConviteAdapter.ViewHolder>{

    List<Convite> mConviteList;
    static Context mContext;
    static  String mPosition;


    public ConviteAdapter(Context context, List<Convite> conviteList) {
        mConviteList = conviteList;
        mContext = context;
    }


    @Override
    public ConviteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_convite_historico, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ConviteAdapter.ViewHolder holder, int position) {
        holder.mConviteTitle.setText(mConviteList.get(position).getChatFirebase() +" - "+ String.valueOf(mConviteList.get(position).getId()));

        mPosition = String.valueOf(position);
        Log.e("Debug2","position "+position);
    }

    @Override
    public int getItemCount() {
        return mConviteList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mConviteTitle;
        public TextView mConviteDate;

        public ViewHolder(View v) {
            super(v);
            mConviteTitle = (TextView) v.findViewById(R.id.item_convite_historico_title);


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
