package udacitynano.com.br.cafelegal;


import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


public class ChatActivity extends AppCompatActivity implements ChatFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // TODO USAR QUANDO FOR TABLET
        //FragmentManager fm = getFragmentManager();
        //FragmentTransaction fragmentTransaction = fm.beginTransaction();
        //fragmentTransaction.replace(R.id.fragment_chat,ChatFragment.newInstance("",""));
        //fragmentTransaction.addToBackStack(null);
        //fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
