package udacitynano.com.br.cafelegal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.iid.FirebaseInstanceId;
import udacitynano.com.br.cafelegal.singleton.UserType;


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
    private String mFirebaseEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(this.getString(R.string.login_activity_title));
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
                    mFirebaseEmail = user.getEmail();

                    FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
                    mUser.getToken(true)
                            .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                public void onComplete(@NonNull Task<GetTokenResult> task) {
                                    if (task.isSuccessful()) {
                                        String idToken = task.getResult().getToken();

                                        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

                                        SharedPreferences sharedPref = getSharedPreferences(
                                                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPref.edit();
                                        editor.putString(getString(R.string.preference_user_firebase_token), refreshedToken);
                                        editor.putString(getString(R.string.preference_user_firebase_email),mFirebaseEmail);

                                        editor.apply();

                                    } else {
                                        Log.e("Debug","Erro");
                                    }
                                }
                            });
                    String uid = user.getUid();

                    Intent intent;
                    UserType userType = UserType.getInstance(mContext);

                    //if(userType.getAppUserType().equals(mContext.getString(R.string.preference_user_type_not_defined))){
                        intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                    //}else{
                    //    intent = new Intent(getApplicationContext(), MainActivity.class);
                    //    intent.putExtra(Constant.INTENT_FRAGMENT_TYPE,Constant.CONVITE_FRAGMENT);
                    //}

                    startActivity(intent);
                    finish();
                } else {
                    // User is signed out
                    mEmailView.setError(getString(R.string.login_informe_email));
                    mEmailView.requestFocus();
                }
            }
        };

        mContext = this;
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
                attemptLogin();
                break;
            case R.id.buttonChangeScreen:
                switchLabels(v);
                break;

        }
    }


    private void switchLabels(View v) {

        Button buttonPressed = (Button) v;

        if(buttonPressed.getText().equals(getResources().getString(R.string.action_sign_in))){
            setTitle(getResources().getText(R.string.app_name) + " - " +getResources().getText(R.string.title_activity_sign_in));
            mButtonSignIn.setText(getResources().getString(R.string.action_sign_in));
            mButtonChangeScreen.setText(getResources().getString(R.string.action_sign_up));
            mSignIn = true;
        }
        else{
            setTitle(getResources().getText(R.string.app_name) + " - " +getResources().getText(R.string.title_activity_sign_up));
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


        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
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
    }

    private void authenticationFirebase(String email, String password) {

        if (mSignIn) {
            //TODO verify the account
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("Debug4", "signInWithEmail:onComplete:" + task.isSuccessful());

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

