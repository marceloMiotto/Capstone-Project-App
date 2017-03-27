package udacitynano.com.br.cafelegal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import udacitynano.com.br.cafelegal.singleton.UserType;
import udacitynano.com.br.cafelegal.util.Constant;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.buttons_aviso_linearLayout)
    LinearLayout buttonsAvisoLinearLayout;

    @BindView(R.id.welcome_button_aviso_perfil)
    Button mButtonAvisoPerfil;


    @BindView(R.id.welcome_button_aviso_perfil_depois)
    Button mButtonAvisoPerfilDepois;

    @BindView(R.id.welcome_aviso_textview)
    TextView mAviso;

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
        UserType userType = UserType.getInstance(this);
        Log.e("Debug","welcome activity user type "+ userType.getAppUserType());

        switch (v.getId()){

            case R.id.welcome_button_sou_advogado:

                buttonsAvisoLinearLayout.setVisibility(View.VISIBLE);
                mAviso.setVisibility(View.VISIBLE);
                editor.putString(getString(R.string.preference_user_type_key), getString(R.string.preference_user_type_advogado));
                editor.commit();
                break;

            case R.id.welcome_button_sou_cliente:

                buttonsAvisoLinearLayout.setVisibility(View.VISIBLE);
                mAviso.setVisibility(View.VISIBLE);
                mAviso.setText(getString(R.string.welcome_cliente_aviso));
                editor.putString(getString(R.string.preference_user_type_key), getString(R.string.preference_user_type_cliente));
                editor.commit();
                break;

            case R.id.welcome_button_aviso_perfil:

                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(Constant.INTENT_FRAGMENT_TYPE,Constant.PERFIL_FRAGMENT);
                startActivity(intent);
                finish();
                break;

            case R.id.welcome_button_aviso_perfil_depois:

                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(Constant.INTENT_FRAGMENT_TYPE,Constant.CONVITE_FRAGMENT);
                startActivity(intent);
                finish();
                break;

            default:

                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(Constant.INTENT_FRAGMENT_TYPE,Constant.CONVITE_FRAGMENT);
                startActivity(intent);
                finish();
                break;
        }

    }
}
