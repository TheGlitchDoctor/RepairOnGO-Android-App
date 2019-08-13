package com.example.repairongo;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Sp_AccountFragment extends Fragment {
    FirebaseAuth mAuth;


    Button editProfile;
    Button logOut;
    TextView profile_name, profile_email, profile_phone ,profile_address, profile_city, Services,city,state,country;


    public Sp_AccountFragment(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_accounts,container,false);
        mAuth= FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();

        final String spID=user.getUid();
        final DatabaseReference database=FirebaseDatabase.getInstance().getReference("serviceprovider");

        editProfile=(Button)view.findViewById(R.id.buttonEdit);
        profile_name=(TextView)view.findViewById(R.id.profile_name);
        profile_email=(TextView)view.findViewById(R.id.profile_email);
        profile_phone=(TextView)view.findViewById(R.id.profile_phone);
        profile_address=(TextView)view.findViewById(R.id.profile_address);
        profile_city=(TextView)view.findViewById(R.id.profile_city);
        Services=view.findViewById(R.id.otherServices);
        city=view.findViewById(R.id.serviceType);
        state=view.findViewById(R.id.state);
        country=view.findViewById(R.id.country);
        state.setText("");
        country.setText("");
        city.setText("Other Services : ");
        Services.setText("Service Type : ");
        logOut=(Button)view.findViewById(R.id.profile_logout);
        DatabaseReference currentUser=database.child(spID);
        currentUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                profile_name.setText(dataSnapshot.child("name").getValue(String.class));
                profile_email.setText(dataSnapshot.child("email").getValue(String.class));
                profile_phone.setText(dataSnapshot.child("phoneNo").getValue(String.class));
                profile_address.setText(dataSnapshot.child("serviceType").getValue(String.class));
                profile_city.setText(dataSnapshot.child("otherServices").getValue(String.class));







            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),"Some error occured loading Your Profile. Check Internet Connection..", Toast.LENGTH_LONG).show();
            }
        });
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n=new Intent(getActivity(),SpEditProfile.class);
                startActivity(n);
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent n=new Intent(getActivity(), StartActivity.class);
                startActivity(n);
            }
        });


        return view;
    }
}
