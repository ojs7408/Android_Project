package com.example.sliding_test;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class Londs extends AppCompatActivity {
    private Location lastKnownLocation = null;
    LocationManager locationManager;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.londs);


        //network 주소 받기
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //GPS 설정화면으로 이동
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            startActivity(intent);
        }

        while (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                break;
            }
        }

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        lm.removeUpdates(locationListener);    // Stop the update if it is in progress.
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        lm.requestLocationUpdates("network", 0, 0, locationListener);

    }
    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            // Get the last location, and update UI.
            lastKnownLocation = location;
            double longitude=lastKnownLocation.getLongitude();
            double latitude=lastKnownLocation.getLatitude();
            String  tmp = Double.toString(longitude);
            String tmp2 = Double.toString(latitude);
            Api_adrss api_adrss=new Api_adrss();                        //API 클래스 생성
            String btrainNo = api_adrss.adrss(tmp, tmp2);

            Intent intent = new Intent (Londs.this,MainActivity.class);
            intent.putExtra("train",btrainNo);
            startActivity(intent);
            finish();
            lm.removeUpdates(this);
        }
        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}

    };


}

