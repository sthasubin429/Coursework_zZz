package com.example.zzz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText signup_email, signup_pass, signup_confirmPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        signup_email = findViewById(R.id.signup_email_id);
        signup_pass = findViewById(R.id.signup_pass_id);
        signup_confirmPass = findViewById(R.id.signup_confirmpass_id);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    public void signUp(View view) {
        //Signup Function

        String email = signup_email.getText().toString().trim();
        String pass = signup_pass.getText().toString().trim();
        String confirm_pass = signup_confirmPass.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Email is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Password is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(confirm_pass)){
            Toast.makeText(this, "Confirm password is Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        else if (pass.length() < 6){
            Toast.makeText(this, "Password to Short", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (pass.equals(confirm_pass)){
            createAccount(email,pass);
        }
        else{
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    private void createAccount(String email, String pass) {

        //Firebase Signup
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(SignIn.this, "Sign up Sucess", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignIn.this, dashboard.class));

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignIn.this, "Sign up Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}

