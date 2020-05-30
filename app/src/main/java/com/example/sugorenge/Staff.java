package com.example.sugorenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sugorenge.Adapter.ShareboxxAdapter;
import com.example.sugorenge.model.ShareboxModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.sugorenge.MainActivity.BASE_URL;

public class Staff extends AppCompatActivity {
    LinearLayout hsmgt, ptcare, acservice, realesate, gardningm, showrooms, facility, hotels;
    RecyclerView recycler1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        recycler1 = findViewById(R.id.recycler1);
       /* realesate =  findViewById(R.id.realesate);
        gardningm =  findViewById(R.id.gardning);
        acservice=  findViewById(R.id.acservice);
        hsmgt = findViewById(R.id.hsmgt);
        showrooms =  findViewById(R.id.showrooms);
        hotels =findViewById(R.id.hotels);
*//*
        facility = findViewById(R.id.facility);
        ptcare = findViewById(R.id.ptcare);
  */
        Submit();
/*
        hsmgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getApplicationContext(), Hospitalmanagement.class);
                startActivity(in);
            }
        });


        acservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getApplicationContext(), ACservice.class);
                startActivity(in);
            }
        });
        realesate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getApplicationContext(), Realestateservices.class);
                startActivity(in);
            }
        });
        gardningm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getApplicationContext(), Gardning.class);
                startActivity(in);
            }
        });
        showrooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getApplicationContext(), Showrooms.class);
                startActivity(in);
            }
        });

        hotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getApplicationContext(), HotelsandResort.class);
                startActivity(in);
            }
        });

        facility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getApplicationContext(), FacilityManagement.class);
                startActivity(in);
            }
        });

        ptcare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getApplicationContext(), Patientcare.class);
                startActivity(in);
            }
        });*/

    }

    public void back(View view) {
        onBackPressed();
    }

    private void Submit() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);


        String url = BASE_URL + "/category/maincategory";
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(Staff.this);

        String username = sharedPreferences.getString("username", null);


        RequestQueue requestQueue = Volley.newRequestQueue(Staff.this);

        Map<String, String> params = new HashMap();

        JSONObject parameters = new JSONObject(params);


        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //     Toast.makeText(Exixtinguser.this, ""+response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));

                    int r_code = obj.getInt("status");
                    if (r_code == 1) {
                        //  Toast.makeText(getApplicationContext(), "" + obj.getString("Message"), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        JSONArray jsonArray = obj.getJSONArray("data");


                        ArrayList<ShareboxModel> Sharelist = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {

                            ShareboxModel ShareboxModel = new ShareboxModel();
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            String cat_id = jsonObject2.getString("cat_id");
                            String name = jsonObject2.getString("name");
                            String main_img = jsonObject2.getString("main_img");

                            if(name.equalsIgnoreCase("facility management")||name.equalsIgnoreCase("patient care")||name.equalsIgnoreCase("air conditioner"))
                            {


                            }
                            else
                            {
                                ShareboxModel.setId(cat_id);
                                ShareboxModel.setImage(main_img);
                                ShareboxModel.setStatus(name);
                                Sharelist.add(ShareboxModel);
                            }

                            }
                        ShareboxxAdapter adapter = new ShareboxxAdapter(Sharelist, Staff.this);
                        recycler1.setLayoutManager(new GridLayoutManager(Staff.this, 4));
                        recycler1.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    } else {
                        //Toast.makeText(getApplicationContext(), "" + obj.getString("Message"), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();  }

                } catch (Exception e) {

                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                // Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {

        };
        requestQueue.add(stringRequest);


    }

}
