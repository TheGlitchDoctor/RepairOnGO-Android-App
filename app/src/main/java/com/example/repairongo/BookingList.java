package com.example.repairongo;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BookingList extends AppCompatActivity {
    private static final String TAG = "StartActivity";
    DatabaseReference database = FirebaseDatabase.getInstance().getReference("booking");
    TextView title;
    FirebaseAuth mAuth;


    private RecyclerView sp_recyclerview;
    private String txt;
    private FirebaseRecyclerAdapter<submit_query, SpViewHolder> SpListAdapter;

    private View view;
    public BookingList(){}






    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_providers_list);
        String txt=getIntent().getStringExtra("key");
        mAuth=FirebaseAuth.getInstance();


        title=findViewById(R.id.sp_list_title);
        title.setText(txt+"Bookings : ");

        sp_recyclerview = (RecyclerView)findViewById(R.id.sp_recycleview);
        sp_recyclerview.hasFixedSize();



        Query event=database.orderByChild("checker").equalTo(mAuth.getCurrentUser().getUid()+txt);
        sp_recyclerview.setLayoutManager(new LinearLayoutManager(BookingList.this));
        FirebaseRecyclerOptions Options=new FirebaseRecyclerOptions.Builder<submit_query>().setQuery(event,submit_query.class).build();
        SpListAdapter=new FirebaseRecyclerAdapter<submit_query, SpViewHolder>(Options) {
            @Override
            protected void onBindViewHolder(@NonNull final SpViewHolder holder, final int position, @NonNull final submit_query model) {
                holder.name.setText(model.getBooking_id());
                holder.email.setText(model.getProblem_title());
                holder.phone.setText(model.getProblem_description());
                holder.otherServices.setText(model.getStatus());

                final DatabaseReference db=FirebaseDatabase.getInstance().getReference("serviceprovider").child(model.getSpid());
                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        holder.spName.setText(dataSnapshot.child("name").getValue(String.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }


            @NonNull
            @Override
            public SpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
                return new SpViewHolder(view);
            }
        };
        sp_recyclerview.setAdapter(SpListAdapter);





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
            TVspName.setText("Service Provider -->");

        }

    }
}