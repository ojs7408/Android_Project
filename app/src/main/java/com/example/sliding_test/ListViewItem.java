package com.example.sliding_test;


public class ListViewItem {
    private String currentStr ;
    private String arrivalStr ;

    public void setCurrent(String title) {
        currentStr = title ;
    }
    public void setArrival(String desc) {
        arrivalStr = desc ;
    }
    public String getCurrent() {
        return this.currentStr ;
    }
    public String getArrival() {
        return this.arrivalStr ;
    }
}
