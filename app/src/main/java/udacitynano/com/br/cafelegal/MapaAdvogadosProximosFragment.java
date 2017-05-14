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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import udacitynano.com.br.cafelegal.model.Advogado;
import udacitynano.com.br.cafelegal.service.PerfilService;
import udacitynano.com.br.cafelegal.singleton.NetworkSingleton;
import udacitynano.com.br.cafelegal.util.Constant;

import static com.google.android.gms.location.LocationServices.FusedLocationApi;


public class MapaAdvogadosProximosFragment  extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    LatLng myLocation;
    List<Advogado> mAdvogadosList;

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

        //TODO VERIFY INTERNET
        String apiURL = Constant.SERVER_API_CAFE_LEGAL + Constant.ADVOGADOS;
        Log.e("Debug2", " aceito url: " + apiURL);
        //TODO INFORMAR A LATITUDE E LONGITUDE CORRETAS
        mAdvogadosList = new ArrayList<>();
        JsonArrayRequest jsonArrayObject = new JsonArrayRequest
                (Request.Method.GET, apiURL,null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            Log.e("Debug13",response.toString());
                            for(int i=0;i<response.length();i++){
                                JSONObject jsonAdvogados = response.getJSONObject(i);
                                mAdvogadosList.add(new Gson().fromJson(jsonAdvogados.toString(),Advogado.class));
                                Log.e("Debug13",jsonAdvogados.toString());
                            }

                            //mAdapter.notifyDataSetChanged();
                            //TODO REFRESH MAP
                           // if(mMap != null){ //prevent crashing if the map doesn't exist yet (eg. on starting activity)
                           //     mMap.clear();

                                // add markers from database to the map
                            //}
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
        NetworkSingleton.getInstance(this).addJSONArrayToRequestQueue(jsonArrayObject);


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
        //LatLng sydney = new LatLng(-30.06329997, -51.1712265);
       // LatLng sydneyTest = new LatLng(-30.06285427, -51.16281509);

       // mMap.addMarker(new MarkerOptions().position(sydney).title("Puc")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_work_black_24dp));
       // mMap.addMarker(new MarkerOptions().position(sydneyTest).title("test")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_work_black_24dp));
        Log.e("Debug23","map is ready");

        //todo remove test
        //TODO TEST
        //while (mAdvogadosList.isEmpty()) {
            Geocoder coder = new Geocoder(this);
            for (Advogado advogado : mAdvogadosList) {
                String endereco = advogado.getEndereco() + "," + advogado.getNumero() + " - " + advogado.getBairro() + "," + advogado.getCidade();
                Log.e("Debug23", "endereco " + endereco);
                try {
                    ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(endereco, 2);
                    for (Address add : adresses) {
                        //if (statement) {//Controls to ensure it is right address such as country etc.
                        double longitude = add.getLongitude();
                        double latitude = add.getLatitude();
                        LatLng escritorio = new LatLng(latitude, longitude);
                        mMap.addMarker(new MarkerOptions().position(escritorio).title(advogado.getNome() + " - " + "OAB: " + advogado.getNumeroInscricaoOAB())).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_work_black_24dp));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        //}

        //end remove test

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                //TODO SEARCH FOR LAWYER BY NAME
                Intent intent = new Intent(getApplicationContext(), AdvogadoDetailsActivity.class);
                intent.putExtra("testExtra", marker.getTitle());
                Log.e("Debug1", marker.getTitle());
                startActivity(intent);

                return true;
            }
        });

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
                myLocation = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
                mMap.addMarker(new MarkerOptions().position(myLocation).title("eu"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation,13.0f));
                mMap.addCircle(new CircleOptions().center(myLocation).radius(500).strokeColor(R.color.colorPrimary).strokeWidth(6));

            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //permission result
    @SuppressWarnings({"MissingPermission"})
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                mLastLocation = FusedLocationApi.getLastLocation(mGoogleApiClient);
                if (mLastLocation != null) {
                    myLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(myLocation).title("eu"));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation,13.0f));
                    mMap.addCircle(new CircleOptions().center(myLocation).radius(500).strokeColor(R.color.colorPrimary).strokeWidth(6));

                    Toast.makeText(this, "Permission Granted. ", Toast.LENGTH_SHORT).show();
                } else {
                    // Permission was denied or request was cancelled
                    Toast.makeText(this, "Permission Denied. ", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }


}
