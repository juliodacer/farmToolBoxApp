package com.cydbot.farmtoolbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cydbot.farmtoolbox.activities.client.HomeClientActivity;
import com.cydbot.farmtoolbox.activities.client.RegisterClientActivity;
import com.cydbot.farmtoolbox.activities.supplier.RegisterSupplierActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private ImageView ivPasswordIcon;
    private TextView tvGoToSignUpBtn;
    private boolean passwordShowding = false;
    private boolean buttonPressed = false;

    private RelativeLayout btnSignInWithGoogle;
    SharedPreferences preferences;

    //Firebase
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();

        preferences = getApplication().getSharedPreferences("typeUser", MODE_PRIVATE);

        etEmail = (EditText) findViewById(R.id.et_emailLogin);
        etPassword = (EditText) findViewById(R.id.et_passwordLogin);
        ivPasswordIcon = (ImageView) findViewById(R.id.iv_passwordShow);
        tvGoToSignUpBtn = (TextView) findViewById(R.id.btn_goToSignUp);
        btnSignInWithGoogle = (RelativeLayout) findViewById(R.id.btn_signInWithGoogle);
        ivPasswordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //verificar si la contraseña es visible o no
                if (passwordShowding) {
                    passwordShowding = false;
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ivPasswordIcon.setImageResource(R.drawable.ic_password_hide);
                } else {
                    passwordShowding = true;
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ivPasswordIcon.setImageResource(R.drawable.ic_password_show);
                }

                //Mover el cursor al final del texto
                etPassword.setSelection(etPassword.length());
            }
        });

        tvGoToSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluarUsuario();
            }
        });

        btnSignInWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonPressed){
                    buttonPressed = false;
                    changeButtonProperties(btnSignInWithGoogle, R.drawable.round_google_button);
                }else{
                    buttonPressed = true;
                    changeButtonProperties(btnSignInWithGoogle, R.drawable.round_google_button_pressed);
                }
                signInWithGoogle();
            }
        });
    }
    private void changeButtonProperties(View v1, int resource){
        v1.setBackgroundResource(resource);
    }
    private void evaluarUsuario() {
        String selectedUser = preferences.getString("user", "");
        if (selectedUser.equals("farmer")) {
            Intent intent = new Intent(LoginActivity.this, RegisterClientActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(LoginActivity.this, RegisterSupplierActivity.class);
            startActivity(intent);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    // [END on_start_check_user]

    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }
    // [END auth_with_google]

    // [START signInWithGoogle]
    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]

    private void updateUI(FirebaseUser user) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null) {
            Intent intent = new Intent(LoginActivity.this, HomeClientActivity.class);
            startActivity(intent);
            finish();
        }
    }

}