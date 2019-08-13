package com.example.repairongo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class Sp_TabAdapter extends FragmentPagerAdapter {
    private Context mcontext;
    int totalTabs;

    public Sp_TabAdapter(Context context,FragmentManager fm,int totalTabs) {
        super(fm);
        mcontext=context;
        this.totalTabs=totalTabs;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Sp_QueryFragment queryFragment=new Sp_QueryFragment();
                return queryFragment;
            case 1:
                Sp_BookingFragment bookingFragment=new Sp_BookingFragment();
                return bookingFragment;
            case 2:
                Sp_AccountFragment accountFragment=new Sp_AccountFragment();
                return accountFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
