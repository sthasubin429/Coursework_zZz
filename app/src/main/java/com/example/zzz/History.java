package com.example.zzz;

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

public class History extends AppCompatActivity {
    private ListView lv;
    private DatabaseReference dbRef;
    private List<Task> listTask;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        /**
         * Selects all the necessary parameters
         */
        dbRef = FirebaseDatabase.getInstance().getReference();

        lv = findViewById(R.id.history_lv_id);

        listTask = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();

        /**
         * On click lister on each item on list view
         * Starts activity that displays all the details of the Task, the details cannot be edited.
         */
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String UserInfo = listTask.get(position).getId();


                Intent i =  new Intent(History.this, Archive.class);
                i.putExtra("currentId", UserInfo);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        /**
         * Queries the Realtime Database
         * Selects the tasks which are Done and displays them using list view
         */
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Query query = dbRef.child("Tasks").orderByChild("assignedTo").equalTo(currentUser.getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot taskSnapshot : dataSnapshot.getChildren()) {
                        Task obj = taskSnapshot.getValue(Task.class);
                        if (obj.getStatus().equals("Done")){
                            listTask.add(obj);
                        }
                    }

                    TaskAdapter adapter = new TaskAdapter(History.this, listTask);
                    lv.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(History.this, "Firebase Connection Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openDashboard(View view) {
        startActivity(new Intent(this, dashboard.class));
    }
}
