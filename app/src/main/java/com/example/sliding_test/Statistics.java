package com.example.sliding_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mikhaellopez.lazydatepicker.LazyDatePicker;


import java.util.ArrayList;
import java.util.Date;

//통계정보에 대한 Activity
public class Statistics  extends AppCompatActivity {
    private static final String DATE_FORMAT = "MM-dd-yyyy";

    ImageButton Menubtn;
    private final long FINISH_INTERVAL_TIME = 20000; // 뒤로가기 버튼 인식 시간 2초
    private long backPressedTime = 0; // 2초를 측정하기 위해 사용하는 변수
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_activity);
        //test

        //날짜 Define함 min max Date
        Date minDate = LazyDatePicker.stringToDate("01-01-2016", DATE_FORMAT);
        Date maxDate = LazyDatePicker.stringToDate("12-31-2018", DATE_FORMAT);

        LazyDatePicker lazyDatePicker = findViewById(R.id.lazyDatePicker);
        lazyDatePicker.setDateFormat(LazyDatePicker.DateFormat.MM_DD_YYYY);
        lazyDatePicker.setMinDate(minDate);
        lazyDatePicker.setMaxDate(maxDate);

        lazyDatePicker.setOnDatePickListener(new LazyDatePicker.OnDatePickListener() {
            @Override
            public void onDatePick(Date dateSelected) {
                //...
            }
        });

        lazyDatePicker.setOnDateSelectedListener(new LazyDatePicker.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Boolean dateSelected) {
                //...
            }
        });




        LineChart lineChart = (LineChart) findViewById(R.id.chart);

        Menubtn = (ImageButton) findViewById(R.id.Menubtn);
        Menubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(Statistics.this,Menubtn);
                MenuInflater inf = popup.getMenuInflater();
                inf.inflate(R.menu.mymenu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.Statistics_btn){
                            Intent intent = new Intent(Statistics.this,MainActivity.class);
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

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(4f, 0));
        entries.add(new Entry(8f, 1));
        entries.add(new Entry(6f, 2));
        entries.add(new Entry(2f, 3));
        entries.add(new Entry(18f, 4));
        entries.add(new Entry(9f, 5));
        entries.add(new Entry(16f, 6));
        entries.add(new Entry(5f, 7));
        entries.add(new Entry(3f, 8));
        entries.add(new Entry(7f, 10));
        entries.add(new Entry(9f, 11));

        LineDataSet dataset = new LineDataSet(entries, "# of Calls");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        labels.add("July");
        labels.add("August");
        labels.add("September");
        labels.add("October");
        labels.add("November");
        labels.add("December");

        LineData data = new LineData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        /*dataset.setDrawCubic(true); //선 둥글게 만들기
        dataset.setDrawFilled(true); //그래프 밑부분 색칠*/

        lineChart.setData(data);
        lineChart.animateY(5000);

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
