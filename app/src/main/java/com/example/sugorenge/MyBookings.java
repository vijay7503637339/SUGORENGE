package com.example.sugorenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sugorenge.Adapter.MyPagerAdapter;
import com.example.sugorenge.Adapter.SubcategoryAdapter;
import com.example.sugorenge.model.Subcategorymodel;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.sugorenge.Adapter.ShareboxxAdapter.IMAGE_BASE_URL;
import static com.example.sugorenge.MainActivity.BASE_URL;

public class MyBookings extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    MyPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);


        viewPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(),"user");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);



    }

    public void booknow(View view) {

        Intent in =new Intent(MyBookings.this,Searchservices.class);
        startActivity(in);

    }

    public void bacxk(View view) {
        onBackPressed();
    }




}
