package com.example.listeningroom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PassedAdapter extends BaseAdapter {
    private List<Child> childList;

    public PassedAdapter() {
        this.childList = new ArrayList<>();
    }

    public void setChildList(List<Child> newChildList){
        this.childList = newChildList;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        TextView child_num_tv,child_name_tv,child_status_tv;
        Child child = getItem(position);
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.child_item,parent,false);
        }
        child_num_tv = convertView.findViewById(R.id.child_number);
        child_name_tv=convertView.findViewById(R.id.child_name);
        child_status_tv=convertView.findViewById(R.id.child_status);

        child_num_tv.setTextColor(context.getResources().getColor(R.color.passed_number_color));
        child_name_tv.setTextColor(context.getResources().getColor(R.color.passed_number_color));
        child_status_tv.setTextColor(context.getResources().getColor(R.color.passed_number_color));
        child_num_tv.setText(String.valueOf(child.number)+"号");
        child_name_tv.setText(child.name);
        child_status_tv.setText(child.status);

        return convertView;
    }

    @Override
    public int getCount() {
        return childList.size();
    }

    @Override
    public Child getItem(int position) {
        return childList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
