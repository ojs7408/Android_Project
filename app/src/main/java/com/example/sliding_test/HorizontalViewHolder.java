package com.example.sliding_test;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class HorizontalViewHolder extends RecyclerView.ViewHolder { //HorizontalAdapter 클래스에서 기본 라이브러리 RecyclerView를 커스텀으로 오버라이딩하기 위한 클래스

    public ImageView icon; //이미지 그림
    public TextView description; //수치값 텍스트

    public HorizontalViewHolder(View itemView){
        super(itemView);
        icon = (ImageView) itemView.findViewById(R.id.train_icon); //recycleitems.xml 기본 지하철 이미지로 세팅
        description=(TextView)itemView.findViewById(R.id.Horizontal_description); //rectcleitems.xml 기본 수치값 0으로 세팅
    }

}
