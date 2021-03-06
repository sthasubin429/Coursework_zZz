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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignIn extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText signup_email, signup_pass, signup_confirmPass, signup_username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        signup_email = findViewById(R.id.signup_email_id);
        signup_pass = findViewById(R.id.signup_pass_id);
        signup_confirmPass = findViewById(R.id.signup_confirmpass_id);
        signup_username = findViewById(R.id.signup_username);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Gets the data input by the user
     * Does Validation on the data entered by the user
     * Creates New user using Firebase Authentication
     * @param view
     */
    public void signUp(View view) {
        //Signup Function

        String email = signup_email.getText().toString().trim();
        String pass = signup_pass.getText().toString().trim();
        String confirm_pass = signup_confirmPass.getText().toString().trim();
        String name = signup_username.getText().toString();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Email is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(name.isEmpty()){
            Toast.makeText(this, "Name is Empty", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "Password must be more than 6 Characters Long.", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (pass.equals(confirm_pass)){
            createAccount(email,pass, name);
        }

        else{
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    /**
     * Function that Requests the Firebase and creates new user
     * Displays appropriate toast based on success or failure to create new user
     * @param email
     * @param pass
     * @param name
     */
    private void createAccount(final String email, final String pass, final String name) {

        //Firebase Signup
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();
                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(SignIn.this, "Sign up Sucess", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(SignIn.this, dashboard.class));
                                                finish();
                                            }
                                        }
                                    });


                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignIn.this, "Sign up Fail, Please use different Email address", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


}

