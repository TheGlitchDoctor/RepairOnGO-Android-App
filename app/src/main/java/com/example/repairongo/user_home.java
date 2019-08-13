package com.example.repairongo;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class user_home extends AppCompatActivity implements View.OnClickListener {
    private Button[][] buttons = new Button [4][2];
    TabLayout tab;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_home_page);

        viewPager=(ViewPager)findViewById(R.id.viewPager);
        tab=(TabLayout)findViewById(R.id.tablayout);

        final TabAdapter adapter=new TabAdapter(this,getSupportFragmentManager(),tab.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));

        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        /*for(int i=0; i<=3; i++){
            for(int j=0;j<=1;j++){
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(user_home.this);
                    }
                }*/
    }

    @Override
    public void onClick(View v) {
        /*String value=((Button) v).getText().toString();
        Intent n=new Intent(user_home.this,sp_list.class);
        n.putExtra("SpType",value);
        startActivity(n);*/
    }
    public void onBackPressed() {
        Toast.makeText(this,"Can't go back!",Toast.LENGTH_LONG).show();
    }

}

