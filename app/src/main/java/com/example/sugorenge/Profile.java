package com.example.sugorenge;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmadrosid.svgloader.SvgLoader;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sugorenge.model.Subcategorymodel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.sugorenge.MainActivity.BASE_URL;

public class Profile extends AppCompatActivity {
    TextView name, number, email, paddress,textnamw,emailtext;
    ImageView imageprofile;
    TextView home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profiledemoui);
        name = findViewById(R.id.name);
        number = findViewById(R.id.number);
        paddress = findViewById(R.id.paddress);
        email = findViewById(R.id.email);
        textnamw = findViewById(R.id.textnamw);
        emailtext = findViewById(R.id.emailtext);
        imageprofile = findViewById(R.id.imageprofile);
        home = findViewById(R.id.home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(getApplicationContext());

                final String mobile = sharedPreferences.getString("mobbile", null);
                final String role = sharedPreferences.getString("role", null);

                Intent in = new Intent(Profile.this, Maindrawer.class);
                in.putExtra("mobbile",  mobile);
                in.putExtra("role", role);
                startActivity(in);


            }
        });


        /*SvgLoader.pluck()
                .with(this)
                 .load("https://joeschmoe.io/api/v1/Ajaykumar", imageprofile);
 */

        apicall();

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
                        progressDialog.dismiss();


                            String namee = jsonArray.getString("name");
                            String emaill = jsonArray.getString("email");
                            String adresss = jsonArray.getString("address");
                            name.setText("Mr. "+namee);
                            number.setText("+91 "+mobile);
                            paddress.setText(adresss);
                            email.setText(emaill);
                        emailtext.setText(emaill);
                        textnamw.setText("Mr. "+namee);




                    } else {

                        Toast.makeText(Profile.this, "" + obj.getString("message"), Toast.LENGTH_SHORT).show();

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

    public void back(View view) {
        onBackPressed();
    }


}
