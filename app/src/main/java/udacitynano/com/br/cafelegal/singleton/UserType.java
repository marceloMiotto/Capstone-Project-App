package udacitynano.com.br.cafelegal.singleton;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import udacitynano.com.br.cafelegal.R;

public class UserType {

    private static UserType ourInstance;
    private static String mUserType = "";
    private static Context mContext;
    private static boolean isAdvogado = false;

    public static synchronized UserType getInstance(Context context) {

        if(ourInstance == null){
            ourInstance = new UserType();
        }
        return ourInstance;
    }

    private UserType() {
    }

    public static boolean isAdvogado(){

        if(mUserType.equals("")){
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);
            String userType = sharedPref.getString(mContext.getString(R.string.preference_user_type_key),mContext.getString(R.string.preference_user_type_cliente));
            if(userType.equals(mContext.getString(R.string.preference_user_type_cliente))){
                isAdvogado = false;
            }
            else{
                isAdvogado = true;
            }
        }
        return isAdvogado;
    }

}
