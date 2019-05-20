package com.example.listeningroom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PassedAdapter extends BaseAdapter {
    private List<Ghmsg> patientList;

    public PassedAdapter() {
        this.patientList = new ArrayList<>();
    }

    public void setPatientList(List<Ghmsg> newPatientList){
        if(this.patientList.get(0)!=newPatientList.get(0)){
            this.patientList = newPatientList;
            notifyDataSetChanged();
        }

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        TextView child_num_tv,child_name_tv,child_status_tv;
        Ghmsg patient = getItem(position);
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.patient_item,parent,false);
        }
        child_num_tv = convertView.findViewById(R.id.child_number);
        child_name_tv=convertView.findViewById(R.id.child_name);
        child_status_tv=convertView.findViewById(R.id.child_status);

        child_num_tv.setTextColor(context.getResources().getColor(R.color.passed_number_color));
        child_name_tv.setTextColor(context.getResources().getColor(R.color.passed_number_color));
        child_status_tv.setTextColor(context.getResources().getColor(R.color.passed_number_color));
        child_num_tv.setText(patient.getPdhm()+"号");
        child_name_tv.setText(patient.getBrxm());
        child_status_tv.setText("过号");

        return convertView;
    }

    @Override
    public int getCount() {
        return patientList.size();
    }

    @Override
    public Ghmsg getItem(int position) {
        return patientList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
