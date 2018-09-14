package com.example.kokwei217.rs_app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    PageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new AllFragment();

            case 1:
                return new ElectricFragment();

            case 2:
                return new MotorFragment();

            case 3:
                return new SensorsFragment();

            case 4:
                return new MicroFragment();

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
