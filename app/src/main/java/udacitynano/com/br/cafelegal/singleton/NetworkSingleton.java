package udacitynano.com.br.cafelegal.singleton;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class NetworkSingleton {
    private static NetworkSingleton ourInstance;
    private static RequestQueue requestQueue;


    public static NetworkSingleton getInstance(Context context) {

        if(ourInstance == null){
            ourInstance = new NetworkSingleton();
            requestQueue = Volley.newRequestQueue(context);
        }

        return ourInstance;
    }

    private NetworkSingleton() {
    }

    public static void addJSONToRequestQueue(JsonObjectRequest jsObjRequest){
        requestQueue.add(jsObjRequest);
    }

    public static void addJSONArrayToRequestQueue(JsonArrayRequest jsonArrayRequest){
        requestQueue.add(jsonArrayRequest);
    }

    public static void addStringRequestQueue(StringRequest request){

        requestQueue.add(request );
    }

}
