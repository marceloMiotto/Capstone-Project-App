package udacitynano.com.br.cafelegal;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import udacitynano.com.br.cafelegal.data.DatabaseContract;
import udacitynano.com.br.cafelegal.model.Advogado;
import udacitynano.com.br.cafelegal.model.Cliente;
import udacitynano.com.br.cafelegal.model.Pessoa;
import udacitynano.com.br.cafelegal.network.NetworkRequests;
import udacitynano.com.br.cafelegal.service.PerfilService;
import udacitynano.com.br.cafelegal.singleton.NetworkSingleton;
import udacitynano.com.br.cafelegal.singleton.UserType;
import udacitynano.com.br.cafelegal.util.Constant;

import static udacitynano.com.br.cafelegal.R.array.seccional;
import static udacitynano.com.br.cafelegal.R.id.perfil_especialista_um_spinner;

public class PerfilFragment extends Fragment implements AdapterView.OnItemSelectedListener
                                                      , LoaderManager.LoaderCallbacks<Cursor>{

    private static final int PERFIL_LOADER_ID = 11;
    @BindView(R.id.perfilNomeEditText)
    EditText mPerfilNomeEditText;
    @BindView(R.id.perfilNomeMeioEditText)
    EditText mPerfilNomeMeioEditText;
    @BindView(R.id.perfilSobrenomeEditText)
    EditText mPerfilSobrenomeEditText;
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
    @BindView(perfil_especialista_um_spinner)
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
    private String mNotificationRegistration;
    private String mFirebaseEmail;
    private String mFirebaseToken;

    Pessoa mCliente;
    Pessoa mAdvogado;
    Pessoa mPessoa;

    private JSONObject jsonObject;

    private OnFragmentInteractionListener mListener;

    public PerfilFragment() {
        // Required empty public constructor
    }

    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        sexoSpinner.setOnItemSelectedListener(this);
        mSeccionalSpinner.setOnItemSelectedListener(this);
        mPerfilEspecialistaUmSpinner.setOnItemSelectedListener(this);
        mPerfilEspecialistaDoisSpinner.setOnItemSelectedListener(this);

        SharedPreferences sharedPref = getActivity().getSharedPreferences(
         getActivity().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        mFirebaseEmail =  sharedPref.getString(getActivity().getString(R.string.preference_user_firebase_email),"x");
        mFirebaseToken =  sharedPref.getString(getActivity().getString(R.string.preference_user_firebase_token),"x");

        getLoaderManager().initLoader(PERFIL_LOADER_ID, null, this);

        mPerfilFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int requestMethod;
                String apiResource;
                String stringJsonObject = "";

                final UserType userType = UserType.getInstance(getActivity());
                if(userType.isAdvogado()){

                    int cep;
                    if(mPerfilCEPEditText.getText().toString().equals("")){
                        cep = 0;
                    }else{
                        cep = Integer.valueOf(mPerfilCEPEditText.getText().toString());
                    }
                    mAdvogado = new Advogado(userType.getUserId()
                            , mPerfilNomeEditText.getText().toString()
                            , mPerfilNomeMeioEditText.getText().toString()
                            , mPerfilSobrenomeEditText.getText().toString()
                            , mFirebaseEmail
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
                            , mFirebaseToken
                    );

                    apiResource = Constant.ADVOGADO;
                    Gson gson = new Gson();
                    stringJsonObject = gson.toJson(mAdvogado);
                    Log.e("Debug","Advogado json "+stringJsonObject);
                    mPessoa = mAdvogado;

                }else{

                    mCliente = new Cliente(userType.getUserId()
                            , mPerfilNomeEditText.getText().toString()
                            , mPerfilNomeMeioEditText.getText().toString()
                            , mPerfilSobrenomeEditText.getText().toString()
                            , mFirebaseEmail
                            , Integer.valueOf(mPerfilCEPEditText.getText().toString())
                            , mPerfilEnderecoEditText.getText().toString()
                            , mPerfilNumeroEditText.getText().toString()
                            , mPerfilComplementoEditText.getText().toString()
                            , mPerfilBairroEditText.getText().toString()
                            , mPerfilCidadeEditText.getText().toString()
                            , mPerfilEstadoEditText.getText().toString()
                            , mPerfilPaisEditText.getText().toString()
                            , mSexoChoosen
                            , mFirebaseToken
                    );

                    Log.e("Debug","Sexo escolhido: "+mSexoChoosen);
                    apiResource = Constant.CLIENTE;
                    Gson gson = new Gson();
                    stringJsonObject = gson.toJson(mCliente);
                    Log.e("Debug","Advogado json "+stringJsonObject);

                    mPessoa = mCliente;

                }


                requestMethod = Request.Method.PUT;


                try {
                    jsonObject = new JSONObject(stringJsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.e("Debug","server api link "+Constant.SERVER_API_CAFE_LEGAL+apiResource);

                final NetworkRequests networkRequests = new NetworkRequests(getActivity(),view);

                networkRequests.jsonRequest(Constant.PERFIL,requestMethod,Constant.SERVER_API_CAFE_LEGAL + apiResource,jsonObject,true);

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

        return view;
    }

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
                Log.e("Debug","Case mSexoChoosen: "+mSexoChoosen + " - spinner id"+spinner.getId());
                break;

            case R.id.perfil_seccional_spinner:
                mSeccionalChoosen = parent.getItemAtPosition(position).toString();
                Log.e("Debug","Case mSeccionalChoosen: "+mSeccionalChoosen+ " - spinner id"+spinner.getId());
                break;

            case perfil_especialista_um_spinner:
                mEspecialidadeUmChoosen = parent.getItemAtPosition(position).toString();
                Log.e("Debug","Case mEspecialidadeUmChoosen: "+mEspecialidadeUmChoosen+ " - spinner id"+spinner.getId());
                break;

            case R.id.perfil_especialista_dois_spinner:
                mEspecialidadeDoisChoosen = parent.getItemAtPosition(position).toString();
                Log.e("Debug","Case mEspecialidadeDoisChoosen: "+mEspecialidadeDoisChoosen+ " - spinner id"+spinner.getId());
                break;

            default:

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        String[] projection = {
                 DatabaseContract.PessoaEntry.COLUMN_NOME
               , DatabaseContract.PessoaEntry.COLUMN_NOME_MEIO
               , DatabaseContract.PessoaEntry.COLUMN_SOBRENOME
               , DatabaseContract.PessoaEntry.COLUMN_EMAIL
               , DatabaseContract.PessoaEntry.COLUMN_CEP
               , DatabaseContract.PessoaEntry.COLUMN_ENDERECO
               , DatabaseContract.PessoaEntry.COLUMN_NUMERO
               , DatabaseContract.PessoaEntry.COLUMN_COMPLEMENTO
               , DatabaseContract.PessoaEntry.COLUMN_BAIRRO
               , DatabaseContract.PessoaEntry.COLUMN_CIDADE
               , DatabaseContract.PessoaEntry.COLUMN_ESTADO
               , DatabaseContract.PessoaEntry.COLUMN_PAIS
               , DatabaseContract.PessoaEntry.COLUMN_SEXO
               , DatabaseContract.PessoaEntry.COLUMN_NUMERO_INSC_OAB
               , DatabaseContract.PessoaEntry.COLUMN_SECCIONAL
               , DatabaseContract.PessoaEntry.COLUMN_TIPO_INSCRICAO
               , DatabaseContract.PessoaEntry.COLUMN_FONE_COMERCIAL
               , DatabaseContract.PessoaEntry.COLUMN_TWITTER
               , DatabaseContract.PessoaEntry.COLUMN_LINKEDIN
               , DatabaseContract.PessoaEntry.COLUMN_ESPECIALISTA_UM
               , DatabaseContract.PessoaEntry.COLUMN_ESPECIALISTA_DOIS


        };
        String selectionClause = DatabaseContract.PessoaEntry.TABLE_NAME+"."+DatabaseContract.PessoaEntry.COLUMN_ID_SERVER + " = ?";
        String[] selectionArgs = {String.valueOf(UserType.getInstance(getActivity()).getUserId())};

        Log.e("Debug","Perfil Service pessoaValues "+projection);
        Log.e("Debug","Perfil Service  selectionClause "+selectionClause);
        Log.e("Debug","Perfil Service  selectionArgs "+selectionArgs[0]);

        CursorLoader loader = new CursorLoader(
                this.getActivity(),
                DatabaseContract.PessoaEntry.CONTENT_URI,
                projection,
                selectionClause,
                selectionArgs,
                null);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (cursor != null) {
            Log.e("Debug", "Lodader count " + cursor.getCount());
        }else{
            Log.e("Debug","Loader Cursor null");
        }


        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            mPerfilNomeEditText.setText(cursor.getString(0));
            mPerfilNomeMeioEditText.setText(cursor.getString(1));
            mPerfilSobrenomeEditText.setText(cursor.getString(2));
            mPerfilCEPEditText.setText(cursor.getString(4));
            mPerfilEnderecoEditText.setText(cursor.getString(5));
            mPerfilNumeroEditText.setText(cursor.getString(6));
            mPerfilComplementoEditText.setText(cursor.getString(7));
            mPerfilBairroEditText.setText(cursor.getString(8));
            mPerfilCidadeEditText.setText(cursor.getString(9));
            mPerfilEstadoEditText.setText(cursor.getString(10));
            mPerfilPaisEditText.setText(cursor.getString(11));

            List<String> strSexo = Arrays.asList(getResources().getStringArray(R.array.sexo_array));

            sexoSpinner.setSelection(strSexo.indexOf(cursor.getString(12)));

            UserType userType = UserType.getInstance(getActivity());
            if (userType.isAdvogado()) {
                mPerfilNumeroOABEditText.setText(cursor.getString(13));

                List<String> strSeccional = Arrays.asList(getResources().getStringArray(R.array.seccional));
                mSeccionalSpinner.setSelection(strSeccional.indexOf(cursor.getString(14)));
                Log.e("Debug","Spinner mSeccionalSpinner: "+cursor.getString(14) + " - INDEX NO ARRAY "+ strSeccional.indexOf(cursor.getString(14)));

                mPerfilTipoInscricaoTextView.setText(cursor.getString(15));
                mPerfilFoneComercialEditText.setText(cursor.getString(16));
                mPerfilTwitterEditText.setText(cursor.getString(17));
                mPerfilLinkedInEditText.setText(cursor.getString(18));

                List<String> strEspecial1 = Arrays.asList(getResources().getStringArray(R.array.especialidade));
                mPerfilEspecialistaUmSpinner.setSelection(strEspecial1.indexOf(cursor.getString(19)));

                List<String> strEspecial2 = Arrays.asList(getResources().getStringArray(R.array.especialidade));
                mPerfilEspecialistaDoisSpinner.setSelection(strEspecial2.indexOf(cursor.getString(20)));

            }

        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


}
