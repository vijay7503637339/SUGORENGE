package com.example.sugorenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText mobile;
    Button login,login2;
    private SignInButton googleSignInButton;
    private GoogleSignInClient googleSignInClient;
    ProgressDialog progressDialog;
    Boolean Vendor=false;

      public static final String BASE_URL = "https://imscertificate.com/servicehat/api/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mobile = findViewById(R.id.mobile);
        login = findViewById(R.id.login);
        login2 = findViewById(R.id.login2);
        googleSignInButton = findViewById(R.id.sign_in_button);


        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(MainActivity.this);
        String mobbile = sharedPreferences.getString("mobbile","");
        String role = sharedPreferences.getString("role","");
        if (!mobbile.equals("") && !role.equals("")){

            if (role.equalsIgnoreCase("1")) {
                Intent intent = new Intent(MainActivity.this, Maindrawer.class);
                finish();
                startActivity(intent);
            }

          else   if (role.equalsIgnoreCase("2")) {
                Intent intent = new Intent(MainActivity.this, VendorDashBoard.class);
                finish();
                startActivity(intent);
            }
        }
        else
        {
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                apicall("1");

                //MainActivity.this.overridePendingTransition(R.anim.translate_left_side, R.anim.translate_right_side);
            }
        });
        login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                apicall("2");

                //MainActivity.this.overridePendingTransition(R.anim.translate_left_side, R.anim.translate_right_side);
            }
        });


        googleSignInButton = findViewById(R.id.sign_in_button);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 101);
            }
        });


        mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() != 10) {

                    if (login2.getVisibility()==View.VISIBLE)
                    {
                      //  login2.setVisibility(View.GONE);
                    }
                    else if (login.getVisibility()==View.VISIBLE){
                      // login.setVisibility(View.GONE);

                    }
                } else {
                    if (Vendor)
                    {
                        login2.setVisibility(View.VISIBLE);
                    }
                    else {

                        login.setVisibility(View.VISIBLE);
                    }


                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }

            }
        });


    }

    private void apicall(final String role) {

        String mobil = mobile.getText().toString();
        if (mobil.length() != 10) {
            mobile.setError("Enter 10 Digit Number");
        } else {


                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                progressDialog.setCancelable(false);


            String url = BASE_URL + "user/adduser";
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


            Map<String, String> params = new HashMap();
            params.put("mobile", mobil);
            params.put("role", role);

            JSONObject parameters = new JSONObject(params);


            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                   // Toast.makeText(MainActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject obj = new JSONObject(String.valueOf(response));

                        int r_code = obj.getInt("status");
                        if (r_code==1) {




                            Intent in = new Intent(MainActivity.this, OTPscreen.class);
                            in.putExtra("mobbile", mobile.getText().toString());
                             in.putExtra("role", role);
                            startActivity(in);

                            finish();

                            progressDialog.dismiss();
                            } else {

                            Toast.makeText(MainActivity.this, ""+obj.getString("message"), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case 101:
                    try {
                        // The Task returned from this call is always completed, no need to attach
                        // a listener.
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        GoogleSignInAccount account = task.getResult(ApiException.class);

                        onLoggedIn(account);
                    } catch (ApiException e) {
                        // The ApiException status code indicates the detailed failure reason.
                        //Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
                        Toast.makeText(this, "signInResult:failed code=" + e.getStatusCode(), Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

    }

    private void onLoggedIn(GoogleSignInAccount googleSignInAccount) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra(ProfileActivity.GOOGLE_ACCOUNT, googleSignInAccount);
        startActivity(intent);
        // Toast.makeText(this, "Sucessfully Logged in", Toast.LENGTH_SHORT).show();
        // progressDialog.dismiss();
        finish();


    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount alreadyloggedAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (alreadyloggedAccount != null) {
            Toast.makeText(this, "Already Logged In", Toast.LENGTH_SHORT).show();
            onLoggedIn(alreadyloggedAccount);
        } else {
            //Toast.makeText(this, "Not Login", Toast.LENGTH_SHORT).show();
        }
    }


    public void vendor(View view) {


        login2.setVisibility(View.VISIBLE);
        login.setVisibility(View.GONE);
        Vendor=true;
    }
}
