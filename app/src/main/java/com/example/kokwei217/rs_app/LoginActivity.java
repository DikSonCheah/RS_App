package com.example.kokwei217.rs_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout layoutUsername_Login;
    private TextInputLayout layoutPassword_Login;
    private EditText username_ET;
    private EditText password_ET;
    private TextView createAccount_TV;
    private Button loginBtn;

    private ProgressBar progressBar_Login;

    private FirebaseAuth firebaseAuth;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            checkForEmptyFields();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        layoutUsername_Login = findViewById(R.id.LoginUsernameLayout_ET);
        layoutPassword_Login = findViewById(R.id.LoginPasswordLayout_ET);
        username_ET = findViewById(R.id.LoginUsername_ET);
        password_ET = findViewById(R.id.LoginPassword_ET);
        progressBar_Login = findViewById(R.id.Login_ProgressBar);

        createAccount_TV = findViewById(R.id.CreateAccount_txt);
        loginBtn = findViewById(R.id.Login_btn);

        firebaseAuth = FirebaseAuth.getInstance();

        createAccount_TV.setOnClickListener(this);
        loginBtn.setOnClickListener(this);

        username_ET.setText("kokwei217@hotmail.com");
        password_ET.setText("xopalx217");
        username_ET.addTextChangedListener(textWatcher);
        password_ET.addTextChangedListener(textWatcher);
        checkForEmptyFields();
    }

    private void LoginUser() {
        final String username = username_ET.getText().toString().trim();
        final String password = password_ET.getText().toString().trim();

        layoutUsername_Login.setError(null);

        //Validate Whether the email is real or not
        if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            layoutUsername_Login.setError("Valid Email is Required");
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Logging In...");
        progressDialog.show();

//        progressBar_Login.setVisibility(View.VISIBLE);

        firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
//                progressBar_Login.setVisibility(View.GONE);
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Intent main = new Intent(getApplicationContext(), MainActivity.class);
//                    removes previous activity so that when user press back it doesnt loop back into login
                    main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(main);
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkForEmptyFields() {
        if (username_ET.getText().toString().equals("") || password_ET.getText().toString().equals("")) {
            loginBtn.setEnabled(false);
        } else {
            loginBtn.setEnabled(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Login_btn:
                LoginUser();
                break;

            case R.id.CreateAccount_txt:
                Intent SignUp = new Intent(this, SignUpActivity.class);
                startActivity(SignUp);
                break;

        }
    }
}
