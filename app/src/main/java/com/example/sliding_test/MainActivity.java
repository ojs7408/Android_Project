package com.example.sliding_test;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    private final long FINISH_INTERVAL_TIME = 20000; // 뒤로가기 버튼 인식 시간 2초
    private long backPressedTime = 0; // 2초를 측정하기 위해 사용하는 변수
    private RecyclerView mVerticalView, mVerticalView2;
    private VerticalAdapter mAdapter, mAdapter2;
    private LinearLayoutManager mLayoutManager, mLayoutManager2;
    private Location lastKnownLocation = null;
    ImageButton Menubtn;    // 왼쪽 상단 팝업 버튼
    private int MAX_ITEM_COUNT = 10;               //  4량 2호선 6량 8호선이나 분당선, 8량 5, 6, 7호선, 10량 1, 2, 3, 4호선
    TextView Mainviewtext;
    ImageButton btn;
    String tmp = null, tmp2 = null;
    String stayion;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //network 주소 받기
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        lm.removeUpdates(locationListener);    // Stop the update if it is in progress.
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        lm.requestLocationUpdates("network", 0, 0, locationListener);
        //network 끝
        //DB 시작//
        String test = "https://seulgi.me/junseok.php";
        Dbconnection task = new Dbconnection(test);
        task.start();
        try {
            task.join();
            System.out.println("waiting.... for result");
        } catch (InterruptedException e) {

        }
        String result = task.getResult();
        System.out.println(result);
        //DB 끝//
        btn = (ImageButton)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                lm.removeUpdates(locationListener);    // Stop the update if it is in progress.
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                lm.requestLocationUpdates("network", 0, 0, locationListener);
                Toast.makeText(MainActivity.this, "새로고침 완료.", Toast.LENGTH_SHORT).show();
            }
        });
        Mainviewtext = (TextView) findViewById(R.id.mainviewtext);
        mVerticalView = (RecyclerView) findViewById(R.id.vertical_list);
        mVerticalView2=(RecyclerView) findViewById(R.id.vertical_list2);
        Menubtn = (ImageButton) findViewById(R.id.Menubtn);
        Menubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(MainActivity.this,Menubtn);
                MenuInflater inf = popup.getMenuInflater();
                inf.inflate(R.menu.mymenu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.Statistics_btn){
                            Intent intent = new Intent(MainActivity.this,Statistics.class);
                            startActivity(intent);
                            finish();
                            return true;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });

        Mainviewtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainActivity.this,Popupmain.class);
                startActivityForResult(intent, 1);
            }
        });

        ArrayList<VerticalData> data = new ArrayList<>();

        int i = 0;
        while (i < MAX_ITEM_COUNT) {
            data.add(new VerticalData(R.drawable.side_traindefault, i+1 +""));
            i++;
        }

       /* // GPS 권한 받기
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionCheck == PackageManager.PERMISSION_DENIED) {

                    // 권한 없음
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_ACCESS_FINE_LOCATION);
                } else {
                }
                if (permissionCheck != PackageManager.PERMISSION_DENIED){
                    String locationProvider = LocationManager.NETWORK_PROVIDER;
                Location location = lm.getLastKnownLocation(locationProvider);
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                System.out.println(location);
                tmp = Double.toString(longitude);
                tmp2 = Double.toString(latitude);
            }
        }
        else{
        }
*/
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mLayoutManager2 = new LinearLayoutManager(this);
        mLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        mVerticalView.setLayoutManager(mLayoutManager);
        mVerticalView2.setLayoutManager(mLayoutManager2);
        mAdapter = new VerticalAdapter();
        mAdapter.setData(data);
        mAdapter2 = new VerticalAdapter();
        mAdapter2.setData(data);
        mVerticalView.setAdapter(mAdapter);
        mVerticalView2.setAdapter(mAdapter2);
    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis(); // 현재 시각과 날짜와의 차이를 long으로 표현
        long intervalTime = tempTime - backPressedTime; // 위에 선언된 tempTime - Main문 처음에 있던 backPressedTime과의 차이

        if(0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
        {
            super.onBackPressed();
        }
        else
        {
            backPressedTime = tempTime;
            Toast.makeText(this, "이전버튼을 한번 더 누르시면 하철2가 종료됩니다.", Toast.LENGTH_SHORT).show();
        }

    }
    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            // Get the last location, and update UI.
            lastKnownLocation = location;
            double longitude=lastKnownLocation.getLongitude();
            double latitude=lastKnownLocation.getLatitude();
            tmp = Double.toString(longitude);
            tmp2 = Double.toString(latitude);
            Api_adrss api_adrss=new Api_adrss();                        //API 클래스 생성
            stayion = api_adrss.adrss(tmp, tmp2);
            Mainviewtext.setText(stayion);           //API 받아온 부분
            // Stop the update to prevent changing the location.
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
