package com.example.sugorenge.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sugorenge.R;
import com.example.sugorenge.SliderUtils;
import com.squareup.picasso.Picasso;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

import static com.example.sugorenge.Adapter.ShareboxxAdapter.IMAGE_BASE_URL;


public class SliderAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<SliderUtils> sliderUtils;
    String service;
    ProgressDialog pDialog;

    public SliderAdapter(Context context, ArrayList<SliderUtils> sliderUtils, String service) {
        this.context = context;
        this.sliderUtils = sliderUtils;
        this.service = service;
    }

    @Override
    public int getCount() {
        return sliderUtils.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.offer_image_slide, null);
         ImageView imageView =  view.findViewById(R.id.imageView);
        GifImageView gif = view.findViewById(R.id.gif);
        Log.d("EE",String.valueOf(sliderUtils));


        Picasso.with(context)
                .load(  IMAGE_BASE_URL+sliderUtils.get(position).getSliderImageUrl())
                .into(imageView);
        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);


        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);

    }
}