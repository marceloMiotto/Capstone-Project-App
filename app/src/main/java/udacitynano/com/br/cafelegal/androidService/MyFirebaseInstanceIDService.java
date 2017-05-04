package udacitynano.com.br.cafelegal.androidService;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import udacitynano.com.br.cafelegal.network.NetworkRequests;
import udacitynano.com.br.cafelegal.singleton.NetworkSingleton;
import udacitynano.com.br.cafelegal.singleton.UserType;
import udacitynano.com.br.cafelegal.util.Constant;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private Context mContext;
    private static final String TAG = "MyFirebaseIIDService";

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
        Log.d(TAG, "c token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        try {
            sendRegistrationToServer(refreshedToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        // TODO: Implement this method to send token to your app server.
        final JSONObject jsonToken = new JSONObject(new Gson().toJson("{'token':'"+token+"'}"));
        Log.e("Debug","jsonConvite "+jsonToken.toString());
        Log.e("Debug79","URL "+ Constant.SERVER_API_CAFE_LEGAL + Constant.ADVOGADO+"/"+ UserType.getUserId()+Constant.NOTIFICATION_TOKEN);

        final NetworkRequests networkRequests = new NetworkRequests(this);

        networkRequests.stringRequest(Constant.ANDROID_SERVICE,Request.Method.POST,Constant.SERVER_API_CAFE_LEGAL + Constant.ADVOGADO+"/"+ UserType.getUserId()+Constant.NOTIFICATION_TOKEN,jsonToken,false,null);

    }
}
