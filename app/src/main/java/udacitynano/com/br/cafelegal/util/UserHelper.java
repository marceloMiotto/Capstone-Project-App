package udacitynano.com.br.cafelegal.util;


import java.util.ArrayList;
import java.util.List;

import udacitynano.com.br.cafelegal.R;

public class UserHelper {


    private final List<Integer> userIconImg = new ArrayList<>();

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
