package com.example.worklogin.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.worklogin.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class Imformation extends RecyclerView.Adapter<Imformation.ViewHolder> {

    private  Context context;
    private  List<JobInfo> data;
    private  OnListItemClickListener itemClickListener;

    public Imformation(Context context, List<JobInfo> data, OnListItemClickListener itemClickListener) {
        this.context = context;
        this.data = data;
        this.itemClickListener = itemClickListener;
    }
    public Imformation(){
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemlayout_activities, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JobInfo job=data.get(position);
        holder.textView.setText(job.getNameOfJob()+" "+job.getTimeHours()+" "+job.getDate()+job.ifOffHours(true));
        holder.layout.setOnClickListener(view -> itemClickListener.onItemClick(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public interface OnListItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout=itemView.findViewById(R.id.ll_activities);
            textView=itemView.findViewById(R.id.tv_get_imformation);
        }
    }
}
