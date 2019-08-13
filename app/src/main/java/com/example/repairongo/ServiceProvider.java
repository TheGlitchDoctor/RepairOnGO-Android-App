package com.example.repairongo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ServiceProvider extends AppCompatActivity {
    EditText editTextName;
    EditText editTextPhoneNo;
    EditText editTextEmail;
    EditText editTextOtherServices;
    EditText editTextPass;
    Spinner spinnerServiceType;
    Button buttonSignUp1;
    FirebaseUser currentUser;
    DatabaseReference databaseServiceProvider;
    private FirebaseAuth mAuth;
    private static final String TAG = "StartActivity";

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
        setContentView(R.layout.service_providers);

        mAuth=FirebaseAuth.getInstance();



        databaseServiceProvider= FirebaseDatabase.getInstance().getReference("serviceprovider");

        editTextName=findViewById(R.id.editTextName);
        editTextPhoneNo=findViewById(R.id.editTextPhoneNo);
        editTextEmail=findViewById(R.id.editTextEmail);
        buttonSignUp1=findViewById(R.id.buttonSignUp1);
        editTextOtherServices=findViewById(R.id.editTextOtherServices);
        spinnerServiceType=findViewById(R.id.spinnerServiceType);
        editTextPass=findViewById(R.id.editTextPassword);

        buttonSignUp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag=validate();
                if(flag==true){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        setDialog(true);
                    }
                    addServiceProvider();
            }}
        });
    }


    private boolean validate(){
        boolean flag=true;
        String name=editTextName.getText().toString().trim();
        String phoneNo=editTextPhoneNo.getText().toString().trim();
        String email=editTextEmail.getText().toString().trim();
        String serviceType=spinnerServiceType.getSelectedItem().toString().trim();
        String otherServices=editTextOtherServices.getText().toString().trim();
        final String pass=editTextPass.getText().toString().trim();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Enter your Name!", Toast.LENGTH_SHORT).show();
            flag=false;
        }else if(TextUtils.isEmpty(phoneNo)){
            Toast.makeText(this, "You need to enter your Phone Number!", Toast.LENGTH_LONG).show();
            flag=false;
        }else if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "You need to enter a valid Email!", Toast.LENGTH_LONG).show();
            flag=false;
        }else if(TextUtils.isEmpty(pass) & TextUtils.getTrimmedLength(pass)<=8){
            Toast.makeText(this,"Enter a password of minimum 8 characters",Toast.LENGTH_LONG).show();
            flag=false;
        }
        return flag;
    }

    private void addServiceProvider(){
        final String name=editTextName.getText().toString().trim();
        final String phoneNo=editTextPhoneNo.getText().toString().trim();
        final String email=editTextEmail.getText().toString().trim();
        final String serviceType=spinnerServiceType.getSelectedItem().toString().trim();
        final String otherServices=editTextOtherServices.getText().toString().trim();
        final String pass=editTextPass.getText().toString().trim();


        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){

                        FirebaseUser user=mAuth.getCurrentUser();
                        String serviceProviderId=user.getUid();
                        UserServiceProvider serviceProvider=new UserServiceProvider(serviceProviderId,name,phoneNo,email,serviceType,otherServices,pass);
                        databaseServiceProvider.child(serviceProviderId).setValue(serviceProvider);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            setDialog(false);
                        }
                        Log.d(TAG,"Service Provider Registration Success!");
                        Intent n1=new Intent(ServiceProvider.this,sp_home.class);
                        startActivity(n1);

                    }else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            setDialog(false);
                        }
                        Log.w(TAG,"Registration Failure! Try Again ..", task.getException());
                        Toast.makeText(ServiceProvider.this,"Authentication Failed", Toast.LENGTH_LONG).show();
                    }

                }
            });
            Toast.makeText(this,"Service Provider Registered Successfully!",Toast.LENGTH_LONG).show();


        }
}