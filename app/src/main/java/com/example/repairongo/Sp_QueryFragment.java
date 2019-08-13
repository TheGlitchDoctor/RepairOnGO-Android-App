package com.example.repairongo;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class Sp_QueryFragment extends Fragment {
    FirebaseAuth mAuth;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference("booking");
    RecyclerView bookingView;
    private FirebaseRecyclerAdapter<submit_query, SpViewHolder> SpListAdapter;

public Sp_QueryFragment(){}


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.sp_fragment_query,container,false);
        mAuth=FirebaseAuth.getInstance();
        TextView title=(TextView) view.findViewById(R.id.title);
        bookingView = (RecyclerView) view.findViewById(R.id.recycleview);
        bookingView.hasFixedSize();

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        bookingView.addItemDecoration(itemDecorator);

        title.setText("Pending Queries : ");

        bookingView.setLayoutManager(new LinearLayoutManager(getActivity()));

        String spID=mAuth.getCurrentUser().getUid();
        Query event=database.orderByChild("sp_checker").equalTo(spID+"Pending");
        FirebaseRecyclerOptions Options = new FirebaseRecyclerOptions.Builder<submit_query>().setQuery(event, submit_query.class).build();

        SpListAdapter = new FirebaseRecyclerAdapter<submit_query, Sp_QueryFragment.SpViewHolder>(Options) {
            @Override
            protected void onBindViewHolder(@NonNull final Sp_QueryFragment.SpViewHolder holder, final int position, @NonNull final submit_query model) {

                holder.name.setText(model.getBooking_id());
                final String bookingID=model.getBooking_id();
                holder.email.setText(model.getProblem_title());
                holder.phone.setText(model.getProblem_description());
                holder.otherServices.setText(model.getStatus());
                final DatabaseReference db=FirebaseDatabase.getInstance().getReference("user").child(model.getUser_ID());
                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        holder.spName.setText(dataSnapshot.child("userName").getValue(String.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getActivity(),QueryResponse.class);
                        intent.putExtra("bookingID",bookingID);
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public Sp_QueryFragment.SpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);

                return new Sp_QueryFragment.SpViewHolder(view1);
            }
        };
        bookingView.setAdapter(SpListAdapter);
        return view;

    }
    @Override
    public void onStart() {
        super.onStart();
        SpListAdapter.startListening();

    }


    @Override
    public void onStop() {
        super.onStop();
        SpListAdapter.stopListening();
    }
    public static class SpViewHolder extends RecyclerView.ViewHolder{
        private TextView name,email,phone,otherServices,spName;
        TextView TVname,TVemail,TVphone,TVotherServices,TVspName;

        public SpViewHolder(@NonNull View itemView){
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.name);
            email=(TextView)itemView.findViewById(R.id.email);
            phone=(TextView)itemView.findViewById(R.id.phone);
            otherServices=(TextView)itemView.findViewById(R.id.otherServices);
            spName=itemView.findViewById(R.id.spName);
            TVname=itemView.findViewById(R.id.textViewName);
            TVname.setText("Booking ID -->");
            TVemail=itemView.findViewById(R.id.textViewEmail);
            TVemail.setText("Booking Title -->");
            TVphone=itemView.findViewById(R.id.textViewPhone);
            TVphone.setText("Problem Description -->");
            TVotherServices=itemView.findViewById(R.id.textViewOtherServices);
            TVotherServices.setText("Status -->");
            TVspName=itemView.findViewById(R.id.textViewSpName);
            TVspName.setText("Customer Name -->");

        }

    }
}


