package com.example.sugorenge;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.sugorenge.MainActivity.BASE_URL;

public class OTPscreen extends AppCompatActivity {
    EditText ed1,ed2,ed3,ed4;
    TextView sentto,otp;
    Button verify;
    String mobbile;
    String role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpscreen);

        verify=findViewById(R.id.verify);
        ed1=findViewById(R.id.ed1);
        ed2=findViewById(R.id.ed2);
        ed3=findViewById(R.id.ed3);
        ed4=findViewById(R.id.ed4);
        otp=findViewById(R.id.otp);
        mobbile=getIntent().getExtras().getString("mobbile");
        role=getIntent().getExtras().getString("role");
         sentto=findViewById(R.id.sentto);
        sentto.setText("Sent to : "+mobbile);
         ed1.addTextChangedListener(new GenericTextWatcher(ed2, ed1));
         ed2.addTextChangedListener(new GenericTextWatcher(ed3, ed1));
         ed3.addTextChangedListener(new GenericTextWatcher(ed4, ed2));
         ed4.addTextChangedListener(new GenericTextWatcher(ed4, ed3));
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apicall();

            }
        });

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mobile", mobbile);

        editor.commit();
    }


    private void apicall() {

        String otp = ed1.getText().toString() + ed2.getText().toString() + ed3.getText().toString() + ed4.getText().toString();
        if (otp.length() != 4) {
            Toast.makeText(this, "Enter 4 Digit OTP", Toast.LENGTH_SHORT).show();
        } else {


            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            progressDialog.setCancelable(false);


            String url = BASE_URL + "user/otpverify";
            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(getApplicationContext());

            String username = sharedPreferences.getString("username", null);
            RequestQueue queue = Volley.newRequestQueue(this);


            Map<String, String> params = new HashMap();
            params.put("mobile", mobbile);
            params.put("otp", otp);

            JSONObject parameters = new JSONObject(params);

              JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //Toast.makeText(OTPscreen.this, "" + response, Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject obj = new JSONObject(String.valueOf(response));

                        int r_code = obj.getInt("status");
                        if (r_code == 1) {
                            // String otp = obj.getString("OTP");
                            if (obj.getString("usertype").equalsIgnoreCase("1")) {
                                progressDialog.dismiss();

                                if (role.equalsIgnoreCase("2")) {

                                    Intent in = new Intent(OTPscreen.this, ThanksVendor.class);
                                    in.putExtra("mobbile", mobbile);
                                    in.putExtra("role", role);

                                    startActivity(in);
                                    finish();
                                    Toast.makeText(OTPscreen.this, "" + obj.getString("message"), Toast.LENGTH_SHORT).show();

                                } else {

                                    Intent in = new Intent(OTPscreen.this, Maindrawer.class);
                                    in.putExtra("mobbile", mobbile);
                                    in.putExtra("role", role);
                                    startActivity(in);
                                    finish();
                                    Toast.makeText(OTPscreen.this, "" + obj.getString("message"), Toast.LENGTH_SHORT).show();

                                }


                            } else {

                                Intent in = new Intent(OTPscreen.this, Signup.class);
                                in.putExtra("mobbile", mobbile);
                                in.putExtra("role", role);
                                startActivity(in);
                                finish();

                                progressDialog.dismiss();
                            }


                        } else {

                            Toast.makeText(OTPscreen.this, "" + obj.getString("message"), Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();


                }
            });
             queue.add(stringRequest);

        }
    }

    public void Resend(View view) {



                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                progressDialog.setCancelable(false);


                String url = BASE_URL + "user/resendotp";
                SharedPreferences sharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(getApplicationContext());

                String username = sharedPreferences.getString("username", null);
                RequestQueue queue = Volley.newRequestQueue(this);


                Map<String, String> params = new HashMap();
                params.put("mobile", mobbile);

                JSONObject parameters = new JSONObject(params);

                JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(OTPscreen.this, "" + response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject obj = new JSONObject(String.valueOf(response));

                            int r_code = obj.getInt("status");
                            if (r_code == 1) {
                                // String otp = obj.getString("OTP");
                                progressDialog.dismiss();
                                        Toast.makeText(OTPscreen.this, "" + obj.getString("message"), Toast.LENGTH_SHORT).show();




                            } else {

                                Toast.makeText(OTPscreen.this, "" + obj.getString("message"), Toast.LENGTH_SHORT).show();
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

                        Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();


                    }
                });
                queue.add(stringRequest);

            }






    public class GenericTextWatcher implements TextWatcher {
        private EditText etPrev;
        private EditText etNext;

        public GenericTextWatcher(EditText etNext, EditText etPrev) {
            this.etPrev = etPrev;
            this.etNext = etNext;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            if (text.length() == 1)
                etNext.requestFocus();
            else if (text.length() == 0)
                etPrev.requestFocus();
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        }
    }
    public void back(View view) {
        onBackPressed();
    }
}
