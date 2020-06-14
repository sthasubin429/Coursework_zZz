package com.example.zzz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyTasks extends AppCompatActivity {
    private ListView lv;
    private DatabaseReference dbRef;
    private List<Task> listTask;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tasks);

        dbRef = FirebaseDatabase.getInstance().getReference();

        lv = findViewById(R.id.my_tasks_lv_id);

        listTask = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String UserInfo = listTask.get(position).getId();


                Intent i =  new Intent(MyTasks.this, ViewActivity.class);
                i.putExtra("currentId", UserInfo);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Query query = dbRef.child("Tasks").orderByChild("assignedTo").equalTo(currentUser.getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot taskSnapshot : dataSnapshot.getChildren()) {
                        Task obj = taskSnapshot.getValue(Task.class);
                        if (obj.getStatus().equals("To Do") || obj.getStatus().equals("In Progress")){
                            listTask.add(obj);
                        }


                    }

                    TaskAdapter adapter = new TaskAdapter(MyTasks.this, listTask);
                    lv.setAdapter(adapter);



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MyTasks.this, "Firebase Connection Failed", Toast.LENGTH_SHORT).show();
            }
        });


    }

}
