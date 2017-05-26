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

    public UserHelper() {

        userIconImg.add(R.drawable.user_black_144);
        userIconImg.add(R.drawable.user_blue_144);
        userIconImg.add(R.drawable.user_green_144);
        userIconImg.add(R.drawable.user_red_144);
        userIconImg.add(R.drawable.user_yellow_144);

    }


    public int getRandom(){

        return (int) (Math.random() * 5);

    }


    public int getUserIcon(int i){

        return userIconImg.get(i);

    }
}
