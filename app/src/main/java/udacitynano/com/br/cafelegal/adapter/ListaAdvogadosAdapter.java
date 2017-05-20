package udacitynano.com.br.cafelegal.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import udacitynano.com.br.cafelegal.AdvogadoDetailsActivity;
import udacitynano.com.br.cafelegal.R;
import udacitynano.com.br.cafelegal.model.Advogado;
import udacitynano.com.br.cafelegal.util.Constant;


public class ListaAdvogadosAdapter extends RecyclerView.Adapter<ListaAdvogadosAdapter.ViewHolder> {
    private static List<Advogado> mAdvogadoList;

    public ListaAdvogadosAdapter(List<Advogado> advogadoList) {
        mAdvogadoList = advogadoList;
    }


    @Override
    public ListaAdvogadosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_advogados, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ListaAdvogadosAdapter.ViewHolder holder, int position) {
        holder.mAdvogadoNome.setText(mAdvogadoList.get(position).getNome() + " - " + mAdvogadoList.get(position).getSobrenome());

    }

    @Override
    public int getItemCount() {
        return mAdvogadoList.size();
    }

    @SuppressWarnings("unused")
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mAdvogadoNome;
        public TextView mAdvogadoEspecialidade;
        public TextView mAdvogadoEndereco;


        public ViewHolder(View v) {
            super(v);
            mAdvogadoNome = (TextView) v.findViewById(R.id.lista_advogados_nome);
            mAdvogadoEspecialidade = (TextView) v.findViewById(R.id.lista_advogados_especialidade);
            mAdvogadoEndereco = (TextView) v.findViewById(R.id.lista_advogados_endereco);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    Intent intent = new Intent(v.getContext(), AdvogadoDetailsActivity.class);
                    intent.putExtra(Constant.ADVOGADO_ESCOLHIDO, mAdvogadoList.get(position));
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}