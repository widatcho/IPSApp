package com.example.administrador.ipsapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrador on 01/02/2018.
 */

class SectionsPagerAdapter  extends FragmentPagerAdapter {
    private final List<Fragment> lista = new ArrayList<>();
    private final List<String> listaString = new ArrayList<>();


    public void addFragment(Fragment fragment, String title){
        lista.add(fragment);
        listaString.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listaString.get(position);
    }

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return lista.get(position);
    }

    @Override
    public int getCount() {
        return lista.size();
    }
}
