package com.example.sliding_test;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


public class Loading extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST = 1000;
    ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_activity);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST);
        Toast.makeText(this, "화면을 클릭해주세요", Toast.LENGTH_LONG).show();
        imageView=(ImageView)findViewById(R.id.loading);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Loading.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }
            });
    }

}
