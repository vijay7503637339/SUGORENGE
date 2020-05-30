package com.example.sugorenge.Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.andexert.library.RippleView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sugorenge.BookingConfirmation;
import com.example.sugorenge.MainActivity;
import com.example.sugorenge.OTPscreen;
import com.example.sugorenge.R;
import com.example.sugorenge.VendorDashBoard;
import com.example.sugorenge.model.BookinggetModel;
import com.example.sugorenge.model.Vendorcases;
import com.google.zxing.common.StringUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.sugorenge.Adapter.ShareboxxAdapter.IMAGE_BASE_URL;
import static com.example.sugorenge.MainActivity.BASE_URL;
import static com.facebook.FacebookSdk.getApplicationContext;

public class VendorcasesAdapter extends RecyclerView.Adapter<VendorcasesAdapter.ViewHolder> {

    ArrayList<Vendorcases> arrayList;
    Context context;
    String type;

    public VendorcasesAdapter(ArrayList<Vendorcases> arrayList, Context context,String type) {
        this.arrayList = arrayList;
        this.context = context;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mybookinglayout, parent, false);
        return new ViewHolder(v);


    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.text.setText("Mr. " + arrayList.get(position).getUsername());
        holder.prize.setText("+91 " + arrayList.get(position).getCust_mobile());
        holder.date.setText(arrayList.get(position).getDate());
        holder.idservice.setText("" + arrayList.get(position).getParentcat() + " for " + arrayList.get(position).getCategoryname());


        if (type.equalsIgnoreCase("active"))

        {
        holder.viewdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                LayoutInflater inflater = LayoutInflater.from(context);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                View convertView = inflater.inflate(R.layout.leadsubmit2, null);

                Button login2 = convertView.findViewById(R.id.login2);
                TextView nameew = convertView.findViewById(R.id.name);
                TextView qty = convertView.findViewById(R.id.qty);
                TextView mobile = convertView.findViewById(R.id.mobile);
                TextView adress = convertView.findViewById(R.id.adress);
                TextView servicetype = convertView.findViewById(R.id.servicetypee);
                ImageView cross = convertView.findViewById(R.id.cross);
                TextView discription = convertView.findViewById(R.id.discription);
                //  EditText discription = convertView.findViewById(R.id.discription);
                nameew.setText(arrayList.get(position).getUsername());
                qty.setText(arrayList.get(position).getQty());
                adress.setText(arrayList.get(position).getAddress());
                mobile.setText(arrayList.get(position).getCust_mobile()+" , "+arrayList.get(position).getAlternatemobile());
                discription.setText(arrayList.get(position).getDescription());
                servicetype.setText(arrayList.get(position).getParentcat() + " for " + arrayList.get(position).getCategoryname());
                cross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                login2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        apicall(arrayList.get(position).getBookingid(), arrayList.get(position).getCust_mobile());

                        alertDialog.dismiss();


                    }
                });

                alertDialog.setView(convertView);
                alertDialog.setCancelable(false);
                alertDialog.show();

            }
        });
        }



        Picasso.with(context)
                .load(R.drawable.booked)
                .into(holder.greywebicon);
        holder.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + arrayList.get(position).getCust_mobile()));
                context.startActivity(intent);


            }
        });


    }

    private void apicall(final String bookingid, final String custmobile) {


        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);


        String url = BASE_URL + "booking/closebookingpinsend";
          RequestQueue requestQueue = Volley.newRequestQueue(context);


        Map<String, String> params = new HashMap();
        params.put("booking_id", bookingid);
        params.put("user_mobile", custmobile);

        JSONObject parameters = new JSONObject(params);


        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //Toast.makeText(MainActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));

                    int r_code = obj.getInt("status");
                    if (r_code == 1) {

                        Toast.makeText(context, "" + obj.getString("message"), Toast.LENGTH_SHORT).show();

                        nextAlert(bookingid,custmobile);


                        progressDialog.dismiss();
                    } else {

                        Toast.makeText(context, "" + obj.getString("message"), Toast.LENGTH_SHORT).show();
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

                Toast.makeText(context, "" + error, Toast.LENGTH_SHORT).show();


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


    private void nextAlert(final String bookingid, final String usermobile) {


        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        LayoutInflater inflater = LayoutInflater.from(context);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View convertView = inflater.inflate(R.layout.bookingverification, null);
        Button login2 = convertView.findViewById(R.id.login2);
        TextView resend = convertView.findViewById(R.id.resend);
        final EditText pin = convertView.findViewById(R.id.pin);

         login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                alertDialog.dismiss();
             String pinstring = pin.getText().toString();


                apicall2(bookingid, pinstring);

                alertDialog.dismiss();


            }
        });
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendpin(bookingid,usermobile);
            }


        });


        alertDialog.setView(convertView);
        alertDialog.show();
        alertDialog.setCancelable(false);

    }
    private void resendpin(String bookingid, String usermobile) {

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);


        String url = BASE_URL + "booking/closebookingpinsendresend";
             RequestQueue queue = Volley.newRequestQueue(context);


        Map<String, String> params = new HashMap();
        params.put("booking_id", bookingid);
        params.put("user_mobile", usermobile);

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
                        Toast.makeText(context, "" + obj.getString("message"), Toast.LENGTH_SHORT).show();




                    } else {

                        Toast.makeText(context, "" + obj.getString("message"), Toast.LENGTH_SHORT).show();
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

                Toast.makeText(context, "" + error, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();


            }
        });
        queue.add(stringRequest);
    }
    private void apicall2(String bookingid, String pin) {


        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);


        String url = BASE_URL + "booking/verifypinbooking";
          RequestQueue requestQueue = Volley.newRequestQueue(context);


        Map<String, String> params = new HashMap();
        params.put("booking_id", bookingid);
        params.put("verify_pin", pin);

        JSONObject parameters = new JSONObject(params);


        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //Toast.makeText(MainActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject obj = new JSONObject(String.valueOf(response));

                    int r_code = obj.getInt("status");
                    if (r_code == 1) {


                        Toast.makeText(context, "" + obj.getString("message"), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(context, VendorDashBoard.class);
                        ((Activity)context).finish();
                        context.startActivity(intent);
                        progressDialog.dismiss();
                    } else {

                        Toast.makeText(context, "" + obj.getString("message"), Toast.LENGTH_SHORT).show();
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

                Toast.makeText(context, "" + error, Toast.LENGTH_SHORT).show();


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

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text, prize, date, idservice,qty;
        Button viewdetail;

        ImageView greywebicon;
        LinearLayout phone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            text = itemView.findViewById(R.id.name);
            greywebicon = itemView.findViewById(R.id.greywebicon);
            prize = itemView.findViewById(R.id.prize);
            date = itemView.findViewById(R.id.date);
            idservice = itemView.findViewById(R.id.idservice);
            viewdetail = itemView.findViewById(R.id.viewdetail);
            phone = itemView.findViewById(R.id.phone);


        }
    }


}
