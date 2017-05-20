package udacitynano.com.br.cafelegal.singleton;

import android.content.Context;
import android.content.SharedPreferences;

import udacitynano.com.br.cafelegal.R;

public class UserType {

    private static UserType ourInstance;
    private static String mUserType;
    private static long mUserId;

    public static synchronized UserType getInstance(Context context) {

        if(ourInstance == null){
            ourInstance = new UserType();
            mUserId     = getUserId(context);
        }

        mUserType   = getAppUserType(context);

        return ourInstance;
    }

    private UserType() {
    }

    public static boolean isAdvogado(Context context){

        if (mUserType.equals(context.getString(R.string.preference_user_type_not_defined))) {
            mUserType = getAppUserType(context);
        }

        return !(mUserType.equals(context.getString(R.string.preference_user_type_cliente)) ||
                mUserType.equals(context.getString(R.string.preference_user_type_not_defined)));

    }

    public static String getAppUserType(Context context){

        mUserType = getSharedUserType(context);
        return mUserType;
    }

    public static long getUserId(Context context){

        if(mUserId <= 0){
            mUserId = getSharedUserId(context);
        }

        return mUserId;
    }

    private static String getSharedUserType(Context context){

        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getString(context.getString(R.string.preference_user_type_key),context.getString(R.string.preference_user_type_not_defined));

    }

    private static long getSharedUserId(Context context){

        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPref.getLong(context.getString(R.string.preference_user_type_id),-1);

    }

}
