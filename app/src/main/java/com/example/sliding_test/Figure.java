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
//4량 2호선 6량 8호선이나 분당선, 8량 5, 6, 7호선, 10량 1, 2, 3, 4호선
        public static ArrayList<HorizontalData> Figure_set(String btrainNo, String lines)// MainActivity에서 열차 번호를 매개변수로 보내면 ArrayList<HorizontalData>형식으로 Return 시켜줌
        {
            String[] train_line=new String[]{"1호선","2호선","3호선","4호선","5호선","6호선","7호선","8호선","9호선","test"};
            //String[] Dbtrain = Dbconnection.Subway(btrainNo);//매개변수로 받은 열차 번호로 DB Class에 보내어 포화도 수치값을 Return 받음
            String[] Dbtrain = Dbconnection.Sensor1(btrainNo,"Figure");
            // Return받은 수치값은 배열 형식
            ArrayList<HorizontalData> dataset = new ArrayList<>(); //MainActivity로 다시 Set한 포화도 그림과 수치를 Return하기 위해 dataset이라는  자료구조 변수 선언
            int count=0;
            if(lines.equals(train_line[0])||lines.equals(train_line[2])||lines.equals(train_line[3])||lines.equals(train_line[9])){count=10;}
            else if(lines.equals(train_line[4])||lines.equals(train_line[5])||lines.equals(train_line[6])){count=8;}
            else if(lines.equals(train_line[7])){count=6;}
            else if(lines.equals(train_line[1])||lines.equals(train_line[8])){count=4;}



            int i=0;// String[] Dbtrain의 배열 인덱스 변수 i 선언, 초기화
        while(i<count)//해당 호선의 지하철 칸 수 만큼 반복
        {
            if(Dbtrain[i] == "error") //DB에서 받은 정보가 error일 경우 모든 지하철 그림과 수치를 Default로 변경
            {
                for(int j = 0 ; j < 10; j++)
                {
                    dataset.add(new HorizontalData(R.drawable.side_traindefault, 0 +""));
                }
                break;
            }

            int holy = Integer.parseInt(Dbtrain[i]); // DB에서 전달받은 수치값을 int형 변환

            /*     수치에 따라 해당 수치값에 맞는 지하철 그림 Set + 수치값 포함         */
            if(holy>=0 && holy<=10) {dataset.add(new HorizontalData(R.drawable.side_train0, holy +""));}

            else if(holy>=11 && holy<=20) {dataset.add(new HorizontalData(R.drawable.side_train10, holy +""));}

            else if(holy>=21 && holy<=30) {dataset.add(new HorizontalData(R.drawable.side_train20, holy +""));}

            else if(holy>=31 && holy<=40) {dataset.add(new HorizontalData(R.drawable.side_train30, holy +""));}

            else if(holy>=41 && holy<=50) {dataset.add(new HorizontalData(R.drawable.side_train40, holy +""));}

            else if(holy>=51 && holy<=60) {dataset.add(new HorizontalData(R.drawable.side_train50, holy +""));}

            else if(holy>=61 && holy<=70) {dataset.add(new HorizontalData(R.drawable.side_train60, holy +""));}

            else if(holy>=71 && holy<=80) {dataset.add(new HorizontalData(R.drawable.side_train70, holy +""));}

            else if(holy>=81 && holy<=90) {dataset.add(new HorizontalData(R.drawable.side_train80, holy +""));}

            else if(holy>=91 && holy<=100) {dataset.add(new HorizontalData(R.drawable.side_train90, holy +""));}

            i++; //인덱스 증가
        }
            return dataset; //dataset 자료구조 반환
        }

    public String Figure_check()
    {
        return null;
}
}
