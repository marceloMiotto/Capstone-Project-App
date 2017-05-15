package udacitynano.com.br.cafelegal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import udacitynano.com.br.cafelegal.adapter.ConvitesAbertosAdapter;
import udacitynano.com.br.cafelegal.model.Convite;
import udacitynano.com.br.cafelegal.singleton.NetworkSingleton;
import udacitynano.com.br.cafelegal.singleton.UserType;
import udacitynano.com.br.cafelegal.util.Constant;


public class ListaConvitesAbertosFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Convite> myDataset;


    private OnFragmentInteractionListener mListener;

    public ListaConvitesAbertosFragment() {
        // Required empty public constructor
    }

    public static ListaConvitesAbertosFragment newInstance(String param1, String param2) {
        ListaConvitesAbertosFragment fragment = new ListaConvitesAbertosFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_convites_abertos, container, false);
        getConvitesAbertos();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.convites_abertos_recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        myDataset = new ArrayList<>();
        mAdapter = new ConvitesAbertosAdapter(getActivity(),myDataset);
        mRecyclerView.setAdapter(mAdapter);
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


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    public void jsonRequest(String apiURL ) {


        JsonArrayRequest jsonArrayObject = new JsonArrayRequest
                (Request.Method.GET, apiURL, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            for(int i=0;i<response.length();i++){
                                JSONObject jsonConvite = response.getJSONObject(i);
                                myDataset.add(new Gson().fromJson(jsonConvite.toString(),Convite.class));
                            }
                            mAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                    }
                });


        // Access the RequestQueue through your singleton class.
        NetworkSingleton.getInstance(getActivity()).addJSONArrayToRequestQueue(jsonArrayObject);


    }

    public void getConvitesAbertos(){
        long advogadoId = UserType.getUserId();
        String apiURL = Constant.SERVER_API_CAFE_LEGAL+Constant.CONVITE_CAFE_LEGAL+"/"+advogadoId+Constant.ABERTOS;
        jsonRequest(apiURL);

    }

}
