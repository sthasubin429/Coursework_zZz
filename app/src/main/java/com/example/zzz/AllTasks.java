package com.example.zzz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllTasks extends AppCompatActivity {
    private ListView lv;
    private DatabaseReference dbRef;
    private List<Task> listTask;
    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);

        dbRef = FirebaseDatabase.getInstance().getReference("Tasks");

        lv = findViewById(R.id.tasks_lv_id);

        test = findViewById(R.id.lv_test);
        listTask = new ArrayList<>();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String UserInfo = listTask.get(position).getId();
                test.setText(UserInfo);


                Intent i =  new Intent(AllTasks.this, ViewActivity.class);
                i.putExtra("currentId", UserInfo);
                startActivity(i);
                finish();
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listTask.clear();
                for(DataSnapshot taskSnapshot : dataSnapshot.getChildren()){
                    Task obj = taskSnapshot.getValue(Task.class);
                    listTask.add(obj);

                }

                TaskAdapter adapter = new TaskAdapter(AllTasks.this, listTask);
                lv.setAdapter(adapter);

                Toast.makeText(AllTasks.this, "Firebase Connected", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AllTasks.this, "Firebase Connection Failed", Toast.LENGTH_SHORT).show();
            }
        });


    }



}
