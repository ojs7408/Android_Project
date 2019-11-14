package com.example.sliding_test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.util.ArrayList;
import java.util.Calendar;

//통계정보에 대한 Activity
public class Statistics  extends AppCompatActivity {
    private static final String DATE_FORMAT = "MM-dd-yyyy";

    ImageButton Menubtn;
    TextView Dateset;
    Button Searchbtn;
    String time;

    private final long FINISH_INTERVAL_TIME = 20000; // 뒤로가기 버튼 인식 시간 2초
    private long backPressedTime = 0; // 2초를 측정하기 위해 사용하는 변수
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_activity);

        //온클릭시 테스트하려고 만든 텍스트입니다.
        final TextView textView = (TextView)findViewById(R.id.textView);

        Spinner spinner = (Spinner) findViewById(R.id.spinnerDrop);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sub_stations, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                textView.setText("선택한 역은 = > " + parent.getItemAtPosition(position));  //선택한거 넘길수있음
                StaristicDB staristicDB =new StaristicDB();
                String lol= ( parent.getItemAtPosition(position).toString());
                System.out.println(time);
                Toast.makeText(Statistics.this, "아이템 선택시.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Searchbtn = (Button)findViewById(R.id.Searchbtn);
        Dateset = (TextView)findViewById(R.id.Dateset);
        final Calendar cal = Calendar.getInstance();
        final int Today_year = cal.get(Calendar.YEAR);
        final int Today_month = cal.get(Calendar.MONTH)+1;
        final int Today_day = cal.get(Calendar.DAY_OF_MONTH);

        Dateset.setText(Today_year+"-"+Today_month+"-"+Today_day+"");


        Dateset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dialog = new DatePickerDialog(Statistics.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month+=1;
                        Dateset.setText(year+"-"+month+"-"+dayOfMonth+"");
                        String years =Integer.toString(year);
                        String months =Integer.toString(month);
                        String dayOfMonths =Integer.toString(dayOfMonth);
                        time=(years+months+dayOfMonths);
                        Toast.makeText(Statistics.this,year+"-"+month+"-"+dayOfMonth+"",Toast.LENGTH_SHORT).show();
                    }
                },Today_year,Today_month-1,Today_day);

                dialog.show();


            }
        });

        Searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });






        LineChart lineChart = (LineChart) findViewById(R.id.chart);

        Menubtn = (ImageButton) findViewById(R.id.Menubtn);
        Menubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(Statistics.this,Menubtn);
                MenuInflater inf = popup.getMenuInflater();
                inf.inflate(R.menu.statistics_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.Statistics_btn){
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
        entries.add(new Entry(4f, 0)); // 5시 포화도
        entries.add(new Entry(8f, 1)); // 6시 포화도
        entries.add(new Entry(6f, 2));
        entries.add(new Entry(2f, 3));
        entries.add(new Entry(18f, 4));
        entries.add(new Entry(9f, 5));
        entries.add(new Entry(16f, 6));
        entries.add(new Entry(5f, 7));
        entries.add(new Entry(3f, 8));
        entries.add(new Entry(7f, 10));
        entries.add(new Entry(30f, 11));
        entries.add(new Entry(62f, 12));
        entries.add(new Entry(77f, 13));



        LineDataSet dataset = new LineDataSet(entries, "# of Calls");

        ArrayList<String> labels = new ArrayList<>();
        int i=5;
        while (i<24) {
            labels.add(i + "시");
            i++;
        }
        labels.add("00시");
        labels.add("01시");



        LineData data = new LineData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelsToSkip(3);

        YAxis yAxisRight = lineChart.getAxisRight(); //그래프 오른쪽 설정
        yAxisRight.setDrawLabels(false);
        yAxisRight.setDrawAxisLine(false);
        yAxisRight.setDrawGridLines(false);

        YAxis yAxisLeft = lineChart.getAxisLeft();
        yAxisLeft.setAxisMaxValue(100);
        yAxisLeft.setAxisMinValue(0);
        yAxisLeft.setLabelCount(11,true);
        yAxisLeft.setStartAtZero(true);



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

