package com.example.sugorenge;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sugorenge.model.Vendorcases;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.sugorenge.MainActivity.BASE_URL;

public class BookingConfirmation extends AppCompatActivity {
    EditText sname, sprice, qty, discription;
    TextView subtotal;
    String price;
    ImageView minus;
    LinearLayout proceedlayout;

    String namee;
    String emaill;
    String adresss, mobileget;
    EditText nameew, altmobile, mobile, adress, email;
    String id;
    int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmation);
        qty = findViewById(R.id.qty);
        sprice = findViewById(R.id.sprice);
        sname = findViewById(R.id.sname);
        subtotal = findViewById(R.id.subtotal);
        minus = findViewById(R.id.minus);
        proceedlayout = findViewById(R.id.proceedlayout);


        String name = getIntent().getExtras().getString("p_name");
        id = getIntent().getExtras().getString("id");
        price = getIntent().getExtras().getString("price");
        value = Integer.parseInt(price);
        apicall();
        sname.setText(name);
        sprice.setText(price);
        qty.setText("1");
        subtotal.setText("Subtotal  ₹" + price);
        proceedlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog alertDialog = new AlertDialog.Builder(BookingConfirmation.this).create();
                LayoutInflater inflater = getLayoutInflater();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                View convertView = inflater.inflate(R.layout.leadsubmit, null);
                Button btn1 = convertView.findViewById(R.id.btn1);
                final ImageButton add = convertView.findViewById(R.id.add);
                ImageButton edit = convertView.findViewById(R.id.edit);
                final ImageButton minus = convertView.findViewById(R.id.minus);
                nameew = convertView.findViewById(R.id.name);
                mobile = convertView.findViewById(R.id.mobile);
                altmobile = convertView.findViewById(R.id.altmobile);
                adress = convertView.findViewById(R.id.adress);
                email = convertView.findViewById(R.id.email);
                discription = convertView.findViewById(R.id.discription);
                nameew.setText(namee);
                email.setText(emaill);
                mobile.setText(mobileget);
                adress.setText(adresss);

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        altmobile.setVisibility(View.VISIBLE);
                        minus.setVisibility(View.VISIBLE);
                        add.setVisibility(View.GONE);
                    }
                });
                minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        altmobile.setVisibility(View.GONE);
                        minus.setVisibility(View.GONE);
                        add.setVisibility(View.VISIBLE);
                    }
                });

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adress.setEnabled(true);

                    }
                });

                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if (discription.getText().toString().equalsIgnoreCase("")) {

                            discription.setError("required");
                        } else {
                            alertDialog.dismiss();
                            Apicall();

                        }


                    }
                });

                alertDialog.setView(convertView);
                alertDialog.show();


            }
        });
    }

    private void Apicall() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);


        final String url = BASE_URL + "booking/addbooking";
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());

        final String mobile = sharedPreferences.getString("mobile", null);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        Map<String, String> params = new HashMap();
        params.put("mobile", mobile);
        params.put("address", adress.getText().toString());
        params.put("qty", qty.getText().toString());
        params.put("alternate_mobile", altmobile.getText().toString());
        params.put("price", "" + Integer.parseInt(price) * Integer.parseInt(qty.getText().toString()));
        params.put("productid", id);
        params.put("description", discription.getText().toString());


        JSONObject parameters = new JSONObject(params);


        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                // Toast.makeText(BookingConfirmation.this, ""+response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));
                    JSONArray jsonArray;
                    String urll = null;
                    String
                            parentcat_name = null, qty = null,
                            mobile = null,
                            address = null,
                            alternate_mobile = null,
                            description = null,
                            price = null,
                            date = null,
                            category_name = null,
                            booingid = null;


                    jsonArray = obj.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                        if (i == 1) {

                            JSONObject jsonArray2 = jsonObject2.getJSONObject("payment_request");

                            urll = jsonArray2.getString("longurl");

                        } else {

                            parentcat_name = jsonObject2.getString("parentcat_name");
                            qty = jsonObject2.getString("qty");
                            mobile = jsonObject2.getString("mobile");
                            address = jsonObject2.getString("address");
                            alternate_mobile = jsonObject2.getString("alternate_mobile");
                            price = jsonObject2.getString("price");
                            date = jsonObject2.getString("date");
                            category_name = jsonObject2.getString("category_name");
                            description = jsonObject2.getString("description");
                            booingid = jsonObject2.getString("id");


                        }


                    }


                    progressDialog.dismiss();

                    Intent intent = new Intent(BookingConfirmation.this, PaymentWebview.class);
                    intent.putExtra("parentcat_name", parentcat_name);
                    intent.putExtra("qty", qty);
                    intent.putExtra("mobile", mobile);
                    intent.putExtra("address", address);
                    intent.putExtra("alternate_mobile", alternate_mobile);
                    intent.putExtra("description", description);
                    intent.putExtra("price", price);
                    intent.putExtra("date", date);
                    intent.putExtra("category_name", category_name);
                    intent.putExtra("url", urll);
                    intent.putExtra("paymenttype", "order");
                    intent.putExtra("booingid", booingid);
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

                //Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_SHORT).show();


            }
        }) {

        };
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    private void apicall() {


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);


        String url = BASE_URL + "user/userbyid";
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());

        final String mobile = sharedPreferences.getString("mobile", null);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        Map<String, String> params = new HashMap();
        params.put("mobile", mobile);


        JSONObject parameters = new JSONObject(params);


        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

//                Toast.makeText(Profile.this, ""+response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));

                    int r_code = obj.getInt("status");
                    if (r_code == 1) {

                        JSONObject jsonArray = obj.getJSONObject("data");


                        namee = jsonArray.getString("name");
                        emaill = jsonArray.getString("email");
                        adresss = jsonArray.getString("address");
                        mobileget = jsonArray.getString("mobile");

                        progressDialog.dismiss();


                    } else {

                        Toast.makeText(BookingConfirmation.this, "" + obj.getString("message"), Toast.LENGTH_SHORT).show();

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

                //Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_SHORT).show();


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

    public void addqty(View view) {

        value = Integer.parseInt(qty.getText().toString());
        value++;
        qty.setText("" + value);
        subtotal.setText("Subtotal  ₹" + Float.parseFloat(price) * value);

        minus.setVisibility(View.VISIBLE);


    }

    public void minusqty(View view) {

        value = Integer.parseInt(qty.getText().toString());
        value--;
        if (value > 0) {
            qty.setText("" + value);
            subtotal.setText("Subtotal  ₹" + Float.parseFloat(price) * value);

        } else {
            minus.setVisibility(View.INVISIBLE);
        }

    }

    public void back(View view) {
        onBackPressed();
    }
}
