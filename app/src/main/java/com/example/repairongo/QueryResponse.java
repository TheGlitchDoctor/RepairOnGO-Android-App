package com.example.repairongo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QueryResponse extends AppCompatActivity {
    TextView desc;
    EditText editTextproblemCost;
    EditText editTextproblemResponse,editTextdate;
    Button buttonAccept;
    private FirebaseAuth mAuth;
    private static final String TAG = "StartActivity";
    DatabaseReference databaseBookings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.queryform);
        mAuth=FirebaseAuth.getInstance();
        databaseBookings= FirebaseDatabase.getInstance().getReference("booking");

        desc=findViewById(R.id.title);

        editTextproblemCost=findViewById(R.id.problem_title);
        editTextproblemCost.setHint("Enter Estimated Service Cost : ");
        editTextproblemResponse=findViewById(R.id.problem_description);
        editTextproblemResponse.setHint("Provide detailed Response : ");
        editTextdate=findViewById(R.id.date);
        editTextdate.setHint("Enter date of Servicing : ");
        buttonAccept=findViewById(R.id.submit_problem);
        buttonAccept.setText("Accept Booking");
        final String bookingID=getIntent().getStringExtra("bookingID");

    DatabaseReference db=databaseBookings.child(bookingID);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String description=dataSnapshot.child("problem_description").getValue(String.class);
                final String userID=dataSnapshot.child("user_ID").getValue(String.class);
                desc.setText(description);

                buttonAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        acceptquery(userID);
                        //Intent n=new Intent(queryform.this,user_home.class);
                        //startActivity(n);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    private void acceptquery(String userID){
        final String Cost=editTextproblemCost.getText().toString().trim();
        final String date=editTextdate.getText().toString().trim();
        final String response=editTextproblemResponse.getText().toString().trim();



        if(TextUtils.isEmpty(Cost)){
            Toast.makeText(this, "Enter Estimated Cost!", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(date)) {
            Toast.makeText(this, "Enter a Date for Booking!", Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(response)){
            Toast.makeText(this, "Enter a Response!", Toast.LENGTH_LONG).show();
        }else{

            String bookingID=getIntent().getStringExtra("bookingID");
            databaseBookings.child(bookingID).child("checker").setValue(userID+"Accepted");
            databaseBookings.child(bookingID).child("sp_checker").setValue(mAuth.getCurrentUser().getUid()+"Accepted");
            databaseBookings.child(bookingID).child("response").setValue(response);
            databaseBookings.child(bookingID).child("bookingdate").setValue(date);
            databaseBookings.child(bookingID).child("bookingcost").setValue(Cost);
            databaseBookings.child(bookingID).child("status").setValue("Accepted");
            Toast.makeText(this, "Booking Accepted.", Toast.LENGTH_LONG).show();
            Intent n1=new Intent(QueryResponse.this,sp_home.class);
            startActivity(n1);
        }


    }
}
