package udacitynano.com.br.cafelegal;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import udacitynano.com.br.cafelegal.model.Advogado;
import udacitynano.com.br.cafelegal.model.Cliente;
import udacitynano.com.br.cafelegal.model.Pessoa;
import udacitynano.com.br.cafelegal.service.PerfilService;
import udacitynano.com.br.cafelegal.singleton.UserType;

import static udacitynano.com.br.cafelegal.R.array.seccional;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PerfilFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment implements AdapterView.OnItemSelectedListener {

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
    @Nullable @BindView(R.id.perfil_seccional_spinner)
    Spinner mSeccionalSpinner;
    @Nullable @BindView(R.id.perfilTipoInscricaoTextView)
    TextView mPerfilTipoInscricaoTextView;
    @Nullable @BindView(R.id.perfilFoneComercialEditText)
    EditText mPerfilFoneComercialEditText;
    @Nullable @BindView(R.id.perfilTwitterEditText)
    EditText mPerfilTwitterEditText;
    @Nullable @BindView(R.id.perfilLinkedInEditText)
    EditText mPerfilLinkedInEditText;
    @BindView(R.id.perfil_especialista_um_spinner)
    Spinner mPerfilEspecialistaUmSpinner;
    @BindView(R.id.perfil_especialista_dois_spinner)
    Spinner mPerfilEspecialistaDoisSpinner;
    @BindView(R.id.perfil_fab)
    FloatingActionButton mPerfilFab;

    View view;

    private String mSexoChoosen;
    private String mSeccionalChoosen;
    private String mEspecialidadeUmChoosen;
    private String mEspecialidadeDoisChoosen;

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

        PerfilService perfilServiceMain = new PerfilService();

        if(UserType.isAdvogado()){
            view = inflater.inflate(R.layout.fragment_perfil_advogado, container, false);
        }
        else{
            view = inflater.inflate(R.layout.fragment_perfil_cliente, container, false);
        }

        ButterKnife.bind(this,view);

        sexoSpinner.setOnItemSelectedListener(this);
        mSeccionalSpinner.setOnItemSelectedListener(this);
        mPerfilEspecialistaUmSpinner.setOnItemSelectedListener(this);
        mPerfilEspecialistaDoisSpinner.setOnItemSelectedListener(this);

        mPerfilFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Fab Test",Toast.LENGTH_SHORT).show();
                Snackbar.make(view,"Snack Test",Snackbar.LENGTH_SHORT).show();


                UserType userType = UserType.getInstance(getActivity());
                if(userType.isAdvogado()){

                    int cep;
                    if(mPerfilCEPEditText.getText().toString().equals("")){
                        cep = 0;
                    }else{
                        cep = Integer.valueOf(mPerfilCEPEditText.getText().toString());
                    }
                    Pessoa advogado = new Advogado(userType.getUserId()
                            , mPerfilNomeEditText.getText().toString()
                            , mPerfilNomeMeioEditText.getText().toString()
                            , mPerfilSobrenomeEditText.getText().toString()
                            , mPerfilEmailEditText.getText().toString()
                            , cep
                            , mPerfilEnderecoEditText.getText().toString()
                            , mPerfilNumeroEditText.getText().toString()
                            , mPerfilComplementoEditText.getText().toString()
                            , mPerfilBairroEditText.getText().toString()
                            , mPerfilCidadeEditText.getText().toString()
                            , mPerfilEstadoEditText.getText().toString()
                            , mPerfilPaisEditText.getText().toString()
                            , mSexoChoosen
                            , mPerfilNumeroOABEditText.getText().toString()
                            , mSeccionalChoosen
                            , mPerfilTipoInscricaoTextView.getText().toString()
                            , mPerfilFoneComercialEditText.getText().toString()
                            , mPerfilTwitterEditText.getText().toString()
                            , mPerfilLinkedInEditText.getText().toString()
                            , mEspecialidadeUmChoosen
                            , mEspecialidadeDoisChoosen
                    );

                    PerfilService perfilService = new PerfilService();
                    if(userType.getUserId() < 0){
                        long id = perfilService.createPerfil(advogado);
                        setSharedId(id);
                    }else{
                        perfilService.updatePerfil(advogado);
                    }

                }else{

                    Pessoa cliente = new Cliente(userType.getUserId()
                            , mPerfilNomeEditText.getText().toString()
                            , mPerfilNomeMeioEditText.getText().toString()
                            , mPerfilSobrenomeEditText.getText().toString()
                            , mPerfilEmailEditText.getText().toString()
                            , Integer.valueOf(mPerfilCEPEditText.getText().toString())
                            , mPerfilEnderecoEditText.getText().toString()
                            , mPerfilNumeroEditText.getText().toString()
                            , mPerfilComplementoEditText.getText().toString()
                            , mPerfilBairroEditText.getText().toString()
                            , mPerfilCidadeEditText.getText().toString()
                            , mPerfilEstadoEditText.getText().toString()
                            , mPerfilPaisEditText.getText().toString()
                            , mSexoChoosen
                    );

                    PerfilService perfilService = new PerfilService();

                    if(userType.getUserId() < 0){
                        long id = perfilService.createPerfil(cliente);
                        setSharedId(id);
                    }else{
                        perfilService.updatePerfil(cliente);
                    }

                }

            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.sexo_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexoSpinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> seccionalAdapter = ArrayAdapter.createFromResource(getActivity(),
                seccional, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSeccionalSpinner.setAdapter(seccionalAdapter);

        ArrayAdapter<CharSequence> especialidadeAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.especialidade, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPerfilEspecialistaUmSpinner.setAdapter(especialidadeAdapter);
        mPerfilEspecialistaDoisSpinner.setAdapter(especialidadeAdapter);

        UserType userType = UserType.getInstance(getActivity());
        if(userType.isAdvogado()) {
            //getPerfil advogado
            Advogado pessoa = (Advogado) perfilServiceMain.getPerfil(userType.getAppUserType());
            mPerfilNomeEditText.setText(pessoa.getNome());
            mPerfilNomeMeioEditText.setText(pessoa.getNomeMeio());
            mPerfilSobrenomeEditText.setText(pessoa.getSobrenome());
            mPerfilEmailEditText.setText(pessoa.getEmail());
            mPerfilCEPEditText.setText(pessoa.getCep());
            mPerfilEnderecoEditText.setText(pessoa.getEndereco());
            mPerfilNumeroEditText.setText(pessoa.getNumero());
            mPerfilComplementoEditText.setText(pessoa.getComplemento());
            mPerfilBairroEditText.setText(pessoa.getBairro());
            mPerfilCidadeEditText.setText(pessoa.getCidade());
            mPerfilEstadoEditText.setText(pessoa.getEstado());
            mPerfilPaisEditText.setText(pessoa.getPais());
            for(int i=0; i < adapter.getCount(); i++) {
                if(pessoa.getSexo().trim().equals(adapter.getItem(i).toString())){
                    sexoSpinner.setSelection(i);
                    break;
                }
            }
            mPerfilNumeroOABEditText.setText(pessoa.getNumeroInscricaoOAB());

            for(int i=0; i < seccionalAdapter.getCount(); i++) {
                if(pessoa.getSeccional().trim().equals(adapter.getItem(i).toString())){
                    mSeccionalSpinner.setSelection(i);
                    break;
                }
            }
            mPerfilTipoInscricaoTextView.setText(pessoa.getTipoInscricao());
            mPerfilFoneComercialEditText.setText(pessoa.getTelefoneComercial());
            mPerfilTwitterEditText.setText(pessoa.getTwitter());
            mPerfilLinkedInEditText.setText(pessoa.getLinkedIn());
            for(int i=0; i < especialidadeAdapter.getCount(); i++) {
                if(pessoa.getEspecialistaUm().trim().equals(adapter.getItem(i).toString())){
                    mPerfilEspecialistaUmSpinner.setSelection(i);
                    break;
                }
            }
            for(int i=0; i < especialidadeAdapter.getCount(); i++) {
                if(pessoa.getSexo().trim().equals(adapter.getItem(i).toString())){
                    mPerfilEspecialistaDoisSpinner.setSelection(i);
                    break;
                }
            }
        }else{

            Cliente pessoa = (Cliente) perfilServiceMain.getPerfil(userType.getAppUserType());
            mPerfilNomeEditText.setText(pessoa.getNome());
            mPerfilNomeMeioEditText.setText(pessoa.getNomeMeio());
            mPerfilSobrenomeEditText.setText(pessoa.getSobrenome());
            mPerfilEmailEditText.setText(pessoa.getEmail());
            mPerfilCEPEditText.setText(pessoa.getCep());
            mPerfilEnderecoEditText.setText(pessoa.getEndereco());
            mPerfilNumeroEditText.setText(pessoa.getNumero());
            mPerfilComplementoEditText.setText(pessoa.getComplemento());
            mPerfilBairroEditText.setText(pessoa.getBairro());
            mPerfilCidadeEditText.setText(pessoa.getCidade());
            mPerfilEstadoEditText.setText(pessoa.getEstado());
            mPerfilPaisEditText.setText(pessoa.getPais());

            for(int i=0; i < adapter.getCount(); i++) {
                if(pessoa.getSexo().trim().equals(adapter.getItem(i).toString())){
                    sexoSpinner.setSelection(i);
                    break;
                }
            }


        }


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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        switch (spinner.getId()){
            case R.id.perfil_sexo_spinner:
                mSexoChoosen = parent.getItemAtPosition(position).toString();
                break;

            case R.id.perfil_seccional_spinner:
                mSeccionalChoosen = parent.getItemAtPosition(position).toString();
                break;


            case R.id.perfil_especialista_um_spinner:
                mEspecialidadeUmChoosen = parent.getItemAtPosition(position).toString();
                break;

            case R.id.perfil_especialista_dois_spinner:
                mEspecialidadeDoisChoosen = parent.getItemAtPosition(position).toString();
                break;

            default:

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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


    private void setSharedId(long id){
        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(getString(R.string.preference_user_type_id), id);
        editor.commit();
    }


}
