package udacitynano.com.br.cafelegal;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import udacitynano.com.br.cafelegal.model.Advogado;
import udacitynano.com.br.cafelegal.util.Constant;
import udacitynano.com.br.cafelegal.util.UserHelper;


@SuppressWarnings("unused")
public class AdvogadoDetailsActivityFragment extends Fragment {

    @BindView(R.id.advogadoIconDetails)
    ImageView advogadoIconDetails;

    @BindView(R.id.detailsAdvogadoNome)
    TextView detailsAdvogadoNome;

    @BindView(R.id.detailsAdvogadoNumeroOAB)
    TextView detailsAdvogadoNumeroOAB;

    @BindView(R.id.detailsAdvogadoEspecialidadeUm)
    TextView detailsAdvogadoEspecialidadeUm;

    @BindView(R.id.detailsAdvogadoEspecialidadeDois)
    TextView detailsAdvogadoEspecialidadeDois;

    @BindView(R.id.detailsAdvogadoTelefone)
    TextView detailsAdvogadoTelefone;

    @BindView(R.id.detailsAdvogadoEndereco)
    TextView detailsAdvogadoEndereco;

    @BindView(R.id.detailsAdvogadoBairro)
    TextView detailsAdvogadoBairro;

    @BindView(R.id.detailsAdvogadoCidade)
    TextView detailsAdvogadoCidade;

    @BindView(R.id.detailsAdvogadoEstado)
    TextView detailsAdvogadoEstado;

    @BindView(R.id.detailsAdvogadoComplemento)
    TextView detailsAdvogadoComplemento;


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
        detailsAdvogadoTelefone.setText(advogado.getTelefoneComercial());
        detailsAdvogadoEndereco.setText(advogado.getEndereco()+ " " +
                                        "nº"+advogado.getNumero());
        detailsAdvogadoBairro.setText(advogado.getComplemento());
        detailsAdvogadoBairro.setText(advogado.getBairro());
        detailsAdvogadoCidade.setText(advogado.getCidade());
        detailsAdvogadoEstado.setText(advogado.getEstado());
        Log.e("Debug","icon: "+advogado.getIconLista());
        UserHelper userHelper = new UserHelper();
        advogadoIconDetails.setImageDrawable(getActivity().getResources().getDrawable(userHelper.getUserIcon(advogado.getIconLista())));

        getActivity().setTitle(advogado.getNome() + " - OAB: "+ advogado.getNumeroInscricaoOAB());

        return view;
    }
}
