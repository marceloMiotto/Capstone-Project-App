package udacitynano.com.br.cafelegal.util;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import udacitynano.com.br.cafelegal.R;

public class UserHelper {


    List<Integer> userIconImg = new ArrayList<>();

    public UserHelper(Context context) {

        userIconImg.add(R.drawable.user_black_144);
        userIconImg.add(R.drawable.user_blue_144);
        userIconImg.add(R.drawable.user_green_144);
        userIconImg.add(R.drawable.user_red_144);
        userIconImg.add(R.drawable.user_yellow_144);

    }


    private int getRandom(){
        int min = 0;
        int max = 4;

        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;
        Log.e("Debug","Random "+i1);
        return i1;
    }


    public Integer getRandomUserIcon(){

      return userIconImg.get(getRandom());

    }


    public int getUserIcon(int i){

        return userIconImg.get(i);

    }
}
