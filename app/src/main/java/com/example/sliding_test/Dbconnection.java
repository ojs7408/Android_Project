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

    public static String[] Subway(String sub){


        String test = "https://seulgi.me/average.php";
        URLConnector task = new URLConnector(test);

        task.start();

        try{
            task.join();
            System.out.println("waiting... for result");
        }
        catch(InterruptedException e){

        }

        String str1 = task.getResult();
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
            //미처리 데이터
        }

        return null;
    }

    public static String UseTable(String sub){
        String test = "https://seulgi.me/chair.php";
        URLConnector task = new URLConnector(test);

        task.start();

        try{
            task.join();
            System.out.println("waiting... for result");
        }
        catch(InterruptedException e){

        }

        String result = task.getResult();

        return result;
    }

}
