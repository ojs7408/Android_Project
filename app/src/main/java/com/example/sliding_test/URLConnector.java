package com.example.sliding_test;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class URLConnector extends Thread {

    private String result;
    private String URL;

    public URLConnector(String url){
        URL = url;
    } // Dbconnection.java 로 보냄

    @Override
    public void run() {
        final String output = request(URL);
        result = output;
    }

    public String getResult(){
        return result;
    }

    private String request(String urlStr) {
        StringBuilder output = new StringBuilder();
        try {
            java.net.URL url = new URL(urlStr); // 접근 가능한 자원의 주소
            HttpURLConnection conn = (HttpURLConnection)url.openConnection(); // HttpURLConnection 객체를 가져온다.
            if (conn != null) {
                conn.setConnectTimeout(10000); // 10초의 타임아웃
                conn.setRequestMethod("GET"); // GET 방식
                conn.setDoInput(true); // 받는 기능 on
                conn.setDoOutput(true); // 쓰는 기능 on

                int resCode = conn.getResponseCode(); // 서버 통신의 결과값 가져오기
                if (resCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream())) ;
                    String line = null;
                    while(true) {
                        line = reader.readLine(); // 받아온 데이터를 읽는다.
                        if (line == null) {
                            break;
                        }
                        output.append(line + "\n");
                    } // 서버 통신에 대한 준비가 되는경우

                    reader.close();
                    conn.disconnect();
                }
            }
        } catch(Exception ex) {
            Log.e("SampleHTTP", "Exception in processing response.", ex);
            ex.printStackTrace();
        } // 반응이 없다.

        return output.toString();
    }
}