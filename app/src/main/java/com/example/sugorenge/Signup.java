package com.example.sugorenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sugorenge.Adapter.ShareboxxAdapter;
import com.example.sugorenge.model.ShareboxModel;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

import static com.example.sugorenge.MainActivity.BASE_URL;

public class Signup extends AppCompatActivity {
    EditText name, Email, Adress;
    String mobbile;
    EditText qualification, experience, location, Servicetype;
    SpinnerDialog spinnerDialog;
    String slectedmainid, role;
    Button login;
    TextView Sta;
    TextInputLayout txt1, txt2, txt3, txt4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = findViewById(R.id.name);
        Email = findViewById(R.id.Email);
        login = findViewById(R.id.register);
        Adress = findViewById(R.id.Adress);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        txt4 = findViewById(R.id.txt4);
        Sta = findViewById(R.id.Sta);
        Servicetype = findViewById(R.id.Servicetype);
        qualification = findViewById(R.id.qualification);
        experience = findViewById(R.id.experience);
        location = findViewById(R.id.location);
        mobbile = getIntent().getExtras().getString("mobbile");
        role = getIntent().getExtras().getString("role");

        if (role.equalsIgnoreCase("1")) {
            txt1.setVisibility(View.GONE);
            txt2.setVisibility(View.GONE);
            txt3.setVisibility(View.GONE);
            txt4.setVisibility(View.GONE);
        } else {

            Sta.setText("One More Step TO Start Earning");
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apicall();
                if (role.equalsIgnoreCase("2")) {
                    apicall2();

                }


            }
        });
        Submit();
        Servicetype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerDialog.showSpinerDialog();
            }
        });


    }

    private void apicall() {

        if (name.getText().toString().equalsIgnoreCase("")) {
            name.setError("Required");
        } else if (Email.getText().toString().equalsIgnoreCase("")) {
            Email.setError("Required");
        } else if (Adress.getText().toString().equalsIgnoreCase("")) {
            Adress.setError("Required");
        } else {


            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            progressDialog.setCancelable(false);


            String url = BASE_URL + "user/userdataupdate";
            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(getApplicationContext());

            String username = sharedPreferences.getString("username", null);
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


            Map<String, String> params = new HashMap();
            params.put("mobile", mobbile);
            params.put("address", Adress.getText().toString());
            params.put("name", name.getText().toString());
            params.put("email", Email.getText().toString());
            params.put("role",role);

            JSONObject parameters = new JSONObject(params);


            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    //Toast.makeText(MainActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject obj = new JSONObject(String.valueOf(response));

                        int r_code = obj.getInt("status");
                        if (r_code == 1) {
                            if (role.equalsIgnoreCase("1")) {
                                progressDialog.dismiss();
                                Intent in = new Intent(Signup.this, Maindrawer.class);
                                in.putExtra("mobbile", mobbile);
                                in.putExtra("role", role);
                                startActivity(in);
                                finish();
                                Toast.makeText(Signup.this, "" + obj.getString("message"), Toast.LENGTH_SHORT).show();
                             }

                        } else {

                            Toast.makeText(Signup.this, "" + obj.getString("message"), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    } catch (Exception e) {

                        progressDialog.dismiss();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    progressDialog.dismiss();

                    Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_SHORT).show();
                }
            }) {

            };
            requestQueue.add(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    60000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
            );

        }
    }


    private void apicall2() {

        if (qualification.getText().toString().equalsIgnoreCase("")) {
            qualification.setError("Required");
        } else if (experience.getText().toString().equalsIgnoreCase("")) {
            experience.setError("Required");
        } else if (location.getText().toString().equalsIgnoreCase("")) {
            location.setError("Required");
        } else if (Servicetype.getText().toString().equalsIgnoreCase("")) {
            Servicetype.setError("Required");
        } else {


            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            progressDialog.setCancelable(false);

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            String url = BASE_URL + "vendor/vendorinfoadd";
            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(getApplicationContext());


            Map<String, String> params = new HashMap();
            params.put("experience", experience.getText().toString());
            params.put("service_id", slectedmainid);
            params.put("job_location", location.getText().toString());
            params.put("qualification", qualification.getText().toString());
            params.put("mobile", mobbile);

            JSONObject parameters = new JSONObject(params);
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    //Toast.makeText(MainActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject obj = new JSONObject(String.valueOf(response));

                        int r_code = obj.getInt("status");
                        if (r_code == 1) {
                            progressDialog.dismiss();

                            Intent in = new Intent(Signup.this, ThanksVendor.class);
                            in.putExtra("mobbile", mobbile);
                            in.putExtra("role", role);
                            in.putExtra("slectedmainid", slectedmainid);
                            startActivity(in);
                            finish();
                            progressDialog.dismiss();
                            Toast.makeText(Signup.this, "" + obj.getString("message"), Toast.LENGTH_SHORT).show();

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(Signup.this, "" + obj.getString("message"), Toast.LENGTH_SHORT).show();

                        }

                    } catch (Exception e) {

                        progressDialog.dismiss();
                        e.printStackTrace();


                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    progressDialog.dismiss();

                    Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_SHORT).show();


                }
            }) {

            };
            requestQueue.add(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    60000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
            );

        }
    }

    private void Submit() {

        final ProgressDialog pDialog = new ProgressDialog(Signup.this, R.style.MyTheme);
        pDialog.setMessage("Loading....");
        pDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Large);

        pDialog.setCancelable(false);
        pDialog.show();

        String url = BASE_URL + "/category/maincategory";
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(Signup.this);

        String username = sharedPreferences.getString("username", null);


        RequestQueue requestQueue = Volley.newRequestQueue(Signup.this);

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
                        pDialog.dismiss();

                        JSONArray jsonArray = obj.getJSONArray("data");


                        ArrayList<String> namelist = new ArrayList<>();
                        final ArrayList<String> idlist = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            String cat_id = jsonObject2.getString("cat_id");
                            String name = jsonObject2.getString("name");
                            String main_img = jsonObject2.getString("main_img");

                            namelist.add(name);
                            idlist.add(cat_id);

                        }

                        spinnerDialog = new SpinnerDialog(Signup.this, namelist, "Select or Search State", R.style.DialogAnimations_SmileWindow, "Close  ");// With 	Animation
                        spinnerDialog.setCancellable(true); // for cancellable
                        spinnerDialog.setShowKeyboard(false);// for open keyboard by default


                        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                            @Override
                            public void onClick(String item, int position) {
                                slectedmainid = idlist.get(position);

                                Servicetype.setText(item);

                            }
                        });


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
