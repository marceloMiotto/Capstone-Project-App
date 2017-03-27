package udacitynano.com.br.cafelegal;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import udacitynano.com.br.cafelegal.singleton.UserType;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PerfilFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {

    @BindView(R.id.perfilNomeEditText)
    EditText mPerfilNomeEditText;
    @BindView(R.id.perfilNomeMeioEditText)
    EditText mPerfilNomeMeioEditText;
    @BindView(R.id.perfilSobrenomeEditText)
    EditText mPerfilSobrenomeEditText;
    @BindView(R.id.perfilEmailEditText)
    EditText mPerfilEmailEditText;
    @BindView(R.id.perfilCEPEditText)
    EditText mPerfilCEPEditText;
    @BindView(R.id.perfilEnderecoEditText)
    EditText mPerfilEnderecoEditText;
    @BindView(R.id.perfilNumeroEditText)
    EditText mPerfilNumeroEditText;
    @BindView(R.id.perfilComplementoEditText)
    EditText mPerfilComplementoEditText;
    @BindView(R.id.perfilBairroEditText)
    EditText mPerfilBairroEditText;
    @BindView(R.id.perfilCidadeEditText)
    EditText mPerfilCidadeEditText;
    @BindView(R.id.perfilEstadoEditText)
    EditText mPerfilEstadoEditText;
    @BindView(R.id.perfilPaisEditText)
    EditText mPerfilPaisEditText;
    @BindView(R.id.perfil_sexo_spinner)
    Spinner sexoSpinner;

    @Nullable @BindView(R.id.perfilNumeroOABEditText)
    EditText mPerfilNumeroOABEditText;
    @Nullable @BindView(R.id.perfilSeccionalEditText)
    EditText mPerfilSeccionalEditText;
    @Nullable @BindView(R.id.perfilTipoInscricaoEditText)
    EditText mPerfilTipoInscricaoEditText;
    @Nullable @BindView(R.id.perfilFoneComercialEditText)
    EditText mPerfilFoneComercialEditText;
    @Nullable @BindView(R.id.perfilTwitterEditText)
    EditText mPerfilTwitterEditText;
    @Nullable @BindView(R.id.perfilLinkedInEditText)
    EditText mPerfilLinkedInEditText;
    @Nullable @BindView(R.id.perfilEspecialistaUmEditText)
    EditText mPerfilEspecialistaUmEditText;
    @Nullable @BindView(R.id.perfilEspecialistaDoisEditText)
    EditText mPerfilEspecialistaDoisEditText;

    @BindView(R.id.perfil_fab)
    FloatingActionButton mPerfilFab;

    View view;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PerfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        if(UserType.isAdvogado()){
            view = inflater.inflate(R.layout.fragment_perfil_advogado, container, false);
        }
        else{
            view = inflater.inflate(R.layout.fragment_perfil_cliente, container, false);
        }

        ButterKnife.bind(this,view);

        mPerfilFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Fab Test",Toast.LENGTH_SHORT).show();
                Snackbar.make(view,"Snack Test",Snackbar.LENGTH_SHORT).show();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.sexo_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexoSpinner.setAdapter(adapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
