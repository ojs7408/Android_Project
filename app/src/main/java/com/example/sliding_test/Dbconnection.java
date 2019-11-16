package com.example.sliding_test;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Dbconnection extends AppCompatActivity {


    TextView sainviewtext = (TextView) findViewById(R.id.mainviewtext);

    public static String[] Subway(String sub){

        String test = "https://seulgi.me/average.php"; // php 선택
        URLConnector task = new URLConnector(test); // DB 연결

        task.start(); // php running

        try{
            task.join(); // 종료시까지 대기
            System.out.println("waiting... for result");
        }
        catch(InterruptedException e){

        }

        String str1 = task.getResult(); // SELECT된 DB값
        String str2, str3;
        String target = sub;
        int target_num1 = str1.indexOf(target);
        System.out.println(str1);

        try{
            str2 = str1.substring(target_num1,(str1.substring(target_num1).indexOf("/")+target_num1));
            int target_num2 = str1.indexOf(", ");
            str3 = str2.substring(target_num2+2,(str2.substring(target_num2).indexOf("*")+target_num2));
            String[] result = str3.split(", ");

            for(int i=0; i < result.length; i++)
            {
                System.out.println(result[i]);
            }
            return result;
        } catch (java.lang.StringIndexOutOfBoundsException e) {
            String[] error = {"error"};
            return error;
        } // 반환해주기 위한 문자열 자르기

    } //쾌적도 값 10개 가져오기

    public static String UseTable(String sub){
        String test = "https://seulgi.me/chair.php"; // php 선택
        URLConnector task = new URLConnector(test); // DB 연결

        task.start(); // php running

        try{
            task.join(); // 종료시까지 대기
            System.out.println("waiting... for result");
        }
        catch(InterruptedException e){

        }

        String result = task.getResult(); // SELECT된 DB값

        return result;

    } //좌석 사용 유무 값 3개 가져오기

} // 받아오는 데이터가 적은 기능에 대한 DB처리
