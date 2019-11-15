package com.example.sliding_test;

public class HorizontalData { //포화도, 노약자석에 대한 이미지 + 수치값 객체 자료구조 데이터형을 위한 클래스

    private int img;
    private String text;

    public HorizontalData(int img, String text){
        this.img = img;
        this.text = text;
    }

    public String getText(){
        return this.text;
    } // text 반환

    public int getImg(){
        return this.img;
    } // 이미지 반환

}
