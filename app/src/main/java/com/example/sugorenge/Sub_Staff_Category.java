package com.example.sugorenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sugorenge.Adapter.ShareboxxAdapter;
import com.example.sugorenge.Adapter.SubcategoryAdapter;
import com.example.sugorenge.model.ShareboxModel;
import com.example.sugorenge.model.Subcategorymodel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.sugorenge.Adapter.ShareboxxAdapter.IMAGE_BASE_URL;
import static com.example.sugorenge.MainActivity.BASE_URL;

public class Sub_Staff_Category extends AppCompatActivity {

    RecyclerView recycler1;
    String id,name;
    ImageView himage,foimage;
    TextView hedtext,bottomtext;
    boolean value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub__staff__category);
        recycler1=findViewById(R.id.recycler1);
        himage=findViewById(R.id.himage);
        foimage=findViewById(R.id.foimage);
        hedtext=findViewById(R.id.hedtext);
        bottomtext=findViewById(R.id.bottomtext);
        id=getIntent().getExtras().getString("id");
        name=getIntent().getExtras().getString("name");
        if (name.equalsIgnoreCase("Air Conditioner"))
        {

            value=true;
        }
        else
        {
            value=false;

        }        hedtext.setText(name);
        bottomtext.setText(name);
       Submit();
    }


    private void Submit() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);

        String url = BASE_URL+"/category/parentcat";
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(Sub_Staff_Category.this);

        String username = sharedPreferences.getString("username", null);


        RequestQueue requestQueue = Volley.newRequestQueue(Sub_Staff_Category.this);

        Map<String, String> params = new HashMap();
        params.put("parent", id);


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
                        progressDialog.dismiss();

                        JSONArray jsonArray = obj.getJSONArray("data");
                        String  himagee= obj.getString("header_img");
                        String  fimage= obj.getString("footer_img");

                        Picasso.with(Sub_Staff_Category.this)
                                .load(  IMAGE_BASE_URL+himagee)
                                .into( himage);

                        Picasso.with(Sub_Staff_Category.this)
                                .load(  IMAGE_BASE_URL+fimage)
                                .into( foimage);

                        ArrayList<Subcategorymodel> Sharelist = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {

                            Subcategorymodel ShareboxModel=new Subcategorymodel();
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            String  cat_id= jsonObject2.getString("cat_id");
                            String  name= jsonObject2.getString("name");

                            ShareboxModel.setId(cat_id);
                             ShareboxModel.setStatus(name);
                            Sharelist.add(ShareboxModel);
                        }
                        SubcategoryAdapter adapter = new SubcategoryAdapter( Sharelist,Sub_Staff_Category.this,value);
                        recycler1.setLayoutManager(new LinearLayoutManager(Sub_Staff_Category.this,RecyclerView.VERTICAL,false));
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
