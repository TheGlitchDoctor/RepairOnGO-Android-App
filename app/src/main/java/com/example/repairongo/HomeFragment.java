package com.example.repairongo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment{
    private Button[] buttons = new Button [8];

    public HomeFragment(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        view.findViewById(R.id.button_00).setOnClickListener(mListener);
        view.findViewById(R.id.button_01).setOnClickListener(mListener);
        view.findViewById(R.id.button_10).setOnClickListener(mListener);
        view.findViewById(R.id.button_11).setOnClickListener(mListener);
        view.findViewById(R.id.button_20).setOnClickListener(mListener);
        view.findViewById(R.id.button_21).setOnClickListener(mListener);
        view.findViewById(R.id.button_30).setOnClickListener(mListener);
        view.findViewById(R.id.button_31).setOnClickListener(mListener);
        return view;
    }
    private final View.OnClickListener mListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                String value=((Button) v).getText().toString();
                Intent n=new Intent(getActivity(),sp_list.class);
                n.putExtra("SpType",value);
                startActivity(n);

            }
        };
    };

