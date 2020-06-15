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
        if(mAuth.getCurrentUser() == null  ){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }






    }

    @Override
    public void onStart() {
        super.onStart();
//        Query query = dbRef.child("Notification").orderByChild("notifiedTo").equalTo(mAuth.getCurrentUser().getEmail());
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    for (DataSnapshot issue : dataSnapshot.getChildren())    {
//                        Notification notification = issue.getValue(Notification.class);
//                        String title = notification.getNotifiedBy();
//                        String text = notification.getNotifitedTo() + notification.getStatus();
//                        createNotification(title,text);
//                        createNotification("helloooooo","hiiiiiii");
//
//                    }
//                    createNotification("hello","hiiiiiii");
//
//
//                }
//                else{
//                    createNotification("hello","hi1");
//                    createNotification("hello","hi2");
//                    createNotification("hello","hi3");
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        this.createNotification("Welcome " + mAuth.getCurrentUser().getEmail());
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
        Toast.makeText(dashboard.this, "Sign Out Sucessful", Toast.LENGTH_SHORT).show();
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

    public void createNotification(String title){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("MyNotifications", "MyNotification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "MyNotifications")
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(999, builder.build());


    }
}
