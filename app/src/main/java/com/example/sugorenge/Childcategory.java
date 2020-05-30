package com.example.sugorenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sugorenge.Adapter.ChildcategoryAdapter;
import com.example.sugorenge.model.Childcategorymodel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.sugorenge.MainActivity.BASE_URL;

public class Childcategory extends AppCompatActivity {
    String id,name;
    RecyclerView recycler1;
    LinearLayout proceedlayout;
    TextView subtotal,nametext;
    private MyInterface listner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_childcategory);
        recycler1 = findViewById(R.id.recycler1);
        proceedlayout = findViewById(R.id.proceedlayout);
        subtotal = findViewById(R.id.subtotal);
        nametext = findViewById(R.id.nametext);
        id = getIntent().getExtras().getString("id");
        name = getIntent().getExtras().getString("name");
        nametext.setText(name);
        Submit();
        listner = new MyInterface() {
            @Override
            public void foo(int count, int total) {

                proceedlayout.setVisibility(View.VISIBLE);

                if (total>0)
                {

                    subtotal.setText("Subtotal  ₹"+total);
                }
                else
                {
                    proceedlayout.setVisibility(View.GONE);

                }

            }
        };
        proceedlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void Submit() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);


        String url = BASE_URL + "/category/childcat";
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(Childcategory.this);

        String username = sharedPreferences.getString("username", null);


        RequestQueue requestQueue = Volley.newRequestQueue(Childcategory.this);

        Map<String, String> params = new HashMap();
        params.put("parent", id);


        JSONObject parameters = new JSONObject(params);


        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //  Toast.makeText(Childcategory.this, ""+response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));

                    int r_code = obj.getInt("status");
                    if (r_code == 1) {
                        //  Toast.makeText(getApplicationContext(), "" + obj.getString("Message"), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                        JSONArray jsonArray = obj.getJSONArray("data");
                        ArrayList<Childcategorymodel> Sharelist = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {

                            Childcategorymodel ShareboxModel = new Childcategorymodel();
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            String cat_id = jsonObject2.getString("cat_id");
                            String name = jsonObject2.getString("name");
                            String price = jsonObject2.getString("price");

                            ShareboxModel.setId(cat_id);
                            ShareboxModel.setStatus(name);
                            ShareboxModel.setPrice(price);
                            Sharelist.add(ShareboxModel);
                        }
                        ChildcategoryAdapter adapter = new ChildcategoryAdapter(Sharelist, Childcategory.this, listner);
                        recycler1.setLayoutManager(new LinearLayoutManager(Childcategory.this, RecyclerView.VERTICAL, false));
                        recycler1.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    } else {
                        //Toast.makeText(getApplicationContext(), "" + obj.getString("Message"), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }

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


    public void back(View view) {
        onBackPressed();
    }
}

