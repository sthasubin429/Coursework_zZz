package com.example.zzz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.cert.CertPathBuilderException;
import java.util.Calendar;

public class dashboard extends AppCompatActivity {
    FirebaseAuth mAuth;
    private DatabaseReference dbRef;
    private TextView user_email, user_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        user_email = findViewById(R.id.dashboard_email);
        user_name = findViewById(R.id.dashboard_name);

        mAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        /**
         * checks if any user is logged in
         * If no user is logged in redirects to sign in page or main activity
         */
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            String name = currentUser.getDisplayName().toString();
            String email = currentUser.getEmail().toString();


            user_name.setText(name);
            user_email.setText(email);
        }
    }

    /**
     * Method to Signout current user
     *
     * @param view
     */
    public void signOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(dashboard.this, "Sign Out Sucessful", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();

    }

    /**
     * Stars create Task Activity
     *
     * @param view
     */
    public void openCreateTask(View view) {
        startActivity(new Intent(getApplicationContext(), CreateTask.class));
    }

    /**
     * Starts all task Activity
     *
     * @param view
     */
    public void openAllTasks(View view) {
        startActivity(new Intent(getApplicationContext(), AllTasks.class));
    }


    /**
     * Starts My task Activity
     *
     * @param view
     */
    public void openMyTask(View view) {
        startActivity(new Intent(getApplicationContext(), MyTasks.class));
    }

    /**
     * Starts history activiy
     *
     * @param view
     */
    public void openHistory(View view) {
        startActivity(new Intent(getApplicationContext(), History.class));
    }
}

