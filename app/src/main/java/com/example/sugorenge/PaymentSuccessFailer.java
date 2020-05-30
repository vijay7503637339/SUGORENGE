package com.example.sugorenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PaymentSuccessFailer extends AppCompatActivity {


    String
            parentcat_name = null
            ,qty = null,
            mobile = null,
            address = null,
            alternate_mobile = null,
            description = null,
            price = null,
            date = null,
            category_name = null,
            booingid=null;

    TextView bottomtext,transactionStatus,tranactionAmount,textbootom;
    TextView oid,proname,totalprice,date_Txt,qtyy,bookingadd,contactnu;
    ImageView check;
    String paymenttype;
    String status;
    CardView orderdetailcard;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success_failer);

        oid=findViewById(R.id.oid);
        proname=findViewById(R.id.proname);
        totalprice=findViewById(R.id.totalprice);
        orderdetailcard=findViewById(R.id.orderdetailcard);
        textbootom=findViewById(R.id.textbootom);
        date_Txt=findViewById(R.id.date_Txt);
        qtyy=findViewById(R.id.qty);
        bookingadd=findViewById(R.id.bookingadd);
        contactnu=findViewById(R.id.contactnu);
        check=findViewById(R.id.check);
        bottomtext=findViewById(R.id.bottomtext);
        transactionStatus=findViewById(R.id.transactionStatus);
        tranactionAmount=findViewById(R.id.tranactionAmount);
        paymenttype=getIntent().getExtras().getString("paymenttype");


        //get Intent
        if (paymenttype.equalsIgnoreCase("order")) {
            parentcat_name = getIntent().getExtras().getString("parentcat_name");
            qty = getIntent().getExtras().getString("qty");
            mobile = getIntent().getExtras().getString("mobile");
            address = getIntent().getExtras().getString("address");
            alternate_mobile = getIntent().getExtras().getString("alternate_mobile");
            description = getIntent().getExtras().getString("description");
            price = getIntent().getExtras().getString("price");
            date = getIntent().getExtras().getString("date");
            category_name = getIntent().getExtras().getString("category_name");
            booingid = getIntent().getExtras().getString("booingid");

        }
        status = getIntent().getExtras().getString("p_status");



       //condition Check

        if (status.equalsIgnoreCase("pass"))
        {
            if (paymenttype.equalsIgnoreCase("order")) {
                tranactionAmount.setText("₹" + price + "-/");
                settext();
            }
            else
            {
                bottomtext.setText("Transaction Suceesfully Done ");

                transactionStatus.setText("Welcome to Service Hat");
                tranactionAmount.setText("₹6000"+"-/");
                orderdetailcard.setVisibility(View.GONE);
                textbootom.setText("You are now our Verified Partner. Login for Orders");
            }

        }
        else
        {

            if (paymenttype.equalsIgnoreCase("order")) {

                bottomtext.setText("Transaction Failed");
                transactionStatus.setText("Your Transaction has been Failed");

                tranactionAmount.setText("₹" + price + "-/");
                tranactionAmount.setTextColor(Color.parseColor("#C12020"));
                check.setImageResource(R.drawable.cross);
                settext();
            }
            else
            {
                bottomtext.setText("Transaction Transaction Failed ");

                transactionStatus.setText("Please Retry!!");
                check.setImageResource(R.drawable.cross);
                tranactionAmount.setText("₹6000"+"-/");
                tranactionAmount.setTextColor(Color.parseColor("#C12020"));
                orderdetailcard.setVisibility(View.GONE);
                textbootom.setVisibility(View.GONE);

            }
        }


    }

    private void settext() {


        oid.setText("OD2020"+booingid);
        proname.setText(category_name);
        totalprice.setText("₹"+price);
        contactnu.setText(mobile+" , "+alternate_mobile);
        date_Txt.setText(date);
        qtyy.setText(qty);
        bookingadd.setText(address);

    }

    @Override
    public void onBackPressed() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());

        final String mobile = sharedPreferences.getString("mobbile", null);
        final String role = sharedPreferences.getString("role", null);

        if (paymenttype.equalsIgnoreCase("order")) {

            Intent in = new Intent(PaymentSuccessFailer.this, Maindrawer.class);
            in.putExtra("mobbile", mobile);
            in.putExtra("role", role);
            startActivity(in);
        }
        else {
            Intent in = new Intent(PaymentSuccessFailer.this, VendorDashBoard.class);
            in.putExtra("mobbile", mobile);
            in.putExtra("role", role);

            startActivity(in);
            finish();
        }

    }

    public void close(View view) {

        onBackPressed();
    }
}
