package com.example.sliding_test;

import java.util.ArrayList;

public class SeatCheck {

    private String btrainNo;
    private String date;

    public static ArrayList<HorizontalData> SeatCheck_set(String btrainNo, String lines){

        String[] train_line=new String[]{"1호선","2호선","3호선","4호선","5호선","6호선","7호선","8호선","9호선","test"};
        Dbconnection dbconnection = new Dbconnection();
        String[] Dbtrain = dbconnection.Sensor(btrainNo);
        String[] test = new String[10];
        ArrayList<HorizontalData> seatset = new ArrayList<>();
        int i=0;
        int count=0;
        //1칸당 3개씩


        System.out.println(Dbtrain[0]+"hello"); //test
        if(lines.equals(train_line[0])||lines.equals(train_line[2])||lines.equals(train_line[3])||lines.equals(train_line[9])){count=10;}
        else if(lines.equals(train_line[4])||lines.equals(train_line[5])||lines.equals(train_line[6])){count=8;}
        else if(lines.equals(train_line[7])){count=6;}
        else if(lines.equals(train_line[1])||lines.equals(train_line[8])){count=4;}

        for(int j=0,k=0;k<count;k++)
        {
            test[k]=Dbtrain[j+10]+Dbtrain[j+11]+Dbtrain[j+12];
            j+=3;
        }


        while(i<count)
        {

            if(Dbtrain[i] == "error") //DB에서 받은 정보가 error일 경우 모든 지하철 그림과 수치를 Default로 변경
            {
                for(int j = 0 ; j < 10; j++)
                {
                    seatset.add(new HorizontalData(R.drawable.top_train0, ""));
                }
                break;
            }
//yny nyn yny nyn nnn nnn nnn nnn nnn

            if(test[i].equals("yyy")){seatset.add(new HorizontalData(R.drawable.top_train7,""));}
            else if(test[i].equals("yyn")){seatset.add(new HorizontalData(R.drawable.top_train4,""));}
            else if(test[i].equals("yny")){seatset.add(new HorizontalData(R.drawable.top_train5,""));}
            else if(test[i].equals("ynn")){seatset.add(new HorizontalData(R.drawable.top_train1,""));}

            else if(test[i].equals("nyy")){seatset.add(new HorizontalData(R.drawable.top_train6,""));}
            else if(test[i].equals("nyn")){seatset.add(new HorizontalData(R.drawable.top_train2,""));}
            else if(test[i].equals("nny")){seatset.add(new HorizontalData(R.drawable.top_train3,""));}
            else if(test[i].equals("nnn")){seatset.add(new HorizontalData(R.drawable.top_train0,""));}

            i++;
        }


        return seatset;
    }


}
