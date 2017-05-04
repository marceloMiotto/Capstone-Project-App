package udacitynano.com.br.cafelegal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import udacitynano.com.br.cafelegal.network.NetworkRequests;
import udacitynano.com.br.cafelegal.singleton.NetworkSingleton;
import udacitynano.com.br.cafelegal.singleton.UserType;
import udacitynano.com.br.cafelegal.util.Constant;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener  {


    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    //
    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Button mButtonSignIn;
    private Button mButtonChangeScreen;
    private boolean mSignIn = true;
    private Context mContext;
    private String mSignInResult = "X";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.editTextEmailId);
        mPasswordView = (EditText) findViewById(R.id.editTextPasswordId);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.buttonSignInId || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mButtonSignIn = (Button) findViewById(R.id.buttonSignInId);
        mButtonChangeScreen = (Button) findViewById(R.id.buttonChangeScreen);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                showProgress(false);
                if (user != null) {
                    if (user != null) {
                        // Name, email address, and profile photo Url
                        String name = user.getDisplayName();
                        String email = user.getEmail();
                        Uri photoUrl = user.getPhotoUrl();

                        // The user's ID, unique to the Firebase project. Do NOT use this value to
                        // authenticate with your backend server, if you have one. Use
                        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
                        mUser.getToken(true)
                                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                                        if (task.isSuccessful()) {
                                            String idToken = task.getResult().getToken();
                                            Log.e("Debug4","User token "+idToken);
                                            // Send token to your backend via HTTPS
                                            // ...
                                            /* TODO remove after tests
                                            try {
                                                sendRegistrationToServer(idToken);
                                            } catch (JSONException e) {
                                                Log.e("Debug4","Notification server exception "+e.getMessage());
                                                e.printStackTrace();
                                            }

                                            Log.e("Debug4","Notification server? ");
                                           */
                                        } else {
                                            String error = task.getException().getMessage();
                                            Log.e("Debug",error);
                                        }
                                    }
                                });
                        String uid = user.getUid();
                        Log.e("Debug4","User id "+uid);
                    }

                    Intent intent;
                    UserType userType = UserType.getInstance(mContext);
                    Log.e("Debug","Login activity user type "+ userType.getAppUserType());
                    //TODO create new user every time just for tests. REMOVE
                    //if(userType.getAppUserType().equals(mContext.getString(R.string.preference_user_type_not_defined))){
                        intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                    //}else{
                    //    intent = new Intent(getApplicationContext(), MainActivity.class);
                    //    intent.putExtra(Constant.INTENT_FRAGMENT_TYPE,Constant.CONVITE_FRAGMENT);
                   // }

                    startActivity(intent);
                    finish();
                    Log.d("Debug3", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("Debug3", "onAuthStateChanged:signed_out");
                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                    mPasswordView.requestFocus();
                }
            }
        };

        mContext = this;
    }

    private void sendRegistrationToServer(String token) throws JSONException {
        // TODO: Implement this method to send token to your app server.
        final JSONObject jsonToken = new JSONObject("{'token':'"+token+"'}");
        Log.e("Debug","jsonConvite "+jsonToken.toString());
        Log.e("Debug","URL "+ Constant.SERVER_API_CAFE_LEGAL + Constant.ADVOGADO+"/"+ UserType.getUserId()+Constant.NOTIFICATION_TOKEN);
        final NetworkRequests networkRequests = new NetworkRequests(this);

       networkRequests.stringRequest(Constant.LOGIN,Request.Method.POST,Constant.SERVER_API_CAFE_LEGAL + Constant.ADVOGADO+"/"+ UserType.getUserId()+Constant.NOTIFICATION_TOKEN,jsonToken,false,null);

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonSignInId:
                Log.e("Debug1","Test button buttonSignInId ");
                attemptLogin();
                break;
            case R.id.buttonChangeScreen:
                Log.e("Debug1","Test button buttonChangeScreen ");
                switchLabels(v);
                break;

        }
    }


    private void switchLabels(View v) {

        Button buttonPressed = (Button) v;
        Log.e("Debug2","Test button text: "+buttonPressed.getText() + getResources().getString(R.string.action_sign_in));

        if(buttonPressed.getText().equals(getResources().getString(R.string.action_sign_in))){
            setTitle(getResources().getText(R.string.title_activity_sign_in));
            mButtonSignIn.setText(getResources().getString(R.string.action_sign_in));
            mButtonChangeScreen.setText(getResources().getString(R.string.action_sign_up));
            mSignIn = true;
        }
        else{
            setTitle(getResources().getText(R.string.title_activity_sign_up));
            mButtonSignIn.setText(getResources().getString(R.string.action_sign_up));
            mButtonChangeScreen.setText(getResources().getString(R.string.action_sign_in));
            mSignIn = false;

        }

    }

    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            authenticationFirebase(email, password);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public void authenticationFirebase(String email, String password) {

        if (mSignIn) {
            //TODO verify the account
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("Debug4", "signInWithEmail:onComplete:" + task.isSuccessful());
                            mSignInResult = "OK";
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Log.w("Debug4", "signInWithEmail:failed", task.getException());
                                Toast.makeText(LoginActivity.this, R.string.auth_failed,
                                        Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
        } else {

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("Debug4", "createUserWithEmail:onComplete:" + task.isSuccessful());
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, R.string.auth_failed,
                                        Toast.LENGTH_SHORT).show();

                            }

                        }
                    });

        }

    }
}

