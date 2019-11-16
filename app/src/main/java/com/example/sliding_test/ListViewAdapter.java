package com.example.sliding_test;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;
    // ListViewAdapter의 생성자
    public ListViewAdapter() {
    }
    // Adapter에 사용되는 데이터의 개수를 리턴.
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.popup_items, parent, false);
        }
        TextView titleTextView =  convertView.findViewById(R.id.itemss1) ;
        TextView descTextView =  convertView.findViewById(R.id.itemss2) ;
        TextView uplineTextView = convertView.findViewById(R.id.itemss4);
        ListViewItem listViewItem = listViewItemList.get(position);
        titleTextView.setText(listViewItem.getCurrent());
        descTextView.setText(listViewItem.getArrival());
        uplineTextView.setText(listViewItem.getUpline());
        return convertView;
    }
    @Override
    public long getItemId(int position) {
        return position ;
    }
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }
    public void addItem( String current, String arrival,String upline,String trains) {
        ListViewItem item = new ListViewItem();
        item.setCurrent(current);
        item.setArrival(arrival);
        item.setUpline(upline);
        item.settrains(trains);
        listViewItemList.add(item);
    }
}
