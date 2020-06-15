package com.example.zzz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
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

        /**
         * Checks if any user is logged in or not redirects to dasahboard if user is already logged in
         */
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


    /**
     * Gets the value Enterd by user
     * Does basic validation on it
     * Authenticates using Firebase Authentication.
     * @param view
     */
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

    /**
     * Takes email and password
     * Authenticates user using firebase authenticates
     * Displays appropriate message on success or failure
     * @param email
     * @param pass
     */

    public void fbSignIn(String email, String pass){
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            createNotification("Welcome to zZz, " + user.getDisplayName());
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

    /**
     * Starts Sign up Activity
     * @param view
     */
    public void main_signup(View view) {
        startActivity(new Intent(this, SignIn.class));
    }

    /**
     * Method to create notification
     * Notification is created every time user logs in.
     * @param title
     */
    public void createNotification(String title){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("MyNotifications", "MyNotifications", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "MyNotifications")
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setAutoCancel(true);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(999, builder.build());


    }


}
