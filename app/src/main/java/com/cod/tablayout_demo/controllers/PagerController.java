package com.cod.tablayout_demo.controllers;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cod.tablayout_demo.fragments.ComandasFragment;
import com.cod.tablayout_demo.fragments.ListaDeEsperaFragment;
import com.cod.tablayout_demo.fragments.MesasFragment;

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
                return new ListaDeEsperaFragment();
            case 1:
                return new ComandasFragment();
            case 2:
                return new MesasFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numoftabs;
    }
}
