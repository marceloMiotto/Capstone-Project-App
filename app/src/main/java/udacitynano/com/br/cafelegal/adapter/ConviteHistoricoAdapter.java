package udacitynano.com.br.cafelegal.adapter;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import udacitynano.com.br.cafelegal.ChatActivity;
import udacitynano.com.br.cafelegal.ChatFragment;
import udacitynano.com.br.cafelegal.PerfilFragment;
import udacitynano.com.br.cafelegal.R;
import udacitynano.com.br.cafelegal.model.Convite;
import udacitynano.com.br.cafelegal.singleton.UserType;

public class ConviteHistoricoAdapter extends RecyclerView.Adapter<ConviteHistoricoAdapter.ViewHolder>{

    private static List<Convite> mConviteList;
    private Context mContext;
    private static boolean mIsTablet;

    public ConviteHistoricoAdapter(Context context, List<Convite> conviteList, boolean isTablet) {
        mConviteList = conviteList;
        mContext = context;
        mIsTablet = isTablet;
    }


    @Override
    public ConviteHistoricoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_convite_historico, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ConviteHistoricoAdapter.ViewHolder holder, int position) {
        holder.mConviteTitle.setText(mConviteList.get(position).getDataCriacao());
        if(UserType.isAdvogado(mContext)){
            holder.mConviteSolicita.setText(mConviteList.get(position).getNomeConvida());
        }else{
            holder.mConviteSolicita.setText(mConviteList.get(position).getNomeAdvogado()+ " - OAB: " + mConviteList.get(position).getAdvogadoOAB());
        }
        Log.e("Debug","onBindViewHolder "+mConviteList.get(position).getId());

    }

    @Override
    public int getItemCount() {
        if(mConviteList != null){
            return mConviteList.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mConviteTitle;
        public TextView mConviteSolicita;

        public ViewHolder(View v) {
            super(v);
            mConviteTitle = (TextView) v.findViewById(R.id.item_convite_historico_data);
            mConviteSolicita = (TextView) v.findViewById(R.id.item_convite_historico_nome);


            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mIsTablet) {
                        Log.e("Debug","Entrou para pegar argumentos");
                        Activity activity = (Activity) v.getContext();
                        FragmentManager fm =  activity.getFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_menu_second_panel,ChatFragment.newInstance(
                                String.valueOf(mConviteList.get(getAdapterPosition()).getId()),
                                mConviteList.get(getAdapterPosition()).getNomeAdvogado(),
                                mConviteList.get(getAdapterPosition()).getAdvogadoOAB(),
                                mConviteList.get(getAdapterPosition()).getNomeConvida()
                        ));
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();


                    }else {
                        Intent intent = new Intent(v.getContext(), ChatActivity.class);
                        intent.putExtra(v.getContext().getString(R.string.adapter_extra_convite), "Convite " + mConviteList.get(getAdapterPosition()).getId());
                        intent.putExtra(v.getContext().getString(R.string.adapter_extra_nome_convida), mConviteList.get(getAdapterPosition()).getNomeConvida());
                        intent.putExtra(v.getContext().getString(R.string.adapter_extra_nome_advoagdo), mConviteList.get(getAdapterPosition()).getNomeAdvogado());
                        intent.putExtra(v.getContext().getString(R.string.adapter_extra_advogado_oab), mConviteList.get(getAdapterPosition()).getAdvogadoOAB());
                        v.getContext().startActivity(intent);
                    }
                }
            });
        }
    }
}
