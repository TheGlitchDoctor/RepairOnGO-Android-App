package com.example.repairongo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class queryform extends AppCompatActivity {

    EditText editTextproblemtitle, date;
    EditText editTextproblemdescription;
    Button buttonsubmit;
    private FirebaseAuth mAuth;
    private static final String TAG = "StartActivity";
    DatabaseReference databaseBookings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.queryform);
        mAuth=FirebaseAuth.getInstance();
        databaseBookings= FirebaseDatabase.getInstance().getReference("booking");
        String bookingid;

        editTextproblemtitle=findViewById(R.id.problem_title);
        editTextproblemdescription=findViewById(R.id.problem_description);
        date=findViewById(R.id.date);
        date.setVisibility(View.GONE);
        buttonsubmit=findViewById(R.id.submit_problem);

        buttonsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addquery();
                //Intent n=new Intent(queryform.this,user_home.class);
                //startActivity(n);
            }
        });
    }
    private void addquery(){

        final String problemTitle=editTextproblemtitle.getText().toString().trim();
        final String problemDescription=editTextproblemdescription.getText().toString().trim();
        
        

        if(TextUtils.isEmpty(problemTitle)){
            Toast.makeText(this, "Enter your Problem Title", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(problemDescription)) {
            Toast.makeText(this, "You need to enter your Problem Description", Toast.LENGTH_LONG).show();
        }else{


            FirebaseUser user1=mAuth.getCurrentUser();
            String spid=getIntent().getStringExtra("spID");
            String response=null;
            String bookingdate=null;
            String bookingcost=null;
            String status="Pending";
            String sp_checker=spid+status;
            String userID=user1.getUid();
            String bookingid=databaseBookings.push().getKey();
            String checker=userID+status;
            submit_query booking= new submit_query(bookingid,userID,spid,problemTitle,problemDescription,response,bookingdate,bookingcost,status,checker,sp_checker);
            databaseBookings.child(bookingid).setValue(booking);
            Toast.makeText(this, "Query Sent.", Toast.LENGTH_LONG).show();
            Intent n1=new Intent(queryform.this,user_home.class);
            startActivity(n1);
        }


    }
}
