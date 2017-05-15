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
import java.util.ArrayList;
import java.util.List;
import udacitynano.com.br.cafelegal.adapter.ConviteHistoricoAdapter;
import udacitynano.com.br.cafelegal.data.DatabaseContract;
import udacitynano.com.br.cafelegal.model.Convite;


public class HistoricoConvitesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int CONVITE_HISTORICO_LOADER_ID = 12;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Convite> myDataset;



    private OnFragmentInteractionListener mListener;

    public HistoricoConvitesFragment() {
        // Required empty public constructor
    }

    public static HistoricoConvitesFragment newInstance(String param1, String param2) {
        HistoricoConvitesFragment fragment = new HistoricoConvitesFragment();

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

        View view = inflater.inflate(R.layout.fragment_historico_convites, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.convite_historico_recyclerView);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        myDataset = new ArrayList<>();
        // specify an adapter (see also next example)
        mAdapter = new ConviteHistoricoAdapter(getActivity(),myDataset);
        mRecyclerView.setAdapter(mAdapter);
        getLoaderManager().initLoader(CONVITE_HISTORICO_LOADER_ID, null, this);

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
    public Loader onCreateLoader(int id, Bundle args) {

        String[] projection = {
                DatabaseContract.ConviteEntry.COLUMN_ID_CONVITE_SERVER,
                DatabaseContract.ConviteEntry.COLUMN_CONVIDA_ID,
                DatabaseContract.ConviteEntry.COLUMN_RESPONDE_ID,
                DatabaseContract.ConviteEntry.COLUMN_DATA_CONVITE,
                DatabaseContract.ConviteEntry.COLUMN_CONVITE_ACEITO,
                DatabaseContract.ConviteEntry.COLUMN_CHAT_FIREBASE,
                DatabaseContract.ConviteEntry.COLUMN_ESPECIALIDADE,
                DatabaseContract.ConviteEntry.COLUMN_AREA_LOCATION,
                DatabaseContract.ConviteEntry.COLUMN_NOME_CONVIDA,
                DatabaseContract.ConviteEntry.COLUMN_NOME_ADVOGADO,
                DatabaseContract.ConviteEntry.COLUMN_ADVOGADO_OAB
        };

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

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                myDataset.add(new Convite( cursor.getLong(0)
                                         , cursor.getLong(1)
                                         , cursor.getLong(2)
                                         , cursor.getString(3)
                                         , cursor.getString(4)
                                         , cursor.getString(5)
                                         , cursor.getString(6)
                                         , cursor.getString(7)
                                         , cursor.getString(8)
                                         , cursor.getString(9)
                                         , cursor.getString(10)
                                         ));
                cursor.moveToNext();

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
