package udacitynano.com.br.cafelegal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{


    @BindView(R.id.welcome_button_concordo)
    Button mButtonConcordo;

    @BindView(R.id.welcome_aviso_advogado_textview)
    TextView mAvisoAdvogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

    }

    @Override
    public void onClick(View v) {

        Intent intent;

        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();

        switch (v.getId()){

            case R.id.welcome_button_sou_advogado:

                mButtonConcordo.setVisibility(View.VISIBLE);
                mAvisoAdvogado.setVisibility(View.VISIBLE);
                editor.putString(getString(R.string.preference_user_type_key), getString(R.string.preference_user_type_advogado));
                editor.commit();
                break;

            case R.id.welcome_button_concordo:
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;

            default: //default is client
                editor.putString(getString(R.string.preference_user_type_key), getString(R.string.preference_user_type_cliente));
                editor.commit();
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
        }

        /* TODO leitura dos shared
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        int defaultValue = getResources().getInteger(R.string.saved_high_score_default);
         long highScore = sharedPref.getInt(getString(R.string.saved_high_score), defaultValue);
         */
    }
}
