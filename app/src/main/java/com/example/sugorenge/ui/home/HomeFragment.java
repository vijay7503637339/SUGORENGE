package com.example.sugorenge.ui.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sugorenge.FacilityManagement;
import com.example.sugorenge.Patientcare;
import com.example.sugorenge.PaymentWebview;
import com.example.sugorenge.R;
import com.example.sugorenge.Adapter.SliderAdapter;
import com.example.sugorenge.Searchservices;
import com.example.sugorenge.SliderUtils;
import com.example.sugorenge.Staff;
import com.example.sugorenge.Sub_Staff_Category;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.sugorenge.MainActivity.BASE_URL;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ViewPager viewPager, viewPager2;
    TabLayout indicator, indicator2;
    RecyclerView recyclerview;
    EditText search;

    LinearLayout hsmgt, ptcare, acservice, realesate, gardningm, showrooms, facility, ac;
    ArrayList<SliderUtils> arrayList = new ArrayList<>();
    ArrayList<SliderUtils> arrayList2 = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = view.findViewById(R.id.view_pager_home);
        viewPager2 = view.findViewById(R.id.view_pager_home2);
        indicator = view.findViewById(R.id.indicator);
        indicator2 = view.findViewById(R.id.indicator2);
        search = view.findViewById(R.id.search);
        recyclerview = view.findViewById(R.id.recyclerview);
        ac = view.findViewById(R.id.ac);
      /*
         acservice= view.findViewById(R.id.acservice);
        realesate = view.findViewById(R.id.realesate);
        gardningm = view.findViewById(R.id.gardning);
        showrooms = view.findViewById(R.id.showrooms);
        hotels = view.findViewById(R.id.hotels);

*/      search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Searchservices.class);
                startActivity(intent);
            }
        });
        hsmgt = view.findViewById(R.id.hsmgt);
        facility = view.findViewById(R.id.facility);
        ptcare = view.findViewById(R.id.ptcare);

        hsmgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), Staff.class);
                startActivity(in);
            }
        });
        ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Sub_Staff_Category.class);
                intent.putExtra("id","125");
                intent.putExtra("name","Air Conditioner");
                 getActivity().startActivity(intent);
            }
        });

/*
        acservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getActivity(), ACservice.class);
                startActivity(in);
            }
        });
        realesate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getActivity(), Realestateservices.class);
                startActivity(in);
            }
        });
        gardningm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getActivity(), Gardning.class);
                startActivity(in);
            }
        });
        showrooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getActivity(), Showrooms.class);
                startActivity(in);
            }
        });

        hotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getActivity(), HotelsandResort.class);
                startActivity(in);
            }
        });*/

        facility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Sub_Staff_Category.class);
                intent.putExtra("id","55");
                intent.putExtra("name","Facility Management");
                getActivity().startActivity(intent);
            }
        });

        ptcare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Sub_Staff_Category.class);
                intent.putExtra("id","63");
                intent.putExtra("name","Medical Care");
                getActivity().startActivity(intent);
            }
        });
        downloadBannerImage();
        //Submit();
        return view;
    }

    private void getBannerImage() {

        viewPager.setAdapter(new SliderAdapter(getActivity(), arrayList, "home"));
        indicator.setupWithViewPager(viewPager, true);
        viewPager2.setAdapter(new SliderAdapter(getActivity(), arrayList2, "home"));
        indicator2.setupWithViewPager(viewPager, true);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 4000, 4000);

    }

    private class SliderTimer extends TimerTask {
        @Override
        public void run() {
            if (getActivity() != null) {
                (getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (viewPager.getCurrentItem() < arrayList.size() - 1) {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        } else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        }
    }


    private void downloadBannerImage() {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);


        String url = BASE_URL + "slider";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));

                    int r_code = obj.getInt("status");
                    if (r_code==1) {

                      progressDialog.dismiss();
                        JSONArray jsonArray = obj.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            String banner_path = jsonObject2.getString("image");
                            String header_img = jsonObject2.getString("header_img");
                            String footer_img = jsonObject2.getString("footer_img");
                            SliderUtils sliderUtils = new SliderUtils();
                            sliderUtils.setSliderImageUrl(banner_path);
                            if (header_img.equalsIgnoreCase("1")) {
                                arrayList.add(sliderUtils);
                            }
                            if (footer_img.equalsIgnoreCase("1")) {
                                arrayList2.add(sliderUtils);
                            }
                        }
                        getBannerImage();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "" + obj.getString("Message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something Wrong" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
        };
        requestQueue.add(stringRequest);
    }

}