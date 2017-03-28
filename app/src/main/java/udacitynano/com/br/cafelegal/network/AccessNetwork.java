package udacitynano.com.br.cafelegal.network;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class AccessNetwork {

    Context mContext;

    AccessNetwork(Context context){
        mContext = context;
    };

    private boolean networkUp() {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }


}
