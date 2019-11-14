package com.example.sliding_test;

import android.Manifest;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static androidx.core.content.PermissionChecker.PERMISSION_DENIED;


public class Loading extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST = 1000;
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_activity);

        ActivityCompat.requestPermissions(Loading.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST);
        ExampleThread thread = new ExampleThread();
        thread.start();
    }
    private class ExampleThread extends Thread {
        private static final String TAG = "ExampleThread";
        public ExampleThread() {

        }
        public void run() {

            int check =ContextCompat.checkSelfPermission(Loading.this, Manifest.permission.ACCESS_FINE_LOCATION);
            while (check ==PERMISSION_DENIED)
            {
                 check =ContextCompat.checkSelfPermission(Loading.this, Manifest.permission.ACCESS_FINE_LOCATION);
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(Loading.this, MainActivity.class);
            intent.putExtra("train","0000");
            startActivity(intent);
            finish();


        }
    }
}
