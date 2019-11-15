package com.example.sliding_test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalViewHolder> {

    private ArrayList<HorizontalData> horizontalData;

    public void setData(ArrayList<HorizontalData> list){
        horizontalData = list;
    }

                                   /**/
    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_items,parent,false);

        HorizontalViewHolder holder = new HorizontalViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(HorizontalViewHolder holder, int position) {

        HorizontalData data = horizontalData.get(position);

        holder.description.setText(data.getText());
        holder.icon.setImageResource(data.getImg());
    }

    @Override
    public int getItemCount() {
        return horizontalData.size();
    }
}
