package udacitynano.com.br.cafelegal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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


@SuppressWarnings({"unused", "AccessStaticViaInstance"})
public class ListaConvitesAbertosFragment extends Fragment
{


    private RecyclerView.Adapter mAdapter;
    private List<Convite> myDataset;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean mIsTablet;

    private OnFragmentInteractionListener mListener;

    public ListaConvitesAbertosFragment() {
        // Required empty public constructor
    }


    public static ListaConvitesAbertosFragment newInstance(boolean isTablet) {

        Bundle bundle = new Bundle();
        bundle.putBoolean("isTablet",isTablet);
        ListaConvitesAbertosFragment listaConvitesAbertosFragment = new ListaConvitesAbertosFragment();
        listaConvitesAbertosFragment.setArguments(bundle);
        return listaConvitesAbertosFragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            mIsTablet = bundle.getBoolean("isTablet");
        }
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
        readBundle(getArguments());
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.convites_abertos_recyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        myDataset = new ArrayList<>();
        mAdapter = new ConvitesAbertosAdapter(myDataset,mIsTablet);
        mRecyclerView.setAdapter(mAdapter);
        getActivity().setTitle(getString(R.string.title_convites_abertos));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getConvitesAbertos();
                Log.e("Debug","onRefresh");
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

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


    private void jsonRequest(String apiURL) {


        JsonArrayRequest jsonArrayObject = new JsonArrayRequest
                (Request.Method.GET, apiURL, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("Debug","Response length "+response.length());
                        try {
                            myDataset.clear();
                            for(int i=0;i<response.length();i++){
                                JSONObject jsonConvite = response.getJSONObject(i);
                                myDataset.add(new Gson().fromJson(jsonConvite.toString(),Convite.class));
                            }
                            mAdapter.notifyDataSetChanged();
                            Log.e("Debug","Entrou response fragment");

                        } catch (JSONException e) {
                            Log.e("Debug","Error response "+ e.getMessage());
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

    private void getConvitesAbertos(){
        long advogadoId = UserType.getUserId(getActivity());
        String apiURL = Constant.SERVER_API_CAFE_LEGAL+Constant.CONVITE_CAFE_LEGAL+"/"+advogadoId+Constant.ABERTOS;
        jsonRequest(apiURL);

    }

}
