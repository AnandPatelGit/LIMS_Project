package com.example.anandpatelak.lims_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class InstructorActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    Button btnAddFolder, btnDisplayFolders;
    ListView folderList;
    String selectedSubjectStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);
        btnAddFolder = (Button) findViewById(R.id.addFolderButton);
        folderList = (ListView) findViewById(R.id.listViewFeedbacks);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            String j =(String) b.get("selected-subject");

            selectedSubjectStr = j;
        }

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("folders");

        databaseReference.orderByChild("subjectName").equalTo(selectedSubjectStr).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.child("folderName").getValue(String.class);
                arrayList.add(value);
                arrayAdapter = new ArrayAdapter<String>(InstructorActivity.this, android.R.layout.simple_list_item_1,arrayList);
                folderList.setAdapter(arrayAdapter);
                folderList.setOnItemClickListener(new FileListClickHandler());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void naviagateToNewFolderActivity(View view){
        Intent intent = new Intent(this, CreateFolder.class);
        intent.putExtra("selected-subject", selectedSubjectStr);
        startActivity(intent);
    }
    public class FileListClickHandler implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
            // TODO Auto-generated method stub
            //TextView listText = (TextView) view.findViewById(R.id.listText);
            String text = ((TextView)view).getText().toString();
            // MyClass item = (MyClass) adapter.getItemAtPosition(position);
            Intent intent = new Intent(InstructorActivity.this, InstructorFolderItems.class);
            intent.putExtra("selected-folder", text);
            startActivity(intent);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.slider, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuLogout:

                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent (this, LoginActivity.class));
        }
        return true;
    }
}
