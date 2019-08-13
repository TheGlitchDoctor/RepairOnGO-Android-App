package com.example.repairongo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StartActivity extends AppCompatActivity {
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

        //EditText editTextName;
        //EditText editTextPhoneNo;
        //EditText editTextEmail;
        Button buttonLogin;
        Button buttonSignUpService;
        Button buttonSignUpUser;



        buttonLogin=findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n3=new Intent(StartActivity.this, LoginPage.class);
                n3.putExtra("flag",true);
                startActivity(n3);
            }
        });

        //DatabaseReference databaseUser;
        buttonSignUpUser=findViewById(R.id.buttonSignUpUser);
        buttonSignUpUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent n2=new Intent(StartActivity.this, User.class);
                startActivity(n2);
            }
        });

        buttonSignUpService=findViewById(R.id.buttonSignUpService);
        buttonSignUpService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n1 = new Intent(StartActivity.this, ServiceProvider.class);
                startActivity(n1);
                }
            });}

    @Override
    public void onBackPressed() {
        Toast.makeText(this,"Can't Go Back!",Toast.LENGTH_LONG).show();
        }

    }



        /*buttonSignUpUser=(Button) findViewById(R.id.buttonSignUpUser);
        buttonSignUpUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(StartActivity.this, ServiceProvider.class);
                startActivity(n);
                }
            });}*/


    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseUser= FirebaseDatabase.getInstance().getReference("user");

        editTextName=(EditText) findViewById(R.id.editTextName);
        editTextPhoneNo=(EditText) findViewById(R.id.editTextPhoneNo);
        editTextEmail=(EditText) findViewById(R.id.editTextEmail);
        buttonSignUp=(Button) findViewById(R.id.buttonSignUp);
        spinnerUserType=(Spinner) findViewById(R.id.spinnerUserType);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userType=spinnerUserType.getSelectedItem().toString();
                if(userType=="Service Provider"){
                    Intent intent = new Intent(getApplicationContext(), com.example.repairongo.ServiceProvider.class);
                    startActivity(intent);
                }
                addUser();
            }
        });
    }*/

    /*private void addUser(){
        String name=editTextName.getText().toString().trim();
        String phoneNo=editTextPhoneNo.getText().toString().trim();
        String email=editTextEmail.getText().toString().trim();
        String userType=spinnerUserType.getSelectedItem().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "You need to enter a Name!", Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(phoneNo)){
            Toast.makeText(this, "You need to enter your Phone Number!", Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "You need to enter a valid Email!", Toast.LENGTH_LONG).show();
        }else{
            String userId=databaseUser.push().getKey();
            User user=new User(userId,name,phoneNo,email,userType);
            databaseUser.child(userId).setValue(user);

            Toast.makeText(this,"User registered successfully!",Toast.LENGTH_LONG).show();
        }
    }

    private void addServiceProvider(){
        String name=editTextName.getText().toString().trim();
        String phoneNo=editTextPhoneNo.getText().toString().trim();
        String email=editTextEmail.getText().toString().trim();
        String userType=spinnerUserType.getSelectedItem().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "You need to enter a Name!", Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(phoneNo)){
            Toast.makeText(this, "You need to enter your Phone Number!", Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "You need to enter a valid Email!", Toast.LENGTH_LONG).show();
        }else{
            String serviceProviderId=databaseUser.push().getKey();
            User user=new User(userId,name,phoneNo,email,userType);
            databaseUser.child(userId).setValue(user);

            Toast.makeText(this,"User registered successfully!",Toast.LENGTH_LONG).show();
        }
    }
}*/
