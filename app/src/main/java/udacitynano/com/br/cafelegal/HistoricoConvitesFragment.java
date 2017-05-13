package udacitynano.com.br.cafelegal;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
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
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import udacitynano.com.br.cafelegal.adapter.ConviteAdapter;
import udacitynano.com.br.cafelegal.data.DatabaseContract;
import udacitynano.com.br.cafelegal.model.Convite;
import udacitynano.com.br.cafelegal.service.ConviteService;
import udacitynano.com.br.cafelegal.singleton.UserType;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HistoricoConvitesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistoricoConvitesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoricoConvitesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Convite> myDataset;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HistoricoConvitesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoricoConvitesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoricoConvitesFragment newInstance(String param1, String param2) {
        HistoricoConvitesFragment fragment = new HistoricoConvitesFragment();
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
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_historico_convites, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.convite_historico_recyclerView);

        ConviteService conviteService = new ConviteService(getActivity(),view);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        myDataset = new ArrayList<>();
        // specify an adapter (see also next example)
        mAdapter = new ConviteAdapter(getActivity(),myDataset);
        mRecyclerView.setAdapter(mAdapter);


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
    public Loader onCreateLoader(int id, Bundle args) {
        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        String[] projection = {
                DatabaseContract.ConviteEntry.COLUMN_ID_CONVITE_SERVER,
                DatabaseContract.ConviteEntry.COLUMN_CONVIDA_ID,
                DatabaseContract.ConviteEntry.COLUMN_RESPONDE_ID,
                DatabaseContract.ConviteEntry.COLUMN_DATA_CONVITE,
                DatabaseContract.ConviteEntry.COLUMN_CONVITE_ACEITO,
                DatabaseContract.ConviteEntry.COLUMN_CHAT_FIREBASE,
                DatabaseContract.ConviteEntry.COLUMN_ESPECIALIDADE,
                DatabaseContract.ConviteEntry.COLUMN_AREA_LOCATION
        };

        Log.e("Debug","Convite loader projection "+projection);

        CursorLoader loader = new CursorLoader(
                this.getActivity(),
                DatabaseContract.ConviteEntry.CONTENT_URI,
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
                myDataset.add(new Convite(cursor.getLong(0), cursor.getLong(1), cursor.getLong(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString((7))));
            }


        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
