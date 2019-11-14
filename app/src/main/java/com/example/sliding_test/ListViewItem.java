package com.example.sliding_test;


public class ListViewItem {
    private String currentStr ;
    private String arrivalStr ;
    private String uplineStr;
    private String trainsStr;

    public void setCurrent(String current) {
        currentStr = current ;
    }
    public void setArrival(String arrival) {
        arrivalStr = arrival ;
    }
    public void setUpline(String upline){
        uplineStr = upline;
    }
    public void settrains (String trains){
        trainsStr = trains;
    }

    public String getCurrent() {
        return this.currentStr ;
    }
    public String getArrival() {
        return this.arrivalStr ;
    }
    public String getUpline() {
        return this.uplineStr ;
    }
    public String gettrains() { return  this.trainsStr;}
}
