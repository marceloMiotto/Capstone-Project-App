package udacitynano.com.br.cafelegal.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import udacitynano.com.br.cafelegal.R;
import udacitynano.com.br.cafelegal.network.NetworkRequests;
import udacitynano.com.br.cafelegal.singleton.NetworkSingleton;
import udacitynano.com.br.cafelegal.singleton.UserType;
import udacitynano.com.br.cafelegal.util.Constant;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private Context mContext;
    private static final String TAG = "MyFirebaseIIDService";
    private static final String FRIENDLY_ENGAGE_TOPIC = "friendly_engage"; //TODO CHAT TEST

    public MyFirebaseInstanceIDService(){}

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("Debug5", "c token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        try {
            sendRegistrationToServer(refreshedToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //TODO CHAT TEST
        // Once a token is generated, we subscribe to topic.
        FirebaseMessaging.getInstance().subscribeToTopic(FRIENDLY_ENGAGE_TOPIC);

    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) throws JSONException {

        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.preference_user_firebase_token), token);

        editor.commit();

        // TODO: Implement this method to send token to your app server.
        final JSONObject jsonToken = new JSONObject(new Gson().toJson("{\"token\":\""+token+"\"}"));
        Log.e("Debug","jsonConvite "+jsonToken.toString());
        Log.e("Debug79","URL "+ Constant.SERVER_API_CAFE_LEGAL + Constant.ADVOGADO+"/"+ UserType.getUserId()+Constant.NOTIFICATION_TOKEN);

        final NetworkRequests networkRequests = new NetworkRequests(this);

        networkRequests.stringRequest(Constant.ANDROID_SERVICE,Request.Method.POST,Constant.SERVER_API_CAFE_LEGAL + Constant.ADVOGADO+"/"+ UserType.getUserId()+Constant.NOTIFICATION_TOKEN,jsonToken,false,null);

    }
}
