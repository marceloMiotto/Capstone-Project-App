package udacitynano.com.br.cafelegal;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.android.gms.common.api.GoogleApiClient;
import org.json.JSONException;
import butterknife.BindView;
import butterknife.ButterKnife;
import udacitynano.com.br.cafelegal.network.GoogleClient;
import udacitynano.com.br.cafelegal.service.ConviteService;
import udacitynano.com.br.cafelegal.singleton.UserType;

public class ConviteFragment extends Fragment  {


    private OnFragmentInteractionListener mListener;

    @BindView(R.id.convite_button)
    ImageButton mConviteButton;
    private View mView;
    private GoogleApiClient mGoogleApiClient;
    private String mAreaLocation;
    private int REQUEST_LOCATION = 1;
    private  GoogleClient mGoogleClient;

    public ConviteFragment() {
        // Required empty public constructor
    }

    public static ConviteFragment newInstance(String param1, String param2) {
        ConviteFragment fragment = new ConviteFragment();
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
        mView = inflater.inflate(R.layout.fragment_convite, container, false);
        ButterKnife.bind(this, mView);
        //create instance google api client
        mGoogleClient = new GoogleClient(getActivity());
        mGoogleApiClient = mGoogleClient.createGoogleClientInstance();


        mConviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String resultMsg = "";
                ConviteService conviteService = new ConviteService(getActivity(), mView);
                UserType userType = UserType.getInstance(getActivity());

                try {
                    SharedPreferences sharedPref = getActivity().getSharedPreferences(
                            getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                    mAreaLocation =  sharedPref.getString(getString(R.string.preference_user_last_location),"");

                    if (conviteService.sendConvite(userType.getUserId(),mAreaLocation) == 0) {
                        resultMsg = getActivity().getString(R.string.convite_result_enviado_sucesso);
                    } else {
                        resultMsg = getActivity().getString(R.string.convite_result_erro_enviar);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                };

                Snackbar.make(mView, resultMsg, Snackbar.LENGTH_SHORT).show();
            }
        });


        return mView;
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
        if(mGoogleApiClient != null){
            mGoogleApiClient.connect();
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mGoogleApiClient.disconnect();
        mListener = null;
    }


    //permission result
    @SuppressWarnings({"MissingPermission"})
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {

         if (requestCode == REQUEST_LOCATION) {
            if(grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mGoogleClient.setLocationSharedPref();


            } else {
                // Permission was denied or request was cancelled
                Toast.makeText(getActivity(),getActivity().getString(R.string.permission_denied),Toast.LENGTH_SHORT).show();
            }
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


}
