package udacitynano.com.br.cafelegal;

import android.os.Bundle;
import android.app.Fragment;
import android.os.Parcelable;
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

    private Advogado mAdvogado;
    public AdvogadoDetailsActivityFragment() {
    }


    public static AdvogadoDetailsActivityFragment newInstance(Parcelable advogado) {

        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.ADVOGADO_ESCOLHIDO,advogado);
        AdvogadoDetailsActivityFragment advogadoDetailsActivityFragment = new AdvogadoDetailsActivityFragment();
        advogadoDetailsActivityFragment.setArguments(bundle);
        return advogadoDetailsActivityFragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            mAdvogado = bundle.getParcelable(Constant.ADVOGADO_ESCOLHIDO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_advogado_details, container, false);
        ButterKnife.bind(this,view);
        if (getArguments() != null) {
            readBundle(getArguments());
        }else{
            Bundle data = getActivity().getIntent().getExtras();
            mAdvogado = data.getParcelable(Constant.ADVOGADO_ESCOLHIDO);
        }

        detailsAdvogadoNome.setText(mAdvogado.getNome() + " " + mAdvogado.getSobrenome());
        detailsAdvogadoNumeroOAB.setText(mAdvogado.getNumeroInscricaoOAB());
        detailsAdvogadoEspecialidadeUm.setText(mAdvogado.getEspecialistaUm());
        detailsAdvogadoEspecialidadeDois.setText(mAdvogado.getEspecialistaDois());
        detailsAdvogadoTelefone.setText(mAdvogado.getTelefoneComercial());
        detailsAdvogadoEndereco.setText(mAdvogado.getEndereco()+ " " +
                                        "nº"+mAdvogado.getNumero());
        detailsAdvogadoBairro.setText(mAdvogado.getComplemento());
        detailsAdvogadoBairro.setText(mAdvogado.getBairro());
        detailsAdvogadoCidade.setText(mAdvogado.getCidade());
        detailsAdvogadoEstado.setText(mAdvogado.getEstado());
        Log.e("Debug","icon: "+mAdvogado.getIconLista());
        UserHelper userHelper = new UserHelper();
        advogadoIconDetails.setImageDrawable(getActivity().getResources().getDrawable(userHelper.getUserIcon(mAdvogado.getIconLista())));

        getActivity().setTitle(mAdvogado.getNome() + " - OAB: "+ mAdvogado.getNumeroInscricaoOAB());

        return view;
    }
}
