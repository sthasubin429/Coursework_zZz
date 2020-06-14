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

import org.w3c.dom.Text;

import java.security.cert.TrustAnchor;
import java.util.Calendar;

public class ViewActivity extends AppCompatActivity {
    private TextView task_id, task_name, task_description, task_priority, task_createdBy, task_CurrentStatus, task_assignedTo;
    private EditText task_dueDate;
    private Button task_doThis;
    private Spinner task_changeStatus;

    private DatabaseReference dbRef;
    private String id;
    private Task taskObj;
    private FirebaseAuth mAuth;
    DatePickerDialog datePiker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        dbRef = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        id = intent.getStringExtra("currentId");

        task_id = findViewById(R.id.view_task_id);
        task_name = findViewById(R.id.view_task_name);
        task_description = findViewById(R.id.view_task_description);
        task_priority = findViewById(R.id.view_task_priority);
        task_dueDate = findViewById(R.id.view_task_dueDate);
        task_createdBy = findViewById(R.id.view_task_createdBy);
        task_CurrentStatus = findViewById(R.id.view_task_currentStatus);
        task_assignedTo = findViewById(R.id.view_assignedTo);
        task_doThis = findViewById(R.id.view_btn_doTask);
        task_changeStatus = findViewById(R.id.view_change_status);
        task_changeStatus.setEnabled(false);

        mAuth = FirebaseAuth.getInstance();


        task_dueDate.setInputType(InputType.TYPE_NULL);
        task_dueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calender = Calendar.getInstance();
                int day = calender.get(Calendar.DAY_OF_MONTH);
                int month = calender.get(Calendar.MONTH);
                int year = calender.get(Calendar.YEAR);
                // date picker dialog
                datePiker = new DatePickerDialog(ViewActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                task_dueDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                            }
                        }, year, month, day);
                datePiker.show();
            }
        });

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
                        task_CurrentStatus.setText(taskObj.getStatus());
                        task_assignedTo.setText(taskObj.getAssignedTo());
                        if(taskObj.getAssignedTo().equals("None")){
                            task_doThis.setEnabled(true);
                        }

                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    public void doThis(View view) {
        taskObj.setAssignedTo(mAuth.getCurrentUser().getEmail());
        dbRef.child("Tasks").child(id).setValue(taskObj);
        startActivity(new Intent(ViewActivity.this, AllTasks.class));
    }

    public void updateTask(View view) {
        String dueDate = task_dueDate.getText().toString();
        taskObj.setDueDate(dueDate);

        if(task_changeStatus.isEnabled()){
            String status = task_changeStatus.getSelectedItem().toString();
            taskObj.setStatus(status);
        }

        dbRef.child("Tasks").child(id).setValue(taskObj);
        startActivity(new Intent(ViewActivity.this, AllTasks.class));
    }

    public void view_btn_changeStatus(View view) {
        task_changeStatus.setEnabled(true);
    }
}
