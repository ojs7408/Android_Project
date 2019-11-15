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
import android.provider.Settings;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity { //현재 지하철 정보 화면 클래스

    private final long FINISH_INTERVAL_TIME = 20000; // 뒤로가기 버튼 인식 시간 2초
    private long backPressedTime = 0; // 2초를 측정하기 위해 사용하는 변수
    private RecyclerView mVerticalView, mVerticalView2;
    private HorizontalAdapter mAdapter, mAdapter2;
    private LinearLayoutManager mLayoutManager, mLayoutManager2;
    private Location lastKnownLocation = null;
    ImageButton Menubtn;    // 왼쪽 상단 팝업 버튼
    public int MAX_ITEM_COUNT = 10;               //  4량 2호선 6량 8호선이나 분당선, 8량 5, 6, 7호선, 10량 1, 2, 3, 4호선
    LocationManager locationManager;
    TextView Mainviewtext;
    ImageButton btn;
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
        btn = (ImageButton)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    //GPS 설정화면으로 이동
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    startActivity(intent);
                }
                while (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                        break;
                }
                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                lm.removeUpdates(locationListener);    // Stop the update if it is in progress.
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                lm.requestLocationUpdates("network", 0, 0, locationListener);
                Toast.makeText(MainActivity.this, "새로고침 중입니다...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent (MainActivity.this,SvsmainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Mainviewtext = (TextView) findViewById(R.id.mainviewtext);
        mVerticalView = (RecyclerView) findViewById(R.id.Horizontal_Top_List);
        mVerticalView2=(RecyclerView) findViewById(R.id.Horizontal_Bottom_List);
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
                intent.putExtra("line","1호선");
                startActivityForResult(intent, 1);
            }
        });

        ArrayList<HorizontalData> data1 = new ArrayList<>(); //포화도 View를 위한 자료구조 변수 선언
        ArrayList<HorizontalData> data2 = new ArrayList<>(); //노약자석 View를 위한 자료구조 변수 선언

        int i = 0; // while문을 위한 index

        //해당 지하철 칸 수 만큼 지하철 이미지를 자료구조 변수에 넣어줌
        while (i < MAX_ITEM_COUNT) {
            data1.add(new HorizontalData(R.drawable.side_traindefault, i+1 +"")); //기본 이미지, 기본 수치
            data2.add(new HorizontalData(R.drawable.top_train0, "")); //기본 이미지는 넣어주나 수치 x
            i++;
            //
        }


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // 지하철 포화도 이미지를 가로로 배열시키기 위해 LinearLayout Horizontal로 새로 생성
        mLayoutManager2 = new LinearLayoutManager(this);
        mLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL); // 지하철 노약자석 이미지를 가로로 배열시키기 위해 LinearLayout Horizontal로 새로 생성
        mVerticalView.setLayoutManager(mLayoutManager); //새로 생성한 LinearLayout을 커스텀한 RecycleView에 넣어줌
        mVerticalView2.setLayoutManager(mLayoutManager2); //새로 생성한 LinearLayout을 커스텀한 RecycleView에 넣어줌
        Intent intent = getIntent(); /*데이터 수신*/
        String trainNo = intent.getExtras().getString("train");
        data1.clear(); //자료구조 변수 초기화
        data1=  Figure.Figure_set(trainNo); //지하철 번호를 Figure로 보내 해당 지하철의 포화도 수치를 받아옴, 받아오는 형식은 Arraylist<HorizontalData>
        mAdapter = new HorizontalAdapter();
        mAdapter.setData(data1); //포화도 이미지, 수치 Reset
        mAdapter2 = new HorizontalAdapter();
        mAdapter2.setData(data2); //노약자석 사용유무 이미지 Reset

        mVerticalView.setAdapter(mAdapter); //포화도 이미지,수치를 적용한 View를 화면에 띄움
        mVerticalView2.setAdapter(mAdapter2); //노약자석 사용유무 이미지를 적용한 View를 화면에 띄움




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
            String tmp = Double.toString(longitude);
            String tmp2 = Double.toString(latitude);
            Api_adrss api_adrss=new Api_adrss();                        //API 클래스 생성
            String btrainNo = api_adrss.adrss(tmp, tmp2);
            Mainviewtext.setText(btrainNo);           //API 받아온 부분
            // Stop the update to prevent changing the location.
            lm.removeUpdates(this);
            Intent intent = new Intent (MainActivity.this,SvsmainActivity.class);
            intent.putExtra("train",btrainNo);
            finish();
            startActivity(intent);

        }
        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}

    };
}

