package com.example.anandpatelak.lims_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class  DisplaySingleFeebackActivity extends AppCompatActivity {

    String selectedFeedbackFileRef;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TextView tvGrade, tvComment, textViewHeaderSingleDisplay, textViewLabelGrade;
    Button buttonBackToMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_single_feeback);


        tvComment = (TextView) findViewById(R.id.tvComment) ;
        tvGrade = (TextView) findViewById(R.id.tvGrade);
        textViewHeaderSingleDisplay = (TextView) findViewById(R.id.textViewHeaderFeedback);
        textViewLabelGrade = (TextView) findViewById(R.id.textView15);

        buttonBackToMain = (Button) findViewById(R.id.buttonBack);

        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();


        textViewHeaderSingleDisplay.setText("Feedback for "+email);

        if(b!=null)
        {
            String j =(String) b.get("selected-feedback");

            textViewLabelGrade.setText("Your Grade for"+j.replace( "Folders/", " "));
            selectedFeedbackFileRef = j;


        }

        //Button Activity
        buttonBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),StudentActivity.class));
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("feedback");




        databaseReference.orderByChild("fileRef").equalTo(selectedFeedbackFileRef).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {



                String grade = dataSnapshot.child("grade").getValue(String.class);
                tvGrade.setText(grade);
                String comment = dataSnapshot.child("comment").getValue(String.class);
                tvComment.setText(comment);
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
}
