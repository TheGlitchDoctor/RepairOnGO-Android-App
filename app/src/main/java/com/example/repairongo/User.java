package com.example.repairongo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class User extends AppCompatActivity {
    EditText editTextName;
    EditText editTextPhoneNo;
    EditText editTextEmail;
    Button buttonSignUp1;
    EditText editTextPass;
    EditText editTextAddress;
    EditText city;
    EditText state;
    EditText country;
    private FirebaseAuth mAuth;
    private static final String TAG = "StartActivity";

    DatabaseReference databaseUser;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setDialog(boolean show){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //View view = getLayoutInflater().inflate(R.layout.progress);
        builder.setView(R.layout.progress);
        Dialog dialog = builder.create();
        if (show)dialog.show();
        else dialog.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_signup);
        mAuth=FirebaseAuth.getInstance();
        databaseUser= FirebaseDatabase.getInstance().getReference("user");

        editTextName=findViewById(R.id.editTextName);
        editTextPhoneNo=findViewById(R.id.editTextPhoneNo);
        editTextEmail=findViewById(R.id.editTextEmail);
        editTextPass=findViewById(R.id.editTextPass);
        editTextAddress=findViewById(R.id.editTextAddress);
        buttonSignUp1=findViewById(R.id.buttonSignUp1);
        city=findViewById(R.id.editTextCity);
        state=findViewById(R.id.editTextState);
        country=findViewById(R.id.editTextCountry);


        buttonSignUp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    setDialog(true);
                }
                addUser();
            }
        });
    }
    private void addUser(){
        final String userName=editTextName.getText().toString().trim();
        final String userPhoneNo=editTextPhoneNo.getText().toString().trim();
        final String userEmail=editTextEmail.getText().toString().trim();
        final String userPass=editTextPass.getText().toString().trim();
        final String userAddress=editTextAddress.getText().toString();
        final String userCity=city.getText().toString();
        final String userState=state.getText().toString();
        final String userCountry=country.getText().toString();


        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this, "Enter your Name!", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(userPhoneNo)){
            Toast.makeText(this, "You need to enter your Phone Number!", Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(userEmail)){
            Toast.makeText(this, "You need to enter a valid Email!", Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(userAddress)){
            Toast.makeText(this,"You need to enter a Address!",Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(userPass)){
            Toast.makeText(this,"You need to set a Password!",Toast.LENGTH_LONG).show();
        }
        else{


            mAuth.createUserWithEmailAndPassword(userEmail,userPass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        FirebaseUser user1=mAuth.getCurrentUser();
                        String userId=user1.getUid();
                        UserSign_up user= new UserSign_up(userId,userName,userPhoneNo,userEmail,userAddress,userPass,userCity,userState,userCountry);
                        databaseUser.child(userId).setValue(user);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            setDialog(false);
                        }
                        Log.d(TAG,"User Registration Successfull!");
                        Intent n1=new Intent(User.this,user_home.class);
                        startActivity(n1);
                    }else{
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            setDialog(false);
                        }
                        Log.w(TAG,"Registration Failure! Try Again .. ",task.getException());
                        Toast.makeText(User.this,"Authentication Failed!",Toast.LENGTH_LONG).show();
                    }
                }
            });

        }}}