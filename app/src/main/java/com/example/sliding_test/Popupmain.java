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

    String[] list3;
    String tmp ="1";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_activity);
        Button button1= findViewById(R.id.stayline1);
        Button button2=findViewById(R.id.stayline2);
        Button button3=findViewById(R.id.stayline3);
        Button button4=findViewById(R.id.stayline4);
        Button button5=findViewById(R.id.stayline5);
        Button button6=findViewById(R.id.stayline6);
        Button button7=findViewById(R.id.stayline7);
        Button button8=findViewById(R.id.stayline8);
        Button button9=findViewById(R.id.stayline9);
        Button buttontest=findViewById(R.id.stayline10);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Popupmain.this,Popupmain.class);
                intent.putExtra("line","1호선");
                startActivityForResult(intent, 1);
                finish();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Popupmain.this,Popupmain.class);
                intent.putExtra("line","2호선");
                startActivityForResult(intent, 1);
                finish();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Popupmain.this,Popupmain.class);
                intent.putExtra("line","3호선");
                startActivityForResult(intent, 1);
                finish();
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Popupmain.this,Popupmain.class);
                intent.putExtra("line","4호선");
                startActivityForResult(intent, 1);
                finish();
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Popupmain.this,Popupmain.class);
                intent.putExtra("line","5호선");
                startActivityForResult(intent, 1);
                finish();
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Popupmain.this,Popupmain.class);
                intent.putExtra("line","6호선");
                startActivityForResult(intent, 1);
                finish();
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Popupmain.this,Popupmain.class);
                intent.putExtra("line","7호선");
                startActivityForResult(intent, 1);
                finish();
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Popupmain.this,Popupmain.class);
                intent.putExtra("line","8호선");
                startActivityForResult(intent, 1);
                finish();
            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Popupmain.this,Popupmain.class);
                intent.putExtra("line","9호선");
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