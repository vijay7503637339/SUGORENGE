package com.afroz.social.ahmad.ui.activities.notification;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.afroz.social.ahmad.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;


public class ImagePreview extends AppCompatActivity {


    String intent_URI,intent_URL;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransitionExit();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransitionEnter();
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/bold.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
        if(getSharedPreferences("theme",MODE_PRIVATE).getBoolean("dark",false)) {
            setContentView(R.layout.activity_image_preview_dark);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.parseColor("#212121"));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    getWindow().getDecorView().setSystemUiVisibility(getWindow().getDecorView().getSystemUiVisibility()&~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

                }
            }
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkk));
            }
            setContentView(R.layout.activity_image_preview);
        }

        intent_URI=getIntent().getStringExtra("uri");
        intent_URL=getIntent().getStringExtra("url");

        PhotoView photoView = findViewById(R.id.photo_view);

        if(!TextUtils.isEmpty(intent_URI)) {
            photoView.setImageURI(Uri.parse(intent_URI));
        }else {
            if(getSharedPreferences("theme",MODE_PRIVATE).getBoolean("dark",false)) {

                Glide.with(this)
                        .setDefaultRequestOptions(new RequestOptions().placeholder(getResources().getDrawable(R.drawable.placeholder)))
                        .load(intent_URL)
                        .into(photoView);

            }else{

                Glide.with(this)
                        .setDefaultRequestOptions(new RequestOptions().placeholder(getResources().getDrawable(R.drawable.placeholder2)))
                        .load(intent_URL)
                        .into(photoView);

            }


        }

    }
}
