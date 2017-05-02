package udacitynano.com.br.cafelegal.singleton;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import udacitynano.com.br.cafelegal.R;

public class UserType {

    private static UserType ourInstance;
    private static String mUserType = "";
    private static Context mContext;
    private static long mUserId;

    public static synchronized UserType getInstance(Context context) {

        mContext = context;

        if(ourInstance == null){
            ourInstance = new UserType();
            mUserType   = getAppUserType();
            mUserId     = getUserId();
        }
        return ourInstance;
    }

    private UserType() {
    }

    public static boolean isAdvogado(){

        if (mUserType.equals(mContext.getString(R.string.preference_user_type_not_defined))) {
            mUserType = getAppUserType();
        }

        if (mUserType.equals(mContext.getString(R.string.preference_user_type_cliente)) ||
                mUserType.equals(mContext.getString(R.string.preference_user_type_not_defined))) {
            return false;
        }

        return true;
    }

    public static String getAppUserType(){

        if (mUserType.equals(mContext.getString(R.string.preference_user_type_not_defined))) {
            mUserType = getSharedUserType();
        }

        return mUserType;
    }

    public static long getUserId(){

        if(mUserId <= 0){
            mUserId = getSharedUserId();
        }

        return mUserId;
    }

    private static String getSharedUserType(){

        SharedPreferences sharedPref = mContext.getSharedPreferences(
                mContext.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getString(mContext.getString(R.string.preference_user_type_key),mContext.getString(R.string.preference_user_type_not_defined));

    }

    private static long getSharedUserId(){

        SharedPreferences sharedPref = mContext.getSharedPreferences(
                mContext.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Log.e("Debug12","preference user type id "+mContext.getString(R.string.preference_user_type_id));
        return sharedPref.getLong(mContext.getString(R.string.preference_user_type_id),-1);

    }

}
