package com.example.sugorenge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toolbar;

public class contentprofile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profiledemoui);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }


}
