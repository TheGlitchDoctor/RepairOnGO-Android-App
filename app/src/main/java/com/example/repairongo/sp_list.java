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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
public class sp_list extends AppCompatActivity {
    private static final String TAG = "StartActivity";
    DatabaseReference database = FirebaseDatabase.getInstance().getReference("serviceprovider");
    TextView textView;

    private RecyclerView sp_recyclerview;
    private String txt;
    private FirebaseRecyclerAdapter<UserServiceProvider, SpViewHolder> SpListAdapter;

    private View view;
    public sp_list(){}






    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_providers_list);
        String txt=getIntent().getStringExtra("SpType");
        Toast.makeText(this,txt,Toast.LENGTH_LONG).show();
        Query event=database.orderByChild("serviceType").equalTo(txt);
        sp_recyclerview = (RecyclerView)findViewById(R.id.sp_recycleview);
        sp_recyclerview.hasFixedSize();


        sp_recyclerview.setLayoutManager(new LinearLayoutManager(sp_list.this));
        FirebaseRecyclerOptions Options=new FirebaseRecyclerOptions.Builder<UserServiceProvider>().setQuery(event,UserServiceProvider.class).build();
        SpListAdapter=new FirebaseRecyclerAdapter<UserServiceProvider, SpViewHolder>(Options) {
            @Override
            protected void onBindViewHolder(@NonNull SpViewHolder holder, final int position, @NonNull UserServiceProvider model) {
                holder.name.setText(model.getName());
                holder.email.setText(model.getEmail());
                holder.phone.setText(model.getPhoneNo());
                holder.otherServices.setText(model.getServiceType());
                holder.spName.setText(model.getOtherServices());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(sp_list.this,queryform.class);
                        intent.putExtra("spID",getRef(position).getKey());
                        startActivity(intent);
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
        private TextView name,email,phone,otherServices;
        TextView TVname,TVemail,TVphone,TVotherServices,TVspName, spName;

        public SpViewHolder(@NonNull View itemView){
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.name);
            email=(TextView)itemView.findViewById(R.id.email);
            phone=(TextView)itemView.findViewById(R.id.phone);
            otherServices=(TextView)itemView.findViewById(R.id.otherServices);
            spName=itemView.findViewById(R.id.spName);
            TVotherServices=itemView.findViewById(R.id.textViewOtherServices);
            TVotherServices.setText("Service Type -->");
            TVspName=itemView.findViewById(R.id.textViewSpName);
            TVspName.setText("Other Services -->");

        }

    }
}