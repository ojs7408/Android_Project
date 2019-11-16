package com.example.sliding_test;

import android.os.AsyncTask;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Dbconnection {

    public String value ;
    String[] result2 = new String[40];

     String[] Sensor(String sub) {
        GetData task = new GetData();
        value = task.doInBackground(sub);// SELECT된 DB값

         String value2, value3;
         String[] result1;
         String target = "\"space1\":";
         int target_num1 = value.indexOf(target);

         value2 = value.substring(target_num1,(value.substring(target_num1).indexOf("            }")+target_num1));
         result1 = value2.split(",                "); // 1차 통계값 자르기

         for(int i = 0; i < 40; i++)
         {
             int target_num2 = result1[i].indexOf("\": \"")+4;
             result2[i] = result1[i].substring(target_num2,(result1[i].substring(target_num2).indexOf("\"")+target_num2));
         } // 최종 통계값 자르기

        return result2;
    }

    public class GetData extends AsyncTask<String, Void, String> {
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        public void onPostExecute(String result) {
            super.onPostExecute(result);
            value = result;
        }

        @Override
        protected String doInBackground(String... params) {
            String searchKeyword1 = params[0];
            String serverURL = "https://seulgi.me/sensor.php";
            String postParameters = "subway_name=" + searchKeyword1;
            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(5000); // 읽는 타임아웃 5초
                httpURLConnection.setConnectTimeout(5000); // 연결 타임아웃 5초
                httpURLConnection.setRequestMethod("POST"); // POST 방식
                httpURLConnection.setDoInput(true); // 받는 기능 on
                httpURLConnection.connect(); // 연결

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8")); // 보낸다.
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                } // 서버 통신 상태 확인

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader); // 받아온다.
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                } // 뒤에 붙이기

                bufferedReader.close();

                return sb.toString().trim();

            } catch (Exception e) {
                errorString = e.toString();
                return null;
            }
        }
    }
}
