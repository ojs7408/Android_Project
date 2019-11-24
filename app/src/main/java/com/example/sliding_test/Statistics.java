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
    String time,lol;
    String[] result2 = new String[21];

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
                StatisticsDB statisticsDB =new StatisticsDB();
                 lol= ( parent.getItemAtPosition(position).toString()); //lol 에 다롭다운한거(현제 선택한 포지션 값)을 저장.toString 사용
                Toast.makeText(Statistics.this, parent.getItemAtPosition(position)+" 선택", Toast.LENGTH_SHORT).show(); //선택하면 Toast로 알려줌
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

        time=Integer.toString(Today_year)+Integer.toString(Today_month)+Integer.toString(Today_day);
        Dateset.setText(Today_year+"-"+Today_month+"-"+Today_day+"");


        Dateset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //클릭 리스너 (클릭 후 반응)시 날짜 설정

                DatePickerDialog dialog = new DatePickerDialog(Statistics.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month+=1;
                        Dateset.setText(year+"-"+month+"-"+dayOfMonth+"");
                        String years =Integer.toString(year);
                        String months =Integer.toString(month);
                        String dayOfMonths =Integer.toString(dayOfMonth);
                        time=(years+months+dayOfMonths);
                        Toast.makeText(Statistics.this,year+"-"+month+"-"+dayOfMonth+"",Toast.LENGTH_SHORT).show(); //선택 후 토스트로 선택 날짜 보여줌
                    }
                },Today_year,Today_month-1,Today_day);

                dialog.show();


            }
        });

        Searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatisticsDB statisticsDB =new StatisticsDB();

                String str1 = statisticsDB.staristicdb(lol,time); // 차트에 들어갈 통계 값 요청
                String str2, str3;
                String[] result1;
                String target = "\"h_05\":";  //php문에서 끌어올때 h_05
                int target_num1 = str1.indexOf(target);

                try{
                    str2 = str1.substring(target_num1,(str1.substring(target_num1).indexOf("            }")+target_num1));
                    result1 = str2.split(",                "); // 1차 통계값 자르기

                    for(int i = 0; i < 21; i++)
                    {
                        int target_num2 = result1[i].indexOf("\": \"")+4;
                        result2[i] = result1[i].substring(target_num2,(result1[i].substring(target_num2).indexOf("\"")+target_num2));
                    } // 최종 통계값 자르기

                    ArrayList<Entry> entries = new ArrayList<>();
                    for(int i = 0 ; i < 21; i++)
                    {
                        entries.add(new Entry(Float.parseFloat(result2[i]), i));
                    } // 차트에 통계값 넣기

                    LineChart lineChart = (LineChart) findViewById(R.id.chart);

                    LineDataSet dataset = new LineDataSet(entries, "# of Calls");

                    ArrayList<String> labels = new ArrayList<>();  //레이블에 시간 보여주기위함 ArrayList<스트링형>으로 구현
                    int i=5;
                    while (i<24) {
                        labels.add(i + "시");
                        i++;
                    }
                    labels.add("00시");
                    labels.add("01시");



                    LineData data = new LineData(labels, dataset); //차트 만들기 위해서 new LineData안에 레이블이랑 데이터셋 값 집어넣어줌
                    dataset.setColors(ColorTemplate.COLORFUL_COLORS); //여기는 COLORFUL_COLORS말고 다른 색상 사용가능.

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

                } catch(StringIndexOutOfBoundsException e) {
                    Toast.makeText(getApplicationContext(),"정보가 없습니다.", Toast.LENGTH_SHORT).show();
                }


            }
        });






        Menubtn = (ImageButton) findViewById(R.id.Menubtn); //메뉴 버튼
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

