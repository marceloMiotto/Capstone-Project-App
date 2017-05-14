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
    Geocoder mCoder;

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


                            if(mCoder != null) {
                                int listPosition = 0;
                                for (Advogado advogado : mAdvogadosList) {
                                    String endereco = advogado.getEndereco() + "," + advogado.getNumero() + " - " + advogado.getBairro() + "," + advogado.getCidade();
                                    String title = String.valueOf(listPosition);
                                    Log.e("Debug23", "endereco " + endereco);
                                    Log.e("Debug23", "title " + title);

                                    try {
                                        ArrayList<Address> adresses = (ArrayList<Address>) mCoder.getFromLocationName(endereco, 2);
                                        for (Address add : adresses) {
                                            //if (statement) {//Controls to ensure it is right address such as country etc.
                                            double longitude = add.getLongitude();
                                            double latitude = add.getLatitude();
                                            LatLng escritorio = new LatLng(latitude, longitude);
                                            mMap.addMarker(new MarkerOptions().position(escritorio).title(title)).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_work_black_24dp));
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    Log.e("Debug23", "listPosition " + listPosition);
                                    listPosition ++;
                                    Log.e("Debug23", "listPosition " + listPosition);

                                }
                            }
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


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.e("Debug23","map is ready");

        mCoder = new Geocoder(this);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //-1 REPRESENTA O MEU MARCADOR
                if(Integer.valueOf(marker.getTitle()) >= 0) {
                    Intent intent = new Intent(getApplicationContext(), AdvogadoDetailsActivity.class);
                    intent.putExtra("ADVOGADO_SELECIOANDO", mAdvogadosList.get(Integer.valueOf(marker.getTitle())));
                    Log.e("Debug1", marker.getTitle());
                    startActivity(intent);

                }

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

            getMyPosition();
        }
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getMyPosition();

                    Toast.makeText(this, "Permission Granted. ", Toast.LENGTH_SHORT).show();
                } else {
                    // Permission was denied or request was cancelled
                    Toast.makeText(this, "Permission Denied. ", Toast.LENGTH_SHORT).show();
                }
            }
        }



    @SuppressWarnings({"MissingPermission"})
    private void getMyPosition() {

        mLastLocation = FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            myLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            mMap.addMarker(new MarkerOptions().position(myLocation).title(String.valueOf(-1)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 16.0f));
            mMap.addCircle(new CircleOptions().center(myLocation).radius(500).strokeColor(R.color.colorPrimary).strokeWidth(6));
            mLastLocation = FusedLocationApi.getLastLocation(mGoogleApiClient);

        }
    }

}

