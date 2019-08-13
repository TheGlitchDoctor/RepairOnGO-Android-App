package com.example.repairongo;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class SpEditProfile extends AppCompatActivity {
    private EditText name, email, phone, otherServices, city, state, country  ;
    private EditText passw;

    private Button submit;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    DatabaseReference database= FirebaseDatabase.getInstance().getReference("serviceprovider");

    public SpEditProfile(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_edit_profile);

        final FirebaseUser user = mAuth.getCurrentUser();
        final String userID = user.getUid();
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        otherServices=findViewById(R.id.userCity);
        passw=findViewById(R.id.password);
        submit=findViewById(R.id.buttonSubmit);
        city=findViewById(R.id.userCity);
        state=findViewById(R.id.userState);
        country=findViewById(R.id.userCountry);

        city.setVisibility(View.GONE);
        state.setVisibility(View.GONE);
        country.setVisibility(View.GONE);
        final EditText pass = findViewById(R.id.password);

        final DatabaseReference currentUser = database.child(userID);
        currentUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                name.setHint(dataSnapshot.child("name").getValue(String.class));
                email.setHint(dataSnapshot.child("email").getValue(String.class));
                phone.setHint(dataSnapshot.child("phoneNo").getValue(String.class));
                otherServices.setHint(dataSnapshot.child("otherServices").getValue(String.class));


                final String userPassword=dataSnapshot.child("pass").getValue(String.class);
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        AuthCredential credential = EmailAuthProvider.getCredential(dataSnapshot.child("email").getValue(String.class), passw.getText().toString());


                        if(TextUtils.isEmpty(name.getText())){
                            Toast.makeText(SpEditProfile.this, "Enter your Name!", Toast.LENGTH_SHORT).show();
                        }else if(TextUtils.isEmpty(email.getText())){
                            Toast.makeText(SpEditProfile.this, "You need to enter your Phone Number!", Toast.LENGTH_LONG).show();
                        }else if(TextUtils.isEmpty(phone.getText())){
                            Toast.makeText(SpEditProfile.this, "You need to enter a valid Email!", Toast.LENGTH_LONG).show();
                        }else if(TextUtils.isEmpty(passw.getText())){
                            Toast.makeText(SpEditProfile.this,"Enter Your Password to confirm!",Toast.LENGTH_LONG).show();
                        }else if(!TextUtils.isEmpty(passw.getText())) {
                            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
                                    User.updateEmail(email.getText().toString());
                                    database.child(userID).child("name").setValue(name.getText().toString());
                                    database.child(userID).child("email").setValue(email.getText().toString());
                                    database.child(userID).child("phoneNo").setValue(phone.getText().toString());
                                    database.child(userID).child("otherServices").setValue(otherServices.getText().toString());


                                    Toast.makeText(SpEditProfile.this, "Updated Successfully!", Toast.LENGTH_LONG).show();
                                    Intent n=new Intent(SpEditProfile.this,sp_home.class);
                                    startActivity(n);
                                }
                            });




                        }else{
                            Toast.makeText(SpEditProfile.this,"Failed to Update! Try Again..",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SpEditProfile.this,"Failed! Try Again .. ",Toast.LENGTH_LONG).show();

            }
        });

    }}

