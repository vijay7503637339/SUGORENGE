package com.example.sugorenge;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.sugorenge.MainActivity.BASE_URL;

public class ThanksVendor extends AppCompatActivity {

    LinearLayout movie_tab,mainlayout;
    String mobbile,role;
    Button makepayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks_vendor);
        mainlayout=findViewById(R.id.mainlayout);
        makepayment=findViewById(R.id.makepayment);
        mobbile=getIntent().getExtras().getString("mobbile");
        role=getIntent().getExtras().getString("role");

        makepayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paymentapi();
            }
        });
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(ThanksVendor.this);
        String status = sharedPreferences.getString("status", "");
        if (status.equalsIgnoreCase("1"))
        {
            Intent in = new Intent(ThanksVendor.this, VendorDashBoard.class);
            in.putExtra("mobbile", mobbile);
            in.putExtra("role", role);

            startActivity(in);
            finish();

        }
        else {
            apicall();

        }



    }

    private void Paymentapi() {


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);


        String url = BASE_URL + "user/venderpayment";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        Map<String, String> params = new HashMap();
        params.put("mobile", mobbile);

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                // Toast.makeText(MainActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));
                    progressDialog.dismiss();

                       JSONObject jsonObject2 = obj.getJSONObject("payment_request");
                       String longurl= jsonObject2.getString("longurl");
                    //Toast.makeText(ThanksVendor.this, ""+longurl, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ThanksVendor.this, PaymentWebview.class);
                     intent.putExtra("url", longurl);
                    intent.putExtra("paymenttype", "vendor");
                    startActivity(intent);


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

    private void apicall( ) {




            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            progressDialog.setCancelable(false);


            String url = BASE_URL + "vendor/vendorverify";
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


            Map<String, String> params = new HashMap();
            params.put("mobile", mobbile);

            JSONObject parameters = new JSONObject(params);

             JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    // Toast.makeText(MainActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject obj = new JSONObject(String.valueOf(response));

                        int r_code = obj.getInt("status");
                        JSONObject jsonObject=obj.getJSONObject("data");
                        String c_id = jsonObject.getString("category_id");
                                 if (c_id.equalsIgnoreCase("125"))
                                {

                                    if (r_code==1) {


                                        Intent in = new Intent(ThanksVendor.this, VendorDashBoard.class);
                                        in.putExtra("mobbile", mobbile);
                                        in.putExtra("role", role);

                                        startActivity(in);
                                        finish();
                                        Toast.makeText(ThanksVendor.this, ""+obj.getString("message"), Toast.LENGTH_SHORT).show();


                                        progressDialog.dismiss();
                                    }
                                    else
                                    {
                                        mainlayout.setVisibility(View.VISIBLE);
                                        progressDialog.dismiss();
                                    }
                                }
                                else
                                {

                                    Intent in = new Intent(ThanksVendor.this, VendorDashBoard.class);
                                    in.putExtra("mobbile", mobbile);
                                    in.putExtra("role", role);

                                    startActivity(in);
                                    finish();
                                    progressDialog.dismiss();
                                }

                        SharedPreferences sharedPreferences = PreferenceManager
                                .getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("mobbile",  mobbile);
                        editor.putString("role",  role);
                        editor.putString("status", String.valueOf(r_code));
                        editor.commit();
                       /* */

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


