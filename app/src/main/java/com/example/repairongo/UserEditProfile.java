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

public class UserEditProfile extends AppCompatActivity {
    private EditText name;
    private EditText email;
    private EditText phone;
    private EditText address;
    private EditText city;
    private EditText state;
    private EditText country;
    private EditText pass;

    private Button submit;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    DatabaseReference database= FirebaseDatabase.getInstance().getReference("user");

    public UserEditProfile(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_edit_profile);

        final FirebaseUser user = mAuth.getCurrentUser();
        final String userID = user.getUid();
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.userAddress);
        city=findViewById(R.id.userCity);
        state=findViewById(R.id.userState);
        country=findViewById(R.id.userCountry);
        pass=findViewById(R.id.password);
        submit=findViewById(R.id.buttonSubmit);
        final EditText pass = findViewById(R.id.password);

        final DatabaseReference currentUser = database.child(userID);
        currentUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                name.setHint(dataSnapshot.child("userName").getValue(String.class));
                email.setHint(dataSnapshot.child("userEmail").getValue(String.class));
                phone.setHint(dataSnapshot.child("userPhoneNo").getValue(String.class));
                address.setHint(dataSnapshot.child("userAddress").getValue(String.class));
                city.setHint(dataSnapshot.child("userCity").getValue(String.class));
                state.setHint(dataSnapshot.child("userState").getValue(String.class));
                country.setHint(dataSnapshot.child("userCountry").getValue(String.class));
                final String userPassword=dataSnapshot.child("userPass").getValue(String.class);
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        AuthCredential credential = EmailAuthProvider.getCredential(dataSnapshot.child("userEmail").getValue(String.class), pass.getText().toString());


                        if(TextUtils.isEmpty(name.getText())){
                            Toast.makeText(UserEditProfile.this, "Enter your Name!", Toast.LENGTH_SHORT).show();
                        }else if(TextUtils.isEmpty(email.getText())){
                            Toast.makeText(UserEditProfile.this, "You need to enter your Phone Number!", Toast.LENGTH_LONG).show();
                        }else if(TextUtils.isEmpty(phone.getText())){
                            Toast.makeText(UserEditProfile.this, "You need to enter a valid Email!", Toast.LENGTH_LONG).show();
                        }else if(TextUtils.isEmpty(address.getText())){
                            Toast.makeText(UserEditProfile.this,"You need to enter a Address!",Toast.LENGTH_LONG).show();
                        }else if(!TextUtils.isEmpty(pass.getText())) {
                            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
                                    User.updateEmail(email.getText().toString());
                                    database.child(userID).child("userName").setValue(name.getText().toString());
                                    database.child(userID).child("userEmail").setValue(email.getText().toString());
                                    database.child(userID).child("userPhoneNo").setValue(phone.getText().toString());
                                    database.child(userID).child("userAddress").setValue(address.getText().toString());
                                    database.child(userID).child("userCity").setValue(city.getText().toString());
                                    database.child(userID).child("userState").setValue(state.getText().toString());
                                    database.child(userID).child("userCountry").setValue(country.getText().toString());

                                    Toast.makeText(UserEditProfile.this, "Updated Successfully!", Toast.LENGTH_LONG).show();
                                    Intent n=new Intent(UserEditProfile.this,user_home.class);
                                    startActivity(n);
                                }
                            });




                        }else{
                            Toast.makeText(UserEditProfile.this,"Failed to Update! Try Again..",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UserEditProfile.this,"Failed! Try Again .. ",Toast.LENGTH_LONG).show();

            }
        });

    }}

