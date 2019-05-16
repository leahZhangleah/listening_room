package com.example.listeningroom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.List;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ViewHolder> {
    List<Child> childList;
    Context context;

    public ChildAdapter(List<Child> childList, Context context) {
        this.childList = childList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.child_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Child child = childList.get(i);
        if(child!=null){
            viewHolder.child_num_tv.setText(String.valueOf(child.getNumber()+"号"));
            viewHolder.child_name_tv.setText(child.getName());
            viewHolder.child_status_tv.setText(child.getStatus());
        }
        if(i==0){
            adoptAnimation(context,viewHolder.itemView);
        }
    }

    public void setChildList(List<Child> newChildList){
        if(childList == newChildList){
            return;
        }else{
            childList.clear();
            childList = newChildList;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        if(childList.isEmpty()||childList==null){
            return 0;
        }else if(childList.size()>5){
            return 5;
        }else{
            return childList.size();
        }
    }

    private void adoptAnimation(Context context,View view){
        Animation blinkAnim = AnimationUtils.loadAnimation(context,R.anim.blink);
        view.startAnimation(blinkAnim);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView child_num_tv,child_name_tv,child_status_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            child_num_tv = itemView.findViewById(R.id.child_number);
            child_name_tv=itemView.findViewById(R.id.child_name);
            child_status_tv=itemView.findViewById(R.id.child_status);
        }
    }
}
