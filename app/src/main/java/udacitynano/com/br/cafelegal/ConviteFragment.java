package udacitynano.com.br.cafelegal;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import udacitynano.com.br.cafelegal.network.NetworkAccess;
import udacitynano.com.br.cafelegal.service.ConviteService;
import udacitynano.com.br.cafelegal.singleton.UserType;

import static com.google.android.gms.location.LocationServices.FusedLocationApi;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConviteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConviteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConviteFragment extends Fragment implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.convite_button)
    ImageButton mConviteButton;

    private View mView;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private int REQUEST_LOCATION = 1;
    private String mAreaLocation;

    public ConviteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConviteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConviteFragment newInstance(String param1, String param2) {
        ConviteFragment fragment = new ConviteFragment();
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
        mView = inflater.inflate(R.layout.fragment_convite, container, false);
        ButterKnife.bind(this, mView);
        mConviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String resultMsg = "";
                ConviteService conviteService = new ConviteService(getActivity(), mView);
                UserType userType = UserType.getInstance(getActivity());

                try {
                    NetworkAccess networkAccess = new NetworkAccess(getActivity());
                    SharedPreferences sharedPref = getActivity().getSharedPreferences(
                            getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                    if(networkAccess.networkUp()){
                        if(mAreaLocation.equals("")||mAreaLocation == null){
                            mAreaLocation =  sharedPref.getString(getString(R.string.preference_user_last_location),"");
                        }

                    }else{
                        mAreaLocation =  sharedPref.getString(getString(R.string.preference_user_last_location),"");
                    }
                    if (conviteService.sendConvite(userType.getUserId(),mAreaLocation) == 0) {
                        resultMsg = "Convite enviado com sucesso";
                    } else {
                        resultMsg = "Erro ao enviar o convite. Tente mais tarde!";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ;

                Snackbar.make(mView, resultMsg, Snackbar.LENGTH_SHORT).show();
            }
        });

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }


        return mView;
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
        mGoogleApiClient.connect();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mGoogleApiClient.disconnect();
        mListener = null;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);


        if (ActivityCompat.checkSelfPermission(getActivity(), "android.permission.ACCESS_FINE_LOCATION")
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{"android.permission.ACCESS_FINE_LOCATION"},
                    REQUEST_LOCATION);
        } else {
            // permission has been granted, continue as usual
            mLastLocation = FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                Toast.makeText(getActivity(),"Coordenadas atuais Latitude: "+String.valueOf(mLastLocation.getLatitude()) + " Longitude: "+String.valueOf(mLastLocation.getLongitude()),Toast.LENGTH_LONG).show();
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getActivity().getString(R.string.preference_user_last_location), "");
                editor.commit();
                mAreaLocation =String.valueOf(mLastLocation.getLatitude())+"#"+String.valueOf(mLastLocation.getLongitude());
            }
        }
    }

    //permission result
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if(grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(getActivity(),"Permission Granted. ",Toast.LENGTH_SHORT).show();
            } else {
                // Permission was denied or request was cancelled
                Toast.makeText(getActivity(),"Permission Denied. ",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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

    //Custom code
    //TODO remove unnecessary code
}
