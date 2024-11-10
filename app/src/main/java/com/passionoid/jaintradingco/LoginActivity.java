package com.passionoid.jaintradingco;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button register_screen, login_start;
    SignInButton googleSignInButton;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private FirebaseAuth mAuth;
    CheckBox checkbox;
    GoogleSignInClient googleSignInClient;
    String TAG = "GOOGLELOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        register_screen = findViewById(R.id.reg);
        login_start = findViewById(R.id.register_btn);
        mEmailView = findViewById(R.id.login_email);
        mPasswordView = findViewById(R.id.m_password);
        checkbox = findViewById(R.id.password_check);
        googleSignInButton = findViewById(R.id.google_login);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 101);
            }
        });


        CheckBox chkBoxRememberMe = findViewById(R.id.remember_me);



        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // show password
                    mPasswordView.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                } else {
                    // hide password
                    mPasswordView.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
            }
        });



        mEmailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmailView.getText().clear();
            }
        });

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.integer.login || id == EditorInfo.IME_NULL) {
                    LoginActivity.this.attemptLogin();
                    return true;
                }
                return false;
            }
        });


        mAuth = FirebaseAuth.getInstance();

        register_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });



    }


    public void ClearTextLogin(View view) {
        switch (view.getId()) {
            case R.id.register_phone:
                mEmailView.getText().clear();
                break;
            case R.id.m_password:
                mPasswordView.getText().clear();
                break;
            default:
                break;
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case 101:
                    try {
                        // The Task returned from this call is always completed, no need to attach
                        // a listener.
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        onLoggedIn(account);
                    } catch (ApiException e) {
                        // The ApiException status code indicates the detailed failure reason.
                        Log.w(TAG, "signInResult:failed code= " + e.getStatusCode());
                    }
                    break;
            }
    }


    private void onLoggedIn(GoogleSignInAccount googleSignInAccount) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }


    public void signInExistingUser(View v) {
        attemptLogin();
    }

    public void registerNewUser(View v) {
        Intent intent = new Intent(this, com.passionoid.jaintradingco.RegisterActivity.class);
        finish();
        startActivity(intent);
    }


    private void attemptLogin() {

        final String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        if (password.equals("") || email.equals("")) return;
        Toast.makeText(this, "Login in Progress", Toast.LENGTH_SHORT).show();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("LIN", "onComplete: " + task.isSuccessful());

                if (!task.isSuccessful()) {
                    Log.d("LIN", "Nope, Not done.  " + task.getException().toString());
                    showErrorDialog("Problem Signing in");
                } else {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        });


    }


    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Oops").setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert).show();
    }


}
