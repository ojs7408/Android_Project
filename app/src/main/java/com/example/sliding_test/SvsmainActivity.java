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
import android.os.StrictMode;
import android.provider.Settings;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SvsmainActivity extends AppCompatActivity {

    private final long FINISH_INTERVAL_TIME = 20000; // 뒤로가기 버튼 인식 시간 2초
    private long backPressedTime = 0; // 2초를 측정하기 위해 사용하는 변수
    private RecyclerView mVerticalView, mVerticalView2;
    private VerticalAdapter mAdapter, mAdapter2 ;
    private LinearLayoutManager mLayoutManager, mLayoutManager2;
    private Location lastKnownLocation = null;
    ImageButton Menubtn;    // 왼쪽 상단 팝업 버튼
    public int MAX_ITEM_COUNT = 10;               //  4량 2호선 6량 8호선이나 분당선, 8량 5, 6, 7호선, 10량 1, 2, 3, 4호선
    LocationManager locationManager;
    TextView Mainviewtext;
    ImageButton btn;
    String tmp = null, tmp2 = null;
    String btrainNo;   // 기차 번호
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sractivity_main);

        btn = (ImageButton)findViewById(R.id.svsbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SvsmainActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Mainviewtext = (TextView) findViewById(R.id.svsmainviewtext);
        mVerticalView = (RecyclerView) findViewById(R.id.svsvertical_list);
        mVerticalView2=(RecyclerView) findViewById(R.id.svsvertical_list2);
        Menubtn = (ImageButton) findViewById(R.id.svsMenubtn);
        Menubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(SvsmainActivity.this,Menubtn);
                MenuInflater inf = popup.getMenuInflater();
                inf.inflate(R.menu.mymenu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.Statistics_btn){
                            Intent intent = new Intent(SvsmainActivity.this,Statistics.class);
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
                Intent intent = new Intent (SvsmainActivity.this,Popupmain.class);
                intent.putExtra("name","1호선");
                startActivityForResult(intent, 1);
            }
        });

        ArrayList<VerticalData> data1 = new ArrayList<>();
        ArrayList<VerticalData> data2 = new ArrayList<>();

        int i = 0;
        while (i < MAX_ITEM_COUNT) {
            data1.add(new VerticalData(R.drawable.side_traindefault, i+1 +""));
            data2.add(new VerticalData(R.drawable.top_train0, ""));
            i++;
            //
        }


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mLayoutManager2 = new LinearLayoutManager(this);
        mLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        mVerticalView.setLayoutManager(mLayoutManager);
        mVerticalView2.setLayoutManager(mLayoutManager2);

        mAdapter = new VerticalAdapter();
        mAdapter.setData(data1);
        mAdapter2 = new VerticalAdapter();
        mAdapter2.setData(data2);

        mVerticalView.setAdapter(mAdapter);
        mVerticalView2.setAdapter(mAdapter2);

        Intent intent = getIntent(); /*데이터 수신*/
        String trainNo = intent.getExtras().getString("train");
        Mainviewtext.setText(trainNo);
        data1.clear();
        data1=  Figure.Figure_set(trainNo);
        mAdapter.setData(data1);
        mVerticalView.setAdapter(mAdapter);



    }

}


