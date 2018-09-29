package com.zajel.zajelandroid.Login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.gson.JsonElement;
import com.zajel.zajelandroid.APIManager.APIManager;
import com.zajel.zajelandroid.PreferenceManager;
import com.zajel.zajelandroid.R;
import com.zajel.zajelandroid.SignUp.ActivitySignUp;
import com.zajel.zajelandroid.SignUp.Models.SignUpRespnseBody;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Headers;

import static com.zajel.zajelandroid.Utils.ValidateUtil.validateEmail;
import static com.zajel.zajelandroid.Utils.ValidateUtil.validatePassword;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
        , APIManager.LogInResponse
        , APIManager.GoogleLogInResponse {
    private static int RC_SIGN_IN = 222;


    @BindView(R.id.signup_TextView)
    TextView signUpTextView;
    @BindView(R.id.forgot_password_TextView)
    TextView forgotPasswordTextView;
    @BindView(R.id.email_EditText)
    EditText emailEditText;
    @BindView(R.id.password_EditText)
    EditText passwordEditText;
    @BindView(R.id.email_TextInputLayout)
    TextInputLayout emailTextInputLayout;
    @BindView(R.id.password_TextInputLayout)
    TextInputLayout passwordTextInputLayout;
    @BindView(R.id.facebook_ImageView)
    ImageView facebookImageView;
    @BindView(R.id.google_ImageView)
    ImageView googleImageView;
    @BindView(R.id.login_Button)
    Button loginButton;
    @BindView(R.id.sign_in_button)
    SignInButton googleSignInButton;


    String emailString, passwordString;
    PreferenceManager preferenceManager;
    APIManager apiManager;
    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        mAuth = FirebaseAuth.getInstance();
        googleSignInButton.setOnClickListener(this);
        forgotPasswordTextView.setOnClickListener(this);
        signUpTextView.setOnClickListener(this);
        facebookImageView.setOnClickListener(this);
        googleImageView.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        emailEditText.addTextChangedListener(new MyTextWatcher(emailEditText));
        passwordEditText.addTextChangedListener(new MyTextWatcher(passwordEditText));
        apiManager = new APIManager(getApplicationContext());
        apiManager.setLoginResponse(this);
        apiManager.setGoogleLogInResponse(this);
        preferenceManager = PreferenceManager.getInstance();


        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("548369515861-gvbeetdi103gpi40ia102tvbb8lajj4c.apps.googleusercontent.com")
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


    }

    private void submitForm(String email, String pass) {
        if (!validateEmail(emailEditText, emailTextInputLayout, LoginActivity.this)) {
            return;
        }

        if (!validatePassword(passwordEditText, passwordTextInputLayout, LoginActivity.this)) {
            return;
        }
        try {

            LogInRequestBody logInRequestBody = new LogInRequestBody(email, pass);
            apiManager.logIn(logInRequestBody);
        } catch (NullPointerException e) {

        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.login_Button:
                emailString = emailEditText.getText().toString();
                passwordString = passwordEditText.getText().toString();
                submitForm(emailString, passwordString);

                break;

            case R.id.facebook_ImageView:
                Log.e("clicked", "click face");
                break;

            case R.id.google_ImageView:
                Log.e("clicked", "click");
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);

//                googleSignInButton.callOnClick();
//                googleSignInButton.performClick();
//                facebookImageView.performClick();

//                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//                startActivityForResult(signInIntent, RC_SIGN_IN);

//                apiManager.googleLogIn();
                break;

            case R.id.signup_TextView:
                Intent i = new Intent(getBaseContext(), ActivitySignUp.class);
                startActivity(i);
                finish();

                break;

            case R.id.forgot_password_TextView:

                break;

            case R.id.sign_in_button:
                Log.e("clicked", "click sign");
//                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//                startActivityForResult(signInIntent, RC_SIGN_IN);
                break;

        }
    }

    @Override
    public void getLoginResponse(SignUpRespnseBody signUpRespnseBody) {

    }

    @Override
    public void getLoginHeaders(Headers headers) {
        preferenceManager.setAccessToken(headers.get("Access-Token"));
        preferenceManager.setClient(headers.get("Client"));
        preferenceManager.setExpiry(headers.get("Expiry"));
        preferenceManager.setUid(headers.get("Uid"));
        preferenceManager.setTokenType(headers.get("Token-Type"));

    }

    @Override
    public void getLoginResponse(JsonElement jsonElement) {
        Log.e("test", "res");
//        Log.e("test",jsonElement.toString());
    }

    @Override
    public void errorOccureLogin() {
        Log.e("test", "error");

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {

        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.e("test", account.getId());
            firebaseAuthWithGoogle(account);
            // Signed in successfully, show authenticated UI.
//            updateUI(account);
        } catch (ApiException e) {
            Log.e("tesssst", e.getLocalizedMessage());
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
        }
    }

    FirebaseUser user ;
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.e("sdsds", "firebaseAuthWithGoogle:" + acct.getIdToken());
        final Long starttime= System.currentTimeMillis();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e("sdsds", "signInWithCredential:success");
                            final FirebaseUser user = mAuth.getCurrentUser();


//                            mAuth.getCurrentUser().getIdToken(false).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
//                                @Override
//                                public void onSuccess(GetTokenResult getTokenResult) {
//                                    long time = System.currentTimeMillis() - starttime;
//
//                                    Log.e("testt", "time " + time + ", token " + getTokenResult.getToken());
//                                }
//                            });
                            Log.e("userrrrrr", user.getIdToken(false).getResult().getToken());

//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e("sdsds", "signInWithCredential:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication Failed.", Toast.LENGTH_LONG).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.email_EditText:
                    validateEmail(emailEditText, emailTextInputLayout, LoginActivity.this);
                    break;
                case R.id.password_EditText:
                    validatePassword(passwordEditText, passwordTextInputLayout, LoginActivity.this);
                    break;
            }
        }

    }


}
