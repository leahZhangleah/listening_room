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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PassedAdapter extends ArrayAdapter<Child> {

    public PassedAdapter(ArrayList<Child> childList,Context context) {
        super(context,0, childList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView child_num_tv,child_name_tv,child_status_tv;
        Child child = getItem(position);
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.child_item,parent,false);
        }
        child_num_tv = convertView.findViewById(R.id.child_number);
        child_name_tv=convertView.findViewById(R.id.child_name);
        child_status_tv=convertView.findViewById(R.id.child_status);

        child_num_tv.setText(String.valueOf(child.number)+"号");
        child_name_tv.setText(child.name);
        child_status_tv.setText(child.status);

        return convertView;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}
