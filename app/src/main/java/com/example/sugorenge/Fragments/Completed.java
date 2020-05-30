package com.example.sugorenge.Fragments;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.sugorenge.Adapter.MybookingsAdapter;
import com.example.sugorenge.R;
import com.example.sugorenge.model.BookinggetModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.sugorenge.MainActivity.BASE_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class Completed extends Fragment {

    RecyclerView recycler1;
    public Completed() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_completed, container, false);
        recycler1=view.findViewById(R.id.recycler1);
        Submit();
        return  view;
    }

    private void Submit() {

        final ProgressDialog pDialog = new ProgressDialog(getContext(), R.style.MyTheme);
        pDialog.setMessage("Loading....");
        pDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Large);

        pDialog.setCancelable(false);
        pDialog.show();

        String url = BASE_URL+"booking/allbooking";
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

                        ArrayList<BookinggetModel> Sharelist = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {

                            BookinggetModel bookinggetModel=new BookinggetModel();
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            String  cat_id= jsonObject2.getString("product_id");
                            String  date= jsonObject2.getString("date");
                            String  time= jsonObject2.getString("time");
                            String  price= jsonObject2.getString("price");
                            String  status= jsonObject2.getString("status");
                            String  Discription= jsonObject2.getString("description");
                            String  imgIcon= jsonObject2.getString("category_main_icon");
                            String  title= jsonObject2.getString("category_name");
                            String  close_status= jsonObject2.getString("close_status");
                            String  parentcat_name= jsonObject2.getString("parentcat_name");
                            String  address= jsonObject2.getString("address");
                            String  id= jsonObject2.getString("id");
                            String  mobile= jsonObject2.getString("mobile");
                            String  qty= jsonObject2.getString("qty");

                            if (close_status.equalsIgnoreCase("1"))
                            {



                                bookinggetModel.setId(cat_id);
                                bookinggetModel.setDate(date);
                                bookinggetModel.setTime(time);
                                bookinggetModel.setPrice(price);
                                bookinggetModel.setStatus(status);
                                bookinggetModel.setDiscription(Discription);
                                bookinggetModel.setIconimage(imgIcon);
                                bookinggetModel.setTitle(title);
                                bookinggetModel.setParentcatname(parentcat_name);
                                bookinggetModel.setAddress(address);
                                bookinggetModel.setBookingid(id);
                                bookinggetModel.setMobile(mobile);
                                bookinggetModel.setQty(qty);
                                Sharelist.add(bookinggetModel);
                            }
                        }
                        MybookingsAdapter adapter = new MybookingsAdapter( Sharelist, getContext());
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
