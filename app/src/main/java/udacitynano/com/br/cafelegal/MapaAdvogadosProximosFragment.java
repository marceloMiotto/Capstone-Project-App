package udacitynano.com.br.cafelegal;


import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import udacitynano.com.br.cafelegal.model.Advogado;
import udacitynano.com.br.cafelegal.singleton.NetworkSingleton;
import udacitynano.com.br.cafelegal.util.Constant;
import static com.google.android.gms.location.LocationServices.FusedLocationApi;


@SuppressWarnings("AccessStaticViaInstance")
public class MapaAdvogadosProximosFragment  extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private List<Advogado> mAdvogadosList;
    private Geocoder mCoder;

    private final int REQUEST_LOCATION = 1;

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


        String apiURL = Constant.SERVER_API_CAFE_LEGAL + Constant.ADVOGADOS;
        mAdvogadosList = new ArrayList<>();
        JsonArrayRequest jsonArrayObject = new JsonArrayRequest
                (Request.Method.GET, apiURL,null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for(int i=0;i<response.length();i++){
                                JSONObject jsonAdvogados = response.getJSONObject(i);
                                mAdvogadosList.add(new Gson().fromJson(jsonAdvogados.toString(),Advogado.class));
                            }


                            if(mCoder != null) {
                                int listPosition = 0;
                                for (Advogado advogado : mAdvogadosList) {
                                    String endereco = advogado.getEndereco() + "," + advogado.getNumero() + " - " + advogado.getBairro() + "," + advogado.getCidade();
                                    String title = String.valueOf(listPosition);

                                    try {
                                        ArrayList<Address> adresses = (ArrayList<Address>) mCoder.getFromLocationName(endereco, 2);
                                        for (Address add : adresses) {
                                            double longitude = add.getLongitude();
                                            double latitude = add.getLatitude();
                                            LatLng escritorio = new LatLng(latitude, longitude);
                                            mMap.addMarker(new MarkerOptions().position(escritorio).title(title)).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_work_black_24dp));
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    listPosition ++;
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                    }
                }) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<>();
                        headers.put(Constant.content_header, Constant.content_application_json);
                        return headers;
                    }
        };

        // Access the RequestQueue through your singleton class.
        NetworkSingleton.getInstance(this).addJSONArrayToRequestQueue(jsonArrayObject);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mCoder = new Geocoder(this);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(Integer.valueOf(marker.getTitle()) >= 0) {
                    Intent intent = new Intent(getApplicationContext(), AdvogadoDetailsActivity.class);
                    intent.putExtra(Constant.ADVOGADO_ESCOLHIDO, mAdvogadosList.get(Integer.valueOf(marker.getTitle())));
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

        if (ActivityCompat.checkSelfPermission(this, this.getString(R.string.permission_access_fine_location))
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this,
                    new String[]{this.getString(R.string.permission_access_fine_location)},
                    REQUEST_LOCATION);
        } else {
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
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getMyPosition();
                } else {
                    // Permission was denied or request was cancelled
                    Toast.makeText(this, this.getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
                }
            }
        }



    @SuppressWarnings({"MissingPermission"})
    private void getMyPosition() {

        Location mLastLocation = FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            LatLng myLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            mMap.addMarker(new MarkerOptions().position(myLocation).title(String.valueOf(-1)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 16.0f));
            mMap.addCircle(new CircleOptions().center(myLocation).radius(500).strokeColor(R.color.colorPrimary).strokeWidth(6));


        }
    }

}

