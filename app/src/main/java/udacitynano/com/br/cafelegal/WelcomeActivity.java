package udacitynano.com.br.cafelegal;

import android.app.DownloadManager;
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

import com.android.volley.Request;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import udacitynano.com.br.cafelegal.model.Advogado;
import udacitynano.com.br.cafelegal.model.Cliente;
import udacitynano.com.br.cafelegal.model.Convite;
import udacitynano.com.br.cafelegal.model.Pessoa;
import udacitynano.com.br.cafelegal.network.NetworkRequests;
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

    NetworkRequests mNetworkRequests;

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
        SharedPreferences.Editor editor;
        String email = sharedPref.getString(this.getString(R.string.preference_user_firebase_email),"");
        String fcmToken = sharedPref.getString(this.getString(R.string.preference_user_firebase_token),"");

        switch (v.getId()){

            case R.id.welcome_button_sou_advogado:

                buttonsAvisoLinearLayout.setVisibility(View.VISIBLE);
                mAviso.setVisibility(View.VISIBLE);
                //Create user
                mNetworkRequests = new NetworkRequests(this);
                final Advogado advogado = new Advogado(0,"","","", email,0,"","","","","","","","","","","","",""
                        ,"","","",fcmToken);
                advogado.setEmail(sharedPref.getString(getString(R.string.preference_user_firebase_email),""));
                editor = sharedPref.edit();
                editor.putString(this.getString(R.string.preference_user_type_key), this.getString(R.string.preference_user_type_advogado));
                editor.commit();
                JSONObject jsonAdvogado = null;
                try {
                    jsonAdvogado = new JSONObject(new Gson().toJson(advogado));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mNetworkRequests.jsonRequest(Constant.PERFIL, Request.Method.POST,Constant.SERVER_API_CAFE_LEGAL+Constant.ADVOGADO,jsonAdvogado,false);
                break;

            case R.id.welcome_button_sou_cliente:

                buttonsAvisoLinearLayout.setVisibility(View.VISIBLE);
                mAviso.setVisibility(View.VISIBLE);
                mAviso.setText(getString(R.string.welcome_cliente_aviso));
                editor = sharedPref.edit();
                editor.putString(this.getString(R.string.preference_user_type_key), this.getString(R.string.preference_user_type_cliente));
                editor.commit();

                //Create user
                final Cliente cliente = new Cliente(0,"","","",email,0,"","","","","","","","",fcmToken);

                JSONObject jsonCliente = null;
                try {
                    jsonCliente = new JSONObject(new Gson().toJson(cliente));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mNetworkRequests.jsonRequest(Constant.PERFIL, Request.Method.POST,Constant.SERVER_API_CAFE_LEGAL+Constant.CLIENTE,jsonCliente,false);

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
