package udacitynano.com.br.cafelegal.adapter;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import udacitynano.com.br.cafelegal.AdvogadoDetailsActivity;
import udacitynano.com.br.cafelegal.AdvogadoDetailsActivityFragment;

import udacitynano.com.br.cafelegal.R;
import udacitynano.com.br.cafelegal.model.Advogado;
import udacitynano.com.br.cafelegal.util.Constant;
import udacitynano.com.br.cafelegal.util.UserHelper;




public class ListaAdvogadosAdapter extends RecyclerView.Adapter<ListaAdvogadosAdapter.ViewHolder> {

    private static boolean mIsTablet;
    private static List<Advogado> mAdvogadoList;

    public ListaAdvogadosAdapter(List<Advogado> advogadoList, boolean isTablet) {
        mAdvogadoList = advogadoList;
        mIsTablet = isTablet;
    }


    @Override
    public ListaAdvogadosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_advogados, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ListaAdvogadosAdapter.ViewHolder holder, int position) {
        holder.mAdvogadoNome.setText(mAdvogadoList.get(position).getNome() + " " + mAdvogadoList.get(position).getSobrenome());
        holder.mAdvogadoEndereco.setText(mAdvogadoList.get(position).getEndereco()+ " "+
                "nº "+mAdvogadoList.get(position).getNumero()
        );

        UserHelper userHelper = new UserHelper();
        int iconRandom = userHelper.getRandom();
        mAdvogadoList.get(position).setIconLista(iconRandom);
        holder.mListaAdvogadoIcone.setImageDrawable(ContextCompat.getDrawable(holder.context,userHelper.getUserIcon(mAdvogadoList.get(position).getIconLista())));
        holder.mAdvogadoOABEspecialidade.setText("OAB: "+mAdvogadoList.get(position).getNumeroInscricaoOAB()+" - "+mAdvogadoList.get(position).getEspecialistaUm());
    }

    @Override
    public int getItemCount() {
        return mAdvogadoList.size();
    }

    @SuppressWarnings("unused")
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView mAdvogadoNome;
        public final TextView mAdvogadoOABEspecialidade;
        public final TextView mAdvogadoEndereco;
        public final ImageView mListaAdvogadoIcone;
        final Context context;


        public ViewHolder(View v) {
            super(v);
            mAdvogadoNome = (TextView) v.findViewById(R.id.lista_advogados_nome);
            mAdvogadoOABEspecialidade = (TextView) v.findViewById(R.id.lista_advogados_oab_especialidade);
            mAdvogadoEndereco = (TextView) v.findViewById(R.id.lista_advogados_endereco);
            mListaAdvogadoIcone = (ImageView) v.findViewById(R.id.lista_advogados_perfil_icone);
            context = v.getContext();


            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (mIsTablet) {
                        Activity activity = (Activity) v.getContext();
                        FragmentManager fm =  activity.getFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_menu_second_panel, AdvogadoDetailsActivityFragment.newInstance(
                                mAdvogadoList.get(position)
                        ));
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                    }else {
                        Intent intent = new Intent(v.getContext(), AdvogadoDetailsActivity.class);
                        intent.putExtra(Constant.ADVOGADO_ESCOLHIDO, mAdvogadoList.get(position));
                        v.getContext().startActivity(intent);
                    }
                }
            });
        }
    }
}