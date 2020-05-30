package com.example.sugorenge;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        //animation

        final View myView=findViewById(R.id.head);

        myView.post(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {

                int cx = (myView.getLeft() + myView.getRight()) / 2;
                int cy = (myView.getTop() + myView.getBottom()) / 2;

                // get the final radius for the clipping circle
                int dx = Math.max(cx, myView.getWidth() - cx);
                int dy = Math.max(cy, myView.getHeight() - cy);
                float finalRadius = (float) Math.hypot(dx, dy);



                // Android native animator
                Animator animator =
                        ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.setDuration(1500);
                animator.start();
                //create your anim here
            }
        });



        //thread


        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);

                    SharedPreferences sharedPreferences = PreferenceManager
                            .getDefaultSharedPreferences(SplashScreen.this);
                    String mobbile = sharedPreferences.getString("mobbile", "");
                    String role = sharedPreferences.getString("role", "");
                    if (!mobbile.equals("") && !role.equals("")) {

                        if (role.equalsIgnoreCase("1")) {
                            Intent intent = new Intent(SplashScreen.this, Maindrawer.class);
                            intent.putExtra("mobbile", mobbile);
                            intent.putExtra("role", role);
                            finish();
                            startActivity(intent);
                        } else if (role.equalsIgnoreCase("2")) {
                            Intent intent = new Intent(SplashScreen.this, ThanksVendor.class);
                            intent.putExtra("mobbile", mobbile);
                            intent.putExtra("role", role);
                            finish();
                            startActivity(intent);
                        } else {

                            Toast.makeText(SplashScreen.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        finish();
                        startActivity(intent);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        myThread.start();

    }



}
