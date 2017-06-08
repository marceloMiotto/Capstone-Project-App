package udacitynano.com.br.cafelegal;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

import udacitynano.com.br.cafelegal.singleton.UserType;
import udacitynano.com.br.cafelegal.util.Constant;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
        , ConviteFragment.OnFragmentInteractionListener
        , HistoricoConvitesFragment.OnFragmentInteractionListener
        , ListaAdvogadosFragment.OnFragmentInteractionListener
        , PerfilFragment.OnFragmentInteractionListener
        , ListaConvitesAbertosFragment.OnFragmentInteractionListener
        , ChatFragment.OnFragmentInteractionListener{

    private boolean mPhone;
    private LinearLayout mLinearLayoutOnePanel;
    private LinearLayout mLinearLayoutTwoPanel;

    @Override
    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if (findViewById(R.id.frame_layout_content_main_container) != null) {
            mPhone = false;
            mLinearLayoutOnePanel = (LinearLayout) findViewById(R.id.one_panel);
            mLinearLayoutTwoPanel = (LinearLayout) findViewById(R.id.two_panel);
            inflateLayout(true);
        } else {
            mPhone = true;
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        UserType userType = UserType.getInstance(this);

        if (UserType.isAdvogado(this)) {
            navigationView.inflateMenu(R.menu.activity_main_advogado_drawer);
        } else {
            navigationView.inflateMenu(R.menu.activity_main_cliente_drawer);
        }

        navigationView.setNavigationItemSelectedListener(this);


        if (mPhone) {
            if (getIntent().getStringExtra(Constant.INTENT_FRAGMENT_TYPE).equals(Constant.PERFIL_FRAGMENT)) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                if (mPhone) {
                    fragmentTransaction.replace(R.id.fragment_menu_switch, PerfilFragment.newInstance());
                } else {
                    inflateLayout(true);
                    fragmentTransaction.replace(R.id.fragment_menu_switch_one_panel, PerfilFragment.newInstance());
                }
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return false;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sign_out) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_convite: {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();

                if (mPhone) {
                    fragmentTransaction.replace(R.id.fragment_menu_switch, ConviteFragment.newInstance());
                } else {
                    inflateLayout(true);
                    fragmentTransaction.replace(R.id.fragment_menu_switch_one_panel, ConviteFragment.newInstance());
                }

                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


                break;
            }
            case R.id.nav_map:

                startActivity(new Intent(this, MapaAdvogadosProximosFragment.class));

                break;
            case R.id.nav_perfil: {

                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                if (mPhone) {
                    fragmentTransaction.replace(R.id.fragment_menu_switch, PerfilFragment.newInstance());
                } else {
                    inflateLayout(true);
                    fragmentTransaction.replace(R.id.fragment_menu_switch_one_panel, PerfilFragment.newInstance());
                }
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            }
            case R.id.nav_historico_convites: {

                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();

                if (mPhone) {
                    fragmentTransaction.replace(R.id.fragment_menu_switch, HistoricoConvitesFragment.newInstance(false));
                } else {
                    inflateLayout(false);
                    fragmentTransaction.replace(R.id.fragment_menu_switch_two_panel, HistoricoConvitesFragment.newInstance(true));
                }

                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            }
            case R.id.nav_adv_list: {//only for clients

                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();

                if (mPhone) {
                    fragmentTransaction.replace(R.id.fragment_menu_switch, ListaAdvogadosFragment.newInstance(false));
                } else {
                    inflateLayout(false);
                    fragmentTransaction.replace(R.id.fragment_menu_switch_two_panel, ListaAdvogadosFragment.newInstance(true));
                }

                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            }
            case R.id.nav_convites_abertos: {//only for lawyers

                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();

                if (mPhone) {
                    fragmentTransaction.replace(R.id.fragment_menu_switch, ListaConvitesAbertosFragment.newInstance(false));
                } else {
                    inflateLayout(false);
                    fragmentTransaction.replace(R.id.fragment_menu_switch_two_panel, ListaConvitesAbertosFragment.newInstance(true));
                }

                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            }
            case R.id.nav_app_sair: //only for lawyers

                FirebaseAuth.getInstance().signOut();
                finish();

                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    private void inflateLayout(boolean onePanel) {



        if (onePanel) {
            mLinearLayoutTwoPanel.setVisibility(View.GONE);
            mLinearLayoutOnePanel.setVisibility(View.VISIBLE);


        } else {
            mLinearLayoutOnePanel.setVisibility(View.GONE);
            mLinearLayoutTwoPanel.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
    }

}
