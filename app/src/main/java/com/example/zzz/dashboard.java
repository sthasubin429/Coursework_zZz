package com.example.zzz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
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

import java.util.Calendar;

public class dashboard extends AppCompatActivity {
    FirebaseAuth mAuth;
    private TextView user_email, user_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        user_email = findViewById(R.id.dashboard_email);
        user_name = findViewById(R.id.dashboard_name);

        mAuth = FirebaseAuth.getInstance();



    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null  ){
            startActivity(new Intent(this, MainActivity.class));
        }
        else{
            String name = currentUser.getDisplayName().toString();
            String email = currentUser.getEmail().toString();


            user_name.setText(name);
            user_email.setText(email);
        }
    }

    public void signOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(dashboard.this, "Sign OUT Sucessful", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();


    }

    public void openCreateTask(View view) {
        startActivity(new Intent(getApplicationContext(), CreateTask.class));
    }

    public void openAllTasks(View view) {
        startActivity(new Intent(getApplicationContext(), AllTasks.class));
    }

    public void openMyTask(View view) {
        startActivity(new Intent(getApplicationContext(), MyTasks.class));
    }

    public void openHistory(View view) {
        startActivity(new Intent(getApplicationContext(), History.class));
    }
}
