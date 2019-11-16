package com.example.sliding_test;

import java.util.ArrayList;

public class SeatCheck {

    private String btrainNo;
    private String date;

    public static ArrayList<HorizontalData> SeatCheck_set(String btrainNo){

        System.out.println(btrainNo);
       // String[] Dbtrain = Dbconnection.UseTable(btrainNo);
        ArrayList<HorizontalData> dataset = new ArrayList<>();

        int i=0;
        while(i<8)
        {
            /*
            if(Dbtrain[i] == "error") //DB에서 받은 정보가 error일 경우 모든 지하철 그림과 수치를 Default로 변경
            {
                for(int j = 0 ; j < 10; j++)
                {
                    dataset.add(new HorizontalData(R.drawable.side_traindefault, 0 +""));
                }
                break;
            }*/



            if(true) //처음에 y일 때
            {
                if(true) // 두번째가 y일 때
                {
                    if(true) // 세번째가 y일 때
                    {
                        dataset.add(new HorizontalData(R.drawable.top_train7,""));
                    }
                    else if(true) //세번째가 n일 때
                    {
                        dataset.add(new HorizontalData(R.drawable.top_train4,""));
                    }
                }
                else if(true) // 두번째가 n일 대
                {
                    if(true) //세번째가 y일 때
                    {
                        dataset.add(new HorizontalData(R.drawable.top_train5,""));
                    }
                    else if(true) //세번째가 n일 때
                    {
                        dataset.add(new HorizontalData(R.drawable.top_train1,""));
                    }

                }

            }

            else if(true) //처음에 n일 때
            {
                if(true) // 두번째가 y일 때
                {
                    if(true) // 세번째가 y일 때
                    {
                        dataset.add(new HorizontalData(R.drawable.top_train6,""));
                    }
                    else if(true) //세번째가 n일 때
                    {
                        dataset.add(new HorizontalData(R.drawable.top_train2,""));
                    }
                }
                else if(true) // 두번째가 n일 대
                {
                    if(true) //세번째가 y일 때
                    {
                        dataset.add(new HorizontalData(R.drawable.top_train3,""));
                    }
                    else if(true) //세번째가 n일 때
                    {
                        dataset.add(new HorizontalData(R.drawable.top_train0,""));
                    }

                }

            }

        }


        return dataset;
    }


}
