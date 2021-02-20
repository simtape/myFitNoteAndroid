package com.example.myfitnoteandroid.ui.login;


import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myfitnoteandroid.ui.sign_up.SignUpTabFragment;

import org.jetbrains.annotations.NotNull;

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

    @NotNull
    public Fragment getItem(int position){
        switch (position){
            case 0:
                return new LoginTabFragment();
            case 1:
                return new SignUpTabFragment();
            default:
                return null;
        }

    }
}
