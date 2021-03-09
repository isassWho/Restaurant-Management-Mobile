package com.cod.tablayout_demo.controllers;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cod.tablayout_demo.fragments.EmptyFragment;
import com.cod.tablayout_demo.fragments.ReservationsFragment;
import com.cod.tablayout_demo.fragments.WaitingListFragment;

public class PagerController extends FragmentPagerAdapter {

    int numoftabs;

    public PagerController(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.numoftabs = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new WaitingListFragment();
            case 1:
                return new EmptyFragment();
            case 2:
                return new ReservationsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numoftabs;
    }
}
