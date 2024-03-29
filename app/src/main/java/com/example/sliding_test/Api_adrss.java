package com.example.sliding_test;

import android.location.Geocoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Api_adrss  {
    String adrs,index_y,index_x,index_stayion,index_line,index_btrainNo;
    String index_trains="열차",index_Arrival="도착역",index_Current="현재역",index_Upline="상행하행";
    public String adrss(String string1){
        BufferedReader br =  null;

/*
        try{
            String urlstr = "http://api.vworld.kr/req/address?service=address&request=getAddress&key=C7E2DE1C-8090-3CDD-A600-D76315E08739&point="+ string2+","+ string1 +"&type=BOTH&format=json";
            URL url = new URL(urlstr);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            urlconnection.setRequestMethod("GET");
            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(),"UTF-8"));
            String result = "";
            String line;
            if((line = br.readLine()) != null)
                result = result + line + "\n";
            int target_num = result.indexOf("text\" : \"")+9;
            adrs = result.substring(target_num, (result.substring(target_num).indexOf("\", \"structure")+target_num));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
*/
        try{
            BufferedReader br1 = null;

            String urlstr = "http://api.vworld.kr/req/address?service=address&request=getCoord&key=C7E2DE1C-8090-3CDD-A600-D76315E08739&type=PARCEL&address="+string1+"&format=json&crs=EPSG:5181";
            URL url = new URL(urlstr);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            urlconnection.setRequestMethod("GET");
            br1 = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(),"UTF-8"));
            String result1 = "";
            String line1;
            if((line1 = br1.readLine()) != null)
                result1 = result1 + line1 + "\n";
            int target_x= result1.indexOf("\"x\" : \"")+7;
            int target_y= result1.indexOf("\", \"y\" : \"")+10;
            index_x = result1.substring(target_x, (result1.substring(target_x).indexOf("\", \"y\" : \"")+target_x));
            index_y = result1.substring(target_y, (result1.substring(target_y).indexOf("\"}}}}")+target_y));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        try{
            BufferedReader br2 = null;
            String urlstr ="http://swopenapi.seoul.go.kr/api/subway/715a4662486a677736326e4c655544/json/nearBy/0/1/"+index_x+"/"+index_y;
            URL url = new URL(urlstr);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            urlconnection.setRequestMethod("GET");
            br2 = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(),"UTF-8"));
            String result2 = "";
            String line2;
            if((line2 = br2.readLine()) != null)
                result2 = result2 + line2 + "\n";
            int target_stayion= result2.indexOf("\"statnNm\":\"")+11;
            int target_line= result2.indexOf("subwayNm\":\"")+11;
            index_stayion = result2.substring(target_stayion, (result2.substring(target_stayion).indexOf("\",\"subwayId\":")+target_stayion));
            index_line = result2.substring(target_line, (result2.substring(target_line).indexOf("\",\"statnNmEng")+target_line));
        }catch(Exception e){

        }
        try{
            BufferedReader br3 = null;
        String urlstr ="http://swopenAPI.seoul.go.kr/api/subway/6a4565616c6a6777393145686e4972/json/realtimeStationArrival/0/1/"+index_stayion;
        URL url = new URL(urlstr);
        HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
        urlconnection.setRequestMethod("GET");
        br3 = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(),"UTF-8"));
        String result3 = "";
        String line3;
        if((line3 = br3.readLine()) != null)
            result3 = result3 + line3 + "\n";
            int target_btrainNo= result3.indexOf("\"btrainNo\":\"")+12;
            int target_statnm = result3.indexOf("\"statnNm\":\"") + 11;
            int target_upline = result3.indexOf("\"updnLine\":\"") + 12;
            int target_statntnm = result3.indexOf("bstatnNm\":\"") + 11;
            index_btrainNo = result3.substring(target_btrainNo, (result3.substring(target_btrainNo).indexOf("\",\"bstatnId\"")+target_btrainNo));
            index_Current =  result3.substring(target_statnm, (result3.substring(target_statnm).indexOf("\",\"trainCo\"") + target_statnm));     //현재역
            index_Upline =  result3.substring(target_upline, (result3.substring(target_upline).indexOf("\",\"trainLineNm") + target_upline));
            index_Arrival =  result3.substring(target_statntnm, (result3.substring(target_statntnm).indexOf("\",\"recptnDt") + target_statntnm));     //도착역
        }catch (Exception e) {
            String er=" ,통신및위치오류, , , ";
            return er;
        }
        return index_btrainNo+","+index_Current+"  /,"+index_Arrival+"  /,"+index_Upline+","+index_line ;
    }
    public String trains(String lines){
        try {
            if (lines.equals("test")) {
                return "test,천안,용산,센서/test,1111,2222,3333/test,인천,인천,센서/test,1,0,1";
            }
                    BufferedReader br4 = null;
                    String urlstr = "http://swopenAPI.seoul.go.kr/api/subway/6a4565616c6a6777393145686e4972/json/realtimePosition/1/50/" + lines;
                    URL url = new URL(urlstr);
                    HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
                    urlconnection.setRequestMethod("GET");
                    br4 = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
                    String result4 = "";
                    String line4;
                    if ((line4 = br4.readLine()) != null)
                        result4 = result4 + line4 + "\n";
                    for(int i=0;i<50;i++) {
                        int target_statnm = result4.indexOf("\"statnNm\":\"") + 11;
                        int target_trainNo = result4.indexOf("\",\"trainNo\":\"") + 13;
                        int target_upline = result4.indexOf("\"updnLine\":\"") + 12;
                        int target_statntnm = result4.indexOf("statnTnm\":\"") + 11;
                        index_Current = index_Current + "," + result4.substring(target_statnm, (result4.substring(target_statnm).indexOf("\",\"trainNo\":\"") + target_statnm));     //현재역
                        index_trains = index_trains + "," + result4.substring(target_trainNo, (result4.substring(target_trainNo).indexOf("\",\"lastRecptnDt") + target_trainNo));     //열차아이디
                        index_Upline = index_Upline + "," + result4.substring(target_upline, (result4.substring(target_upline).indexOf("\",\"statnTid") + target_upline));
                        index_Arrival = index_Arrival + "," + result4.substring(target_statntnm, (result4.substring(target_statntnm).indexOf("\",\"trainSttus") + target_statntnm));     //도착역
                        String result5 = result4.substring((result4.substring(target_statntnm).indexOf("\",\"trainSttus") + target_statntnm));
                        result4 = result5;
}
            }catch(Exception e){
                System.out.println(e);
            }

        return index_Current+"/"+index_trains+"/"+index_Arrival+"/"+index_Upline;
    }
}

