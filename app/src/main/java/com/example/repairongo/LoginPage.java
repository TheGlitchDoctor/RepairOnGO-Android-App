package com.example.repairongo;

import android.annotation.SuppressLint;
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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class LoginPage extends AppCompatActivity {
    EditText editTextEmail;
    EditText editTextPass;
    Button buttonLogin;
    private FirebaseAuth mAuth;
    private static final String TAG = "StartActivity";
    protected Boolean flag;

    DatabaseReference databaseUser;
    DatabaseReference databaseServiceProvider;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setDialog(boolean show){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //View view = getLayoutInflater().inflate(R.layout.progress);
        builder.setView(R.layout.progress);
        Dialog dialog = builder.create();
        if (show)dialog.show();
        else dialog.dismiss();
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(getIntent().getExtras().getBoolean("flag")){
            flag=getIntent().getExtras().getBoolean("flag");
        }
        else{
            flag=false;
        }

        if(flag==true) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.login_page);
        }



        mAuth=FirebaseAuth.getInstance();

        databaseServiceProvider= FirebaseDatabase.getInstance().getReference("serviceprovider");
        databaseUser=FirebaseDatabase.getInstance().getReference("user");

        editTextEmail=findViewById(R.id.editTextEmail);
        editTextPass=findViewById(R.id.editTextPass);
        buttonLogin=findViewById(R.id.buttonLogin);


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    setDialog(true);
                }
                signIn(editTextEmail.getText().toString(), editTextPass.getText().toString());

            }
        });
    }
    private void signIn(final String username, final String password){

        mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(TAG,"Sign In Success");

                    FirebaseUser user=mAuth.getCurrentUser();
                    final String userID=user.getUid();
                    databaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(userID)){
                                Toast.makeText(LoginPage.this,"Sign in Success",Toast.LENGTH_LONG).show();
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    setDialog(false);
                                }
                                Intent n1=new Intent(LoginPage.this, user_home.class);
                                startActivity(n1);
                        }else{
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    setDialog(false);
                                }
                                Intent n2=new Intent(LoginPage.this,sp_home.class);
                                startActivity(n2);
                            }


                    }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(LoginPage.this,"Login Error!!",Toast.LENGTH_LONG).show();

                        }

                    });

                    }


                }
            });
        }

        /*if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "You need to enter a valid Email!", Toast.LENGTH_LONG).show();
        }else{
            String serviceProviderId=databaseServiceProvider.push().getKey();
            UserServiceProvider serviceProvider=new UserServiceProvider(serviceProviderId,name,phoneNo,email);//,serviceType);
            databaseServiceProvider.child(serviceProviderId).setValue(serviceProvider);
            Toast.makeText(this,"Service Provider Registered Successfully!",Toast.LENGTH_LONG).show();


        }*/
        }
