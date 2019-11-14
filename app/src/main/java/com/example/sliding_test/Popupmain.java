package com.example.sliding_test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Popupmain extends Activity  {
    ImageButton button1 ,buttontest;
    String[] list3;
    String tmp ="1";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_activity);
        button1=(ImageButton)findViewById(R.id.stayline1);
        buttontest=(ImageButton)findViewById(R.id.stayline10);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Popupmain.this,Popupmain.class);
                intent.putExtra("line","2호선");
                startActivityForResult(intent, 1);
                finish();
            }
        });
        buttontest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Popupmain.this,Popupmain.class);
                intent.putExtra("line","test");
                startActivityForResult(intent, 1);
                finish();

            }
        });
        Intent intent = getIntent(); /*데이터 수신*/
        String list = intent.getExtras().getString("line");
            Api_adrss api_adrss = new Api_adrss();
            String Search = api_adrss.trains(list);
            System.out.println(Search);
            String[] Searchs = Search.split("/");
            String Current = Searchs[0];
            String Trains = Searchs[1];
            String Arrival = Searchs[2];
            String Upline = Searchs[3];
            String[] list1 = Current.split(",");            //현재역
            String[] list2 = Arrival.split(",");            //도착역
            list3 = Trains.split(",");             //열차번호
            String[] list4 = Upline.split(",");             //상하행선
        // api 검색끝
        ListView listview ;
        ListViewAdapter adapter;
        adapter = new ListViewAdapter() ;
        listview = (ListView) findViewById(R.id.popuplist);
        listview.setAdapter(adapter);
        for(int i=0;i<list1.length;i++) {
            if( list4[i].equals(tmp)) {
                adapter.addItem(list1[i], list2[i],"상행",list3[i]);
            }
            else {
                System.out.println(list4[i]);
                adapter.addItem(list1[i], list2[i],"하행",list3[i]);
            }
        }
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(list3[position]);
            }
        });

    }
}