package com.example.zzz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Archive extends AppCompatActivity {
    private TextView task_id, task_name, task_description, task_priority,task_dueDate, task_createdBy, task_status, task_assignedTo;

    private DatabaseReference dbRef;
    private String id;
    private Task taskObj;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);

        dbRef = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        id = intent.getStringExtra("currentId");

        task_id = findViewById(R.id.archive_id);
        task_name = findViewById(R.id.archive_name);
        task_description = findViewById(R.id.archive_description);
        task_priority = findViewById(R.id.archive_priority);
        task_dueDate = findViewById(R.id.archive_dueDate);
        task_createdBy = findViewById(R.id.archive_createdBy);
        task_status = findViewById(R.id.archive_Status);
        task_assignedTo = findViewById(R.id.archive_assignedTo);

        mAuth = FirebaseAuth.getInstance();



    }

    @Override
    protected void onStart() {
        super.onStart();
        Query query = dbRef.child("Tasks").orderByChild("id").equalTo(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        taskObj = issue.getValue(Task.class);
                        task_id.setText(taskObj.getId());
                        task_name.setText(taskObj.getName());
                        task_description.setText(taskObj.getDescription());
                        task_priority.setText(taskObj.getPriority());
                        task_dueDate.setText(taskObj.getDueDate());
                        task_createdBy.setText(taskObj.getCreatedBy());
                        task_status.setText(taskObj.getStatus());
                        task_assignedTo.setText(taskObj.getAssignedTo());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void goToHistory(View view) {
        startActivity(new Intent(getApplicationContext(), History.class));
    }
}
