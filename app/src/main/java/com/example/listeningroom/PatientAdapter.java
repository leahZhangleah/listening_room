package com.example.listeningroom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.ViewHolder> {
    List<Waitmsg> waitList;
    Context context;
    WaitingCallback waitingCallback;

    public PatientAdapter(WaitingCallback waitingCallback,Context context) {
        this.context = context;
        waitList = new ArrayList<>();
        this.waitingCallback=waitingCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.patient_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Waitmsg patient = waitList.get(i);
        if(patient!=null){
            viewHolder.child_num_tv.setText(String.valueOf(patient.getPdhm()+"号"));
            viewHolder.child_name_tv.setText(patient.getBrxm());
            viewHolder.child_status_tv.setText("等待");
        }
        if(i==0 && waitList.get(i)!=null){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.topMargin = dp2px(context,15);
            params.bottomMargin = dp2px(context,15);
            viewHolder.itemView.setLayoutParams(params);
            adoptAnimation(context,viewHolder.itemView);
            viewHolder.child_status_tv.setText("检查");
        }
    }

    private int dp2px(Context context,float dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,context.getResources().getDisplayMetrics());
    }


    public void setPatientList(List<Waitmsg> newPatientList){
        //todo
        if(this.waitList!=newPatientList){
            this.waitList = newPatientList;
            notifyDataSetHasChanged();
        }
    }

    private void notifyDataSetHasChanged(){
        notifyDataSetChanged();
        if(waitList.get(0)!=null){
            waitingCallback.addNewSpeech(waitList.get(0                                                         ));
        }
    }

    @Override
    public int getItemCount() {
        if(waitList.size()>5){
            return 5;
        }else{
            return waitList.size();
        }
    }

    private void adoptAnimation(Context context,View view){
        Animation blinkAnim = AnimationUtils.loadAnimation(context,R.anim.blink);
        //blinkAnim.restrictDuration(3000);
        view.startAnimation(blinkAnim);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView child_num_tv,child_name_tv,child_status_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            child_num_tv = itemView.findViewById(R.id.child_number);
            child_name_tv=itemView.findViewById(R.id.child_name);
            child_status_tv=itemView.findViewById(R.id.child_status);

            child_num_tv.setTextColor(context.getResources().getColor(R.color.calling_number_color));
            child_name_tv.setTextColor(context.getResources().getColor(R.color.calling_number_color));
            child_status_tv.setTextColor(context.getResources().getColor(R.color.calling_number_color));
        }
    }
}
