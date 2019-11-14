package com.example.sliding_test;

import java.util.ArrayList;

public class Figure {
    //DB에서 String 묶어서 올 예정


        private String btrainNo;
/*
    public Figure(String btrainNo){
        this.btrainNo = btrainNo;
        btrainNo="0668";
        String[] Dbtrain = Dbconnection.Subway(btrainNo);
    }
*/

        public static ArrayList<VerticalData> Figure_set(String btrainNo)
        {

            System.out.println(btrainNo);
            String[] DDD = new String[] {"5","15","25","35","45","55","65","75"};
            String[] Dbtrain = Dbconnection.Subway(btrainNo);
            ArrayList<VerticalData> dataset = new ArrayList<>();

            int i=0;
        while(i<8)
        {
            if(Dbtrain[i] == "error")
            {
                for(int j = 0 ; j < 10; j++)
                {
                    dataset.add(new VerticalData(R.drawable.side_traindefault, 0 +""));
                }
                break;
            }

            int holy = Integer.parseInt(Dbtrain[i]);

            if(holy>=0 && holy<=10) {dataset.add(new VerticalData(R.drawable.side_train0, holy +""));}

            else if(holy>=11 && holy<=20) {dataset.add(new VerticalData(R.drawable.side_train10, holy +""));}

            else if(holy>=21 && holy<=30) {dataset.add(new VerticalData(R.drawable.side_train20, holy +""));}

            else if(holy>=31 && holy<=40) {dataset.add(new VerticalData(R.drawable.side_train30, holy +""));}

            else if(holy>=41 && holy<=50) {dataset.add(new VerticalData(R.drawable.side_train40, holy +""));}

            else if(holy>=51 && holy<=60) {dataset.add(new VerticalData(R.drawable.side_train50, holy +""));}

            else if(holy>=61 && holy<=70) {dataset.add(new VerticalData(R.drawable.side_train60, holy +""));}

            else if(holy>=71 && holy<=80) {dataset.add(new VerticalData(R.drawable.side_train70, holy +""));}

            else if(holy>=81 && holy<=90) {dataset.add(new VerticalData(R.drawable.side_train80, holy +""));}

            else if(holy>=91 && holy<=100) {dataset.add(new VerticalData(R.drawable.side_train90, holy +""));}

            i++;
        }

        return dataset;
    }

    public String Figure_check()
    {
        return null;
}
}
