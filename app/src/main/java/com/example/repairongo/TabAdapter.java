package com.example.repairongo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabAdapter extends FragmentPagerAdapter {
    private Context mcontext;
    int totalTabs;

    public TabAdapter(Context context,FragmentManager fm,int totalTabs) {
        super(fm);
        mcontext=context;
        this.totalTabs=totalTabs;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                HomeFragment homeFragment=new HomeFragment();
                return homeFragment;
            case 1:
                BookingFragment bookingFragment=new BookingFragment();
                return bookingFragment;
            case 2:
                AccountFragment accountFragment=new AccountFragment();
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
