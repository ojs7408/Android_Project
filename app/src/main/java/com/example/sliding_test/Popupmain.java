package com.example.sliding_test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class Popupmain extends Activity {
    ImageButton button1;
    public String Selects="1호선";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_activity);
        button1=(ImageButton)findViewById(R.id.stayline1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Selects="2호선";
            }
        });

        Api_adrss api_adrss = new Api_adrss();
        String Search =api_adrss.trains(Selects);
        String[] Searchs =Search.split("/");
        String Current =Searchs[0];
        String Trains =Searchs[1];
        String Arrival =Searchs[2];
        String[] list1 =Current.split(",");
        String[] list2 =Arrival.split(",");
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.popup_items,R.id.itemss1,list1) ;
        ArrayAdapter adapter2 = new ArrayAdapter(this, R.layout.popup_items,R.id.itemss2,list2) ;
        ListView listview = (ListView) findViewById(R.id.popuplist) ;
        listview.setAdapter(adapter) ;
    }
}
