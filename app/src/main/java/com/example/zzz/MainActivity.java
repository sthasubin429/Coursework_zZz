package com.example.zzz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    private EditText signin_email, signin_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signin_email = findViewById(R.id.signin_email_id);
        signin_pass = findViewById(R.id.signin_pass_id);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null  ){
            startActivity(new Intent(getApplicationContext(), dashboard.class));
            finish();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly

    }

    public void signIn(View view) {

        String email = signin_email.getText().toString();
        String pass = signin_pass.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Email is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Password is Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        else{
            fbSignIn(email, pass);

            return;
        }

    }

    public void fbSignIn(String email, String pass){
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Signin Sucessful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, dashboard.class));

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            // ...
                        }
                    }
                });

    }

    public void main_signup(View view) {
        startActivity(new Intent(this, SignIn.class));
    }
}
