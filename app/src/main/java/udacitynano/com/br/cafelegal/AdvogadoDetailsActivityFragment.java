package udacitynano.com.br.cafelegal;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import udacitynano.com.br.cafelegal.model.Advogado;
import udacitynano.com.br.cafelegal.util.Constant;


@SuppressWarnings("unused")
public class AdvogadoDetailsActivityFragment extends Fragment {

    @BindView(R.id.detailsAdvogadoNome)

    TextView detailsAdvogadoNome;
    @BindView(R.id.detailsAdvogadoNumeroOAB)

    TextView detailsAdvogadoNumeroOAB;
    @BindView(R.id.detailsAdvogadoEspecialidadeUm)

    TextView detailsAdvogadoEspecialidadeUm;
    @BindView(R.id.detailsAdvogadoEspecialidadeDois)

    TextView detailsAdvogadoEspecialidadeDois;


    public AdvogadoDetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_advogado_details, container, false);
        ButterKnife.bind(this,view);
        Bundle data = getActivity().getIntent().getExtras();
        Advogado advogado = data.getParcelable(Constant.ADVOGADO_ESCOLHIDO);


        detailsAdvogadoNome.setText(advogado.getNome() + " " + advogado.getSobrenome());
        detailsAdvogadoNumeroOAB.setText(advogado.getNumeroInscricaoOAB());
        detailsAdvogadoEspecialidadeUm.setText(advogado.getEspecialistaUm());
        detailsAdvogadoEspecialidadeDois.setText(advogado.getEspecialistaDois());

        return view;
    }
}
