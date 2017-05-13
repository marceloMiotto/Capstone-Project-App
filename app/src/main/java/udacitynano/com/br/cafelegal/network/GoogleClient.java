package udacitynano.com.br.cafelegal.network;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import udacitynano.com.br.cafelegal.R;

import static com.google.android.gms.location.LocationServices.FusedLocationApi;

public class GoogleClient implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private int REQUEST_LOCATION = 1;
    private Context mContext;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    public GoogleClient(Context context){

        this.mContext = context;

    }

    public GoogleApiClient createGoogleClientInstance(){
    // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        }

        return mGoogleApiClient;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getLastLocation();

    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }


    public void getLastLocation(){

        if (ActivityCompat.checkSelfPermission(mContext, "android.permission.ACCESS_FINE_LOCATION")
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions((Activity) mContext,
                    new String[]{"android.permission.ACCESS_FINE_LOCATION"},
                    REQUEST_LOCATION);
        } else {
            // permission has been granted, continue as usual
            setLocationSharedPref();
        }
    }

    @SuppressWarnings({"MissingPermission"})
    public void setLocationSharedPref() {
        NetworkAccess networkAccess = new NetworkAccess(mContext);
        if (networkAccess.networkUp()) {

            SharedPreferences sharedPref = mContext.getSharedPreferences(
                    mContext.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            mLastLocation = FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                Toast.makeText(mContext, "Coordenadas atuais Latitude: " + String.valueOf(mLastLocation.getLatitude()) + " Longitude: " + String.valueOf(mLastLocation.getLongitude()), Toast.LENGTH_LONG).show();
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(mContext.getString(R.string.preference_user_last_location), "");
                editor.commit();
            }
        }
    }

}