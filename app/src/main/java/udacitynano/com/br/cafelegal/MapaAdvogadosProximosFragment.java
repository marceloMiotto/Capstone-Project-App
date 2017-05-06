package udacitynano.com.br.cafelegal;

import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;

import com.google.android.gms.common.api.GoogleApiClient;

import static com.google.android.gms.location.LocationServices.FusedLocationApi;


public class MapaAdvogadosProximosFragment  extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    LatLng myLocation;

    private int REQUEST_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mapa_advogados_proximos);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-30.06329997, -51.1712265);
        LatLng sydneyTest = new LatLng(-30.06285427, -51.16281509);

        mMap.addMarker(new MarkerOptions().position(sydney).title("Puc")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_work_black_24dp));
        mMap.addMarker(new MarkerOptions().position(sydneyTest).title("test")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_work_black_24dp));


        //todo remove test
        Geocoder coder = new Geocoder(this);
        try {
            ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName("Rua Marcílio Dias, 524 - Menino Deus, Porto Alegre - RS, Brasil", 50);
            for(Address add : adresses){
                //if (statement) {//Controls to ensure it is right address such as country etc.
                double longitude = add.getLongitude();
                double latitude = add.getLatitude();
                LatLng casa = new LatLng(latitude, longitude);
                Toast.makeText(getApplicationContext(),"Test: "+String.valueOf(longitude),Toast.LENGTH_LONG);
                mMap.addMarker(new MarkerOptions().position(casa).title("CASA"));
                mMap.addCircle(new CircleOptions().center(casa).radius(300).strokeColor(R.color.colorPrimary).strokeWidth(6));
                //}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //end remove test

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {


                Intent intent = new Intent(getApplicationContext(), AdvogadoDetailsActivity.class);
                intent.putExtra("testExtra", marker.getTitle());
                Log.e("Debug1", marker.getTitle());
                startActivity(intent);

                return true;
            }
        });
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,10.0f));
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle connectionHint) {

        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION")
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this,
                    new String[]{"android.permission.ACCESS_FINE_LOCATION"},
                    REQUEST_LOCATION);
        } else {
            // permission has been granted, continue as usual
            mLastLocation = FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                Toast.makeText(getApplicationContext(),"Coordenadas Latitude: "+String.valueOf(mLastLocation.getLatitude()) + " Longitude: "+String.valueOf(mLastLocation.getLongitude()),Toast.LENGTH_SHORT).show();

                myLocation = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
                mMap.addMarker(new MarkerOptions().position(myLocation).title("eu"));
                //mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
                //mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
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
                //   mLastLocation =  FusedLocationApi.getLastLocation(mGoogleApiClient);
                //   if (mLastLocation != null) {
                //       Toast.makeText(getApplicationContext(),"Coordenadas Latitude: "+String.valueOf(mLastLocation.getLatitude()) + " Longitude: "+String.valueOf(mLastLocation.getLongitude()),Toast.LENGTH_SHORT).show();
                //mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
                //mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
                //   }
                Toast.makeText(this,"Permission Granted. ",Toast.LENGTH_SHORT).show();
            } else {
                // Permission was denied or request was cancelled
                Toast.makeText(this,"Permission Denied. ",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


}
