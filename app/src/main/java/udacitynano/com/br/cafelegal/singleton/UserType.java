package udacitynano.com.br.cafelegal.singleton;

import android.content.Context;
import android.content.SharedPreferences;

import udacitynano.com.br.cafelegal.R;

public class UserType {

    private static UserType ourInstance;
    private static String mUserType = "";
    private static Context mContext;

    public static synchronized UserType getInstance(Context context) {

        mContext = context;

        if(ourInstance == null){
            ourInstance = new UserType();
            mUserType = getSharedUserType();
        }
        return ourInstance;
    }

    private UserType() {
    }

    public static boolean isAdvogado(){

        if (mUserType.equals(mContext.getString(R.string.preference_user_type_not_defined))) {
            mUserType = getSharedUserType();
        }

        if (mUserType.equals(mContext.getString(R.string.preference_user_type_cliente)) ||
                mUserType.equals(mContext.getString(R.string.preference_user_type_not_defined))) {
            return false;
        }

        return true;
    }

    public static String getAppUserType(){
        return getSharedUserType();
    }

    private static String getSharedUserType(){

        SharedPreferences sharedPref = mContext.getSharedPreferences(
                mContext.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getString(mContext.getString(R.string.preference_user_type_key),mContext.getString(R.string.preference_user_type_not_defined));

    }
}
