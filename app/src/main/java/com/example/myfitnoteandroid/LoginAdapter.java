package com.example.myfitnoteandroid;


import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class LoginAdapter extends FragmentPagerAdapter {


    int totalTabs;
    Context context;

    public LoginAdapter(FragmentManager fm, Context context, int totalTabs){
        super(fm);
        this.totalTabs = totalTabs;
        this.context = context;
    }

    @Override
    public int getCount() {
        return totalTabs;
    }

    public Fragment getItem(int position){
        switch (position){
            case 0:
            LoginTabFragment loginTabFragment = new LoginTabFragment();
            return loginTabFragment;
            case 1:
            RegistrazioneTabFragment registrazioneTabFragment = new RegistrazioneTabFragment();
            return registrazioneTabFragment;
            default:
                return null;
        }

    }
}
