package com.example.sugorenge.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.sugorenge.Fragments.Activecase;
import com.example.sugorenge.Fragments.Allbookings;
import com.example.sugorenge.Fragments.Closecase;
import com.example.sugorenge.Fragments.Completed;
import com.example.sugorenge.Fragments.Ongoing;

public class MyPagerAdapter extends FragmentPagerAdapter {
    String service;
    int number = 2;
    Context context;
    String String;


    public MyPagerAdapter(@NonNull FragmentManager fm, String service) {
        super(fm);


        this.service=service;
        //  Toast.makeText(context, ""+service, Toast.LENGTH_SHORT).show();

    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;


        if (service.equalsIgnoreCase("vendor"))

        {
            if (position == 0) {
                fragment = new Activecase();
            } else if (position == 1) {
                fragment = new Closecase();
            }

        }
        else {

            if (position == 0) {
                fragment = new Ongoing();
            } else if (position == 1) {
                fragment = new Completed();
            }
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return number;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;

        {
            if (service.equalsIgnoreCase("vendor")) {

                if (position == 0) {
                    title = "Active Cases";
                } else if (position == 1) {
                    title = "Close Cases";
                }

            } else {

                if (position == 0) {
                    title = "On Going";
                } else if (position == 1) {
                    title = "Completed";
                }
            }

            return title;

        }
    }}