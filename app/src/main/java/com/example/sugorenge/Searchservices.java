package com.example.sugorenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ethanhua.skeleton.Skeleton;
import com.example.sugorenge.Adapter.FAQAdapter;
import com.example.sugorenge.model.FAQModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.sugorenge.MainActivity.BASE_URL;

public class Searchservices extends AppCompatActivity {
    EditText search;
    private FAQAdapter adapter;
    private ArrayList<FAQModel> mFaqList = new ArrayList<>();
    private RecyclerView recyclarView;
    ProgressDialog pDialog;
    ImageView imagesearche;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchservices);
        search = findViewById(R.id.search);
        recyclarView = findViewById(R.id.recycler1);
        imagesearche = findViewById(R.id.imagesearche);
        faq();

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (adapter != null)
                    adapter.getFilter().filter(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length()>0)
                {
                    recyclarView.setVisibility(View.VISIBLE);
                    imagesearche.setVisibility(View.GONE);
                }
                else
                {
                    recyclarView.setVisibility(View.GONE);
                    imagesearche.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    private void faq() {


        pDialog = new ProgressDialog(Searchservices.this, R.style.MyTheme);
        pDialog.setMessage("Loading....");
        pDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Large);

        pDialog.setCancelable(false);
        pDialog.show();

        String url = BASE_URL + "category/getchildcategory";
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());

        String username = sharedPreferences.getString("username", null);


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        Map<String, String> params = new HashMap();
      /*  params.put("ServiceId", "1");

        JSONObject parameters = new JSONObject(params);
*/

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //    Toast.makeText(Searchservices.this, ""+response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));

                    int r_code = obj.getInt("status");
                    if (r_code == 1) {
                        //  Toast.makeText(getApplicationContext(), "" + obj.getString("Message"), Toast.LENGTH_SHORT).show();
                        pDialog.dismiss();

                        JSONArray jsonArray = obj.getJSONArray("data");


                        ArrayList<FAQModel> list = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {

                            FAQModel faqmodel = new FAQModel();
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            String name = jsonObject2.getString("name");
                            String cat_id = jsonObject2.getString("cat_id");
                            String price = jsonObject2.getString("price");

                            faqmodel.setName(name);
                            faqmodel.setId(cat_id);
                            faqmodel.setPrice(price);
                            list.add(faqmodel);
                        }
                        setList(list);

                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(getApplicationContext(), "" + obj.getString("Message"), Toast.LENGTH_SHORT).show();
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

    private void setList(ArrayList<FAQModel> list) {
        if (list != null && list.size() > 0) {
            if (adapter == null) {
                mFaqList.clear();
                mFaqList.addAll(list);
                adapter = new FAQAdapter(Searchservices.this, mFaqList);
                recyclarView.setLayoutManager(new LinearLayoutManager(Searchservices.this, RecyclerView.VERTICAL, false));
                recyclarView.setAdapter(adapter);

            } else {
                mFaqList.clear();
                mFaqList.addAll(list);
                adapter.notifyDataSetChanged();
            }
        }
    }


    public void back(View view) {
        onBackPressed();
    }
}
