package com.example.sugorenge.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sugorenge.Adapter.VendorcasesAdapter;
import com.example.sugorenge.R;
import com.example.sugorenge.model.Vendorcases;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.sugorenge.MainActivity.BASE_URL;


public class Closecase extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    RecyclerView recycler1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_closecase, container, false);
        mSwipeRefreshLayout = view.findViewById(R.id.swiperefresh_items);
        recycler1=view.findViewById(R.id.recycler1);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(mSwipeRefreshLayout.isRefreshing()) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 2000);
                getActivity().finish();
                startActivity(getActivity().getIntent());
                // Your code to make your refresh action
                // CallYourRefreshingMethod();
            }
        });  Submit();
        return  view;

    }

    private void Submit() {

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();
        pDialog.setCancelable(false);

        String url = BASE_URL+"vendor/allclosecase";
        SharedPreferences sharedPreferences;
        sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getContext());

        String mobile = sharedPreferences.getString("mobile", null);


        RequestQueue requestQueue = Volley.newRequestQueue(getContext());


        Map<String, String> params = new HashMap();
        params.put("mobile", mobile);


        JSONObject parameters = new JSONObject(params);


        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url,parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //     Toast.makeText(Exixtinguser.this, ""+response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));

                    int r_code = obj.getInt("status");
                    if (r_code==1) {
                        //  Toast.makeText(getApplicationContext(), "" + obj.getString("Message"), Toast.LENGTH_SHORT).show();
                        pDialog.dismiss();

                        JSONArray jsonArray;
                        jsonArray = obj.getJSONArray("data");

                        ArrayList<Vendorcases> Sharelist = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {

                            Vendorcases vendorcases=new Vendorcases();
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            String  date= jsonObject2.getString("date");
                            String  booking_id= jsonObject2.getString("booking_id");
                            String  time= jsonObject2.getString("time");
                            String  Discription= jsonObject2.getString("description");
                            String  title= jsonObject2.getString("category_name");
                            String  address= jsonObject2.getString("address");
                            String  alternate_mobile= jsonObject2.getString("alternate_mobile");
                            String  mobile= jsonObject2.getString("mobile");
                            String  parentcat_name= jsonObject2.getString("parentcat_name");
                            String  user_name= jsonObject2.getString("user_name");
                            String  qty= jsonObject2.getString("qty");
                            vendorcases.setAddress(address);
                            vendorcases.setAlternatemobile(alternate_mobile);
                            vendorcases.setCust_mobile(mobile);
                            vendorcases.setDescription(Discription);
                            vendorcases.setDate(date);
                            vendorcases.setCategoryname(title);
                            vendorcases.setParentcat(parentcat_name);
                            vendorcases.setUsername(user_name);
                            vendorcases.setBookingid(booking_id);
                            vendorcases.setQty(qty);

                            Sharelist.add(vendorcases);
                        }
                        VendorcasesAdapter adapter = new VendorcasesAdapter( Sharelist, getContext(),"close");
                        recycler1.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
                        recycler1.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    } else {
                        //Toast.makeText(getApplicationContext(), "" + obj.getString("Message"), Toast.LENGTH_SHORT).show();
                        pDialog.dismiss();

                    }

                } catch (Exception e) {

                    e.printStackTrace();
                    pDialog.dismiss();

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                // Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_SHORT).show();
                pDialog.dismiss();


            }
        }) {

        };
        requestQueue.add(stringRequest);


    }

}
