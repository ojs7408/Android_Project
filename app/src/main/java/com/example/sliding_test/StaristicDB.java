package com.example.sliding_test;

import android.os.AsyncTask;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class StaristicDB {

    public String value ;

    String staristicdb(String sub, String sub1) {
        GetData task = new GetData();
        value = task.doInBackground(sub, sub1);
        return value;
    } // 통계 값 반환 시켜주기

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
            String searchKeyword2 = params[1];
            String serverURL = "https://seulgi.me/testfile.php";
            String postParameters = "subway_name=" + searchKeyword1 +"&time=" + searchKeyword2;
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
