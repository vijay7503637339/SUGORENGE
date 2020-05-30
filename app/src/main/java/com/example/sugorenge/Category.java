package com.example.sugorenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Category extends AppCompatActivity {
    LinearLayout hsmgt, ptcare, acservice, realesate, gardningm, showrooms, facility, ac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        hsmgt =  findViewById(R.id.hsmgt);
        facility =  findViewById(R.id.facility);
        ptcare = findViewById(R.id.ptcare);
        ac =  findViewById(R.id.ac);


        hsmgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Category.this, Staff.class);
                startActivity(in);
            }
        });
        ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Category.this, Sub_Staff_Category.class);
                intent.putExtra("id","31");
                intent.putExtra("name","Air Conditioner");
               startActivity(intent);
            }
        });
        facility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Category.this, Sub_Staff_Category.class);
                intent.putExtra("id","55");
                intent.putExtra("name","Facility Management");
                 startActivity(intent);
            }
        });

        ptcare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Category.this, Sub_Staff_Category.class);
                intent.putExtra("id","63");
                intent.putExtra("name","Medical Care");
                startActivity(intent);
            }
        });
    }

    public void back(View view) {

        onBackPressed();
    }
}
