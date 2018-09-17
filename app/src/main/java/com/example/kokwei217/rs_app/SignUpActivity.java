package com.example.kokwei217.rs_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    TextInputLayout LayoutUsername_Signup;
    EditText Username_SignUp;
    EditText Password_SignUp;
    TextInputLayout LayoutPassword_Signup;
    ProgressBar ProgressBar_SignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        LayoutUsername_Signup = findViewById(R.id.SignupUsernameLayout_ET);
        Username_SignUp = findViewById(R.id.SignupUsername_ET);
        LayoutPassword_Signup = findViewById(R.id.SignupPasswordLayout_ET);
        Password_SignUp = findViewById(R.id.SignupPassword_ET);
        ProgressBar_SignUp = findViewById(R.id.Signup_ProgressBar);

        firebaseAuth = FirebaseAuth.getInstance();

        findViewById(R.id.Haveanaccount_txt).setOnClickListener(this);
        findViewById(R.id.Signup_btn).setOnClickListener(this);
    }

    public void RegisterUser() {
        //trim() removes spacing from both sides
        String Username = Username_SignUp.getText().toString().trim();
        String Password = Password_SignUp.getText().toString().trim();

        LayoutPassword_Signup.setError(null);
        LayoutUsername_Signup.setError(null);

        if (Username.isEmpty()) {
            LayoutUsername_Signup.setError("Please enter an email");
            return;
        }

        if (Password.isEmpty()) {
            LayoutPassword_Signup.setError("Please enter password");
            return;
        }

        //Validate Whether the email is real or not
        if (!Patterns.EMAIL_ADDRESS.matcher(Username).matches()) {
            LayoutUsername_Signup.setError("Valid Email is Required");
            return;
        }

        if (Password.length() < 6) {
            LayoutPassword_Signup.setError("Minimum password length should be six");
            return;
        }

        ProgressBar_SignUp.setVisibility(View.VISIBLE);

        firebaseAuth.createUserWithEmailAndPassword(Username, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                ProgressBar_SignUp.setVisibility(View.GONE);

                if (task.isSuccessful()) {

                    firebaseUser = firebaseAuth.getCurrentUser();
                    firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {

                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                //if sending the email verification is successful open the alert dialog
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                                builder.setTitle("Verification")
                                        .setMessage("A verification email has been sent to your email, please verify email in order to login")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                                                loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(loginIntent);
                                            }

                                        });
                                builder.show();


                            } else {
                                //if fail to send email verification get the error message
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "This email is already registered", Toast.LENGTH_SHORT).show();

                    } else {
                        //shows whats the error message that preventing signing up
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Signup_btn:
                RegisterUser();
                break;


            case R.id.Haveanaccount_txt:
                Intent Login = new Intent(this, LoginActivity.class);
                Login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(Login);
                break;
        }

    }

}