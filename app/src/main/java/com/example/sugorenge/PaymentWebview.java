package com.example.sugorenge;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.dynamic.IFragmentWrapper;

import static com.example.sugorenge.MainActivity.BASE_URL;

public class PaymentWebview extends AppCompatActivity {

    WebView web;
    public static CustomProgressBar progressBar = new CustomProgressBar();

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

    String paymenttype;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_webview);
        web=findViewById(R.id.web);

         paymenttype=getIntent().getExtras().getString("paymenttype");


        if (paymenttype.equalsIgnoreCase("order"))

        {
            parentcat_name=getIntent().getExtras().getString("parentcat_name");
            qty=getIntent().getExtras().getString("qty");
            mobile=getIntent().getExtras().getString("mobile");
            address=getIntent().getExtras().getString("address");
            alternate_mobile=getIntent().getExtras().getString("alternate_mobile");
            description=getIntent().getExtras().getString("description");
            price=getIntent().getExtras().getString("price");
            date=getIntent().getExtras().getString("date");
            booingid=getIntent().getExtras().getString("booingid");
            category_name=getIntent().getExtras().getString("category_name");
        }


        progressBar.show(this,"Loading..");
        String url=getIntent().getExtras().getString("url");
        web.getSettings().setLoadsImagesAutomatically(true);
        web.getSettings().setJavaScriptEnabled(true);
        web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        web.loadUrl(url);
        web.setWebViewClient(new MyWebViewClient(this));

    }

    class MyWebViewClient extends WebViewClient {
        private Context context;



        public MyWebViewClient(Context context) {
            this.context = context;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.equalsIgnoreCase(BASE_URL + "passpayment")) {


                     Intent intent = new Intent(PaymentWebview.this, PaymentSuccessFailer.class);
                    intent.putExtra("parentcat_name", parentcat_name);
                    intent.putExtra("qty", qty);
                    intent.putExtra("mobile", mobile);
                    intent.putExtra("address", address);
                    intent.putExtra("alternate_mobile", alternate_mobile);
                    intent.putExtra("description", description);
                    intent.putExtra("price", price);
                    intent.putExtra("date", date);
                    intent.putExtra("category_name", category_name);
                    intent.putExtra("p_status", "pass");
                    intent.putExtra("paymenttype", paymenttype);
                    intent.putExtra("booingid", booingid);
                    startActivity(intent);
                    finish();

            }
            else if (url.equalsIgnoreCase(BASE_URL + "failpayment"))

            {

                Intent intent = new Intent(PaymentWebview.this, PaymentSuccessFailer.class);
                intent.putExtra("parentcat_name",parentcat_name);
                intent.putExtra("qty", qty);
                intent.putExtra("mobile", mobile);
                intent.putExtra("address", address);
                intent.putExtra("alternate_mobile", alternate_mobile);
                intent.putExtra("description", description);
                intent.putExtra("price", price);
                intent.putExtra("date", date);
                intent.putExtra("category_name", category_name);
                intent.putExtra("p_status","fail");

                startActivity(intent);
                finish();


            }             view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.getDialog().dismiss();
        }
    }




    @Override
    public void onBackPressed() {
        if (web.canGoBack()) {
            web.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
