package com.example.sliding_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final long FINISH_INTERVAL_TIME = 20000; // 뒤로가기 버튼 인식 시간 2초
    private long backPressedTime = 0; // 2초를 측정하기 위해 사용하는 변수


    private RecyclerView mVerticalView;
    private RecyclerView mVerticalView2;
    private VerticalAdapter mAdapter;
    private VerticalAdapter mAdapter2;
    private LinearLayoutManager mLayoutManager;
    private LinearLayoutManager mLayoutManager2;
    ImageButton Menubtn;    // 왼쪽 상단 팝업 버튼
    private int MAX_ITEM_COUNT = 10;               //  4량 2호선 6량 8호선이나 분당선, 8량 5, 6, 7호선, 10량 1, 2, 3, 4호선
    TextView Mainviewtext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            data.add(new VerticalData(R.drawable.traindefault, i+1 +""));
            i++;
        }

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
}
