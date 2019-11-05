package com.example.sliding_test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Api_adrss  {
    String adrs,index_y,index_x,index_stayion,index_line;
    public String adrss(String string2,String string1){
        BufferedReader br = null;
        BufferedReader br1 = null;
        BufferedReader br2 = null;
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

        try{
            String urlstr = "http://api.vworld.kr/req/address?service=address&request=getCoord&key=C7E2DE1C-8090-3CDD-A600-D76315E08739&type=PARCEL&address="+adrs+"&format=json&crs=EPSG:5181";
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
            String er="통신및위치오류";
            return er;
        }
        return index_stayion;
    }
}
