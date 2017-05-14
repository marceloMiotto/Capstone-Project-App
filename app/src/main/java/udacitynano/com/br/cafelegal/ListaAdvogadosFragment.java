package udacitynano.com.br.cafelegal;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import udacitynano.com.br.cafelegal.adapter.ConviteHistoricoAdapter;
import udacitynano.com.br.cafelegal.adapter.ListaAdvogadosAdapter;
import udacitynano.com.br.cafelegal.data.DatabaseContract;
import udacitynano.com.br.cafelegal.model.Advogado;
import udacitynano.com.br.cafelegal.model.Convite;
import udacitynano.com.br.cafelegal.service.ConviteService;
import udacitynano.com.br.cafelegal.service.PerfilService;
import udacitynano.com.br.cafelegal.singleton.NetworkSingleton;
import udacitynano.com.br.cafelegal.util.Constant;


public class ListaAdvogadosFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LISTA_ADVOGADOS_LOADER_ID = 13;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Advogado> myDataset;
    private OnFragmentInteractionListener mListener;


    public ListaAdvogadosFragment() {
        // Required empty public constructor
    }

    public static ListaAdvogadosFragment newInstance(String param1, String param2) {
        ListaAdvogadosFragment fragment = new ListaAdvogadosFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lista_advogados, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.lista_advogados_recyclerView);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        myDataset = new ArrayList<>();
        // specify an adapter (see also next example)
        mAdapter = new ListaAdvogadosAdapter(getActivity(),myDataset);
        mRecyclerView.setAdapter(mAdapter);
        Log.e("Debug15","Convite historico entrou");
        getLoaderManager().initLoader(LISTA_ADVOGADOS_LOADER_ID, null, this);

        String apiURL = Constant.SERVER_API_CAFE_LEGAL + Constant.ADVOGADOS;
        Log.e("Debug2", " aceito url: " + apiURL);
        //TODO INFORMAR A LATITUDE E LONGITUDE CORRETAS

        JsonArrayRequest jsonArrayObject = new JsonArrayRequest
                (Request.Method.GET, apiURL,null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            Log.e("Debug13",response.toString());
                            for(int i=0;i<response.length();i++){
                                JSONObject jsonAdvogados = response.getJSONObject(i);
                                PerfilService perfilService = new PerfilService(getActivity(),null);
                                perfilService.updateCreateUserOnSQLite(new Gson().fromJson(jsonAdvogados.toString(),Advogado.class));
                                //myDataset.add(new Gson().fromJson(jsonAdvogados.toString(),Advogado.class));
                                Log.e("Debug13",jsonAdvogados.toString());
                            }

                            //mAdapter.notifyDataSetChanged();
                            Log.e("Debug12","Ok");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Debug", "Network error: " + error.getMessage() + String.valueOf(error.networkResponse.statusCode));

                    }
                }) {


            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        // Access the RequestQueue through your singleton class.
        NetworkSingleton.getInstance(getActivity()).addJSONArrayToRequestQueue(jsonArrayObject);

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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        String[] projection = {
                DatabaseContract.PessoaEntry.COLUMN_ID_SERVER,
                DatabaseContract.PessoaEntry.COLUMN_NOME,
                DatabaseContract.PessoaEntry.COLUMN_NOME_MEIO,
                DatabaseContract.PessoaEntry.COLUMN_SOBRENOME,
                DatabaseContract.PessoaEntry.COLUMN_EMAIL,
                DatabaseContract.PessoaEntry.COLUMN_CEP,
                DatabaseContract.PessoaEntry.COLUMN_ENDERECO,
                DatabaseContract.PessoaEntry.COLUMN_NUMERO,
                DatabaseContract.PessoaEntry.COLUMN_COMPLEMENTO,
                DatabaseContract.PessoaEntry.COLUMN_BAIRRO,
                DatabaseContract.PessoaEntry.COLUMN_CIDADE,
                DatabaseContract.PessoaEntry.COLUMN_ESTADO,
                DatabaseContract.PessoaEntry.COLUMN_PAIS,
                DatabaseContract.PessoaEntry.COLUMN_NUMERO_INSC_OAB,
                DatabaseContract.PessoaEntry.COLUMN_SECCIONAL,
                DatabaseContract.PessoaEntry.COLUMN_FONE_COMERCIAL,
                DatabaseContract.PessoaEntry.COLUMN_ESPECIALISTA_UM,
                DatabaseContract.PessoaEntry.COLUMN_ESPECIALISTA_DOIS

        };

        Log.e("Debug","Convite loader projection "+projection);

        CursorLoader loader = new CursorLoader(
                this.getActivity(),
                DatabaseContract.PessoaEntry.CONTENT_URI,
                projection,
                null,
                null,
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
            for (int i = 0; i < cursor.getCount(); i++) {

                myDataset.add(new Advogado(
                        cursor.getLong(0)      //id,
                        , cursor.getString(1)  //nome,
                        , cursor.getString(2)  //nomeMeio
                        , cursor.getString(3)  // sobrenome
                        , cursor.getString(4)  // email
                        , cursor.getInt(5)     // cep
                        , cursor.getString(6)  // endereco
                        , cursor.getString(7)  // numero
                        , cursor.getString(8)  // complemento
                        , cursor.getString(9)  // bairro
                        , cursor.getString(10) // cidade
                        , cursor.getString(11) // estado
                        , cursor.getString(12) // pais
                        , ""                   // sexo
                        , cursor.getString(13) // numeroInscricaoOAB
                        , cursor.getString(14) // seccional
                        , ""                   // tipoInscricao
                        , cursor.getString(15) // telefoneComercial
                        , ""                   // twitter
                        , ""                   // linkedIn
                        , cursor.getString(16) // especialistaUm
                        , cursor.getString(17) // especialistaDois
                        , ""                   // notificationRegistration
                ));
                cursor.moveToNext();
            }

        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }


}
