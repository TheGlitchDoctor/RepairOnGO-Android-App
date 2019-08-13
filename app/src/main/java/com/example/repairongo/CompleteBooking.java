package com.example.repairongo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CompleteBooking extends AppCompatActivity {
    TextView title, desc;
    Button complete;
    EditText passw;
    private FirebaseAuth mAuth;
    private static final String TAG = "StartActivity";
    DatabaseReference databaseBookings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complete_booking);
        mAuth = FirebaseAuth.getInstance();
        databaseBookings = FirebaseDatabase.getInstance().getReference("booking");
        title=findViewById(R.id.title);
        desc=findViewById(R.id.desc);
        complete=findViewById(R.id.button);
        passw=findViewById(R.id.password);
        final FirebaseUser user = mAuth.getCurrentUser();

        DatabaseReference db=databaseBookings.child(getIntent().getStringExtra("bookingID"));
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                title.setText(dataSnapshot.child("problem_title").getValue(String.class));
                desc.setText(dataSnapshot.child("problem_description").getValue(String.class));
                final String userID=dataSnapshot.child("user_ID").getValue(String.class);
                final String spID=dataSnapshot.child("spid").getValue(String.class);
                complete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference database=FirebaseDatabase.getInstance().getReference("user").child(userID);
                        database.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnap) {
                                AuthCredential credential = EmailAuthProvider.getCredential(dataSnap.child("userEmail").getValue(String.class), passw.getText().toString());
                                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        completeBooking(userID,spID);
                                        Toast.makeText(CompleteBooking.this, "Booking Completed!", Toast.LENGTH_LONG).show();
                                        Intent n1=new Intent(CompleteBooking.this,user_home.class);
                                        startActivity(n1);
                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    private void completeBooking(String userID,String spID){

            String bookingID=getIntent().getStringExtra("bookingID");
            databaseBookings.child(bookingID).child("checker").setValue(userID+"Completed");
            databaseBookings.child(bookingID).child("sp_checker").setValue(spID+"Completed");
            databaseBookings.child(bookingID).child("status").setValue("Completed");



    }
}
