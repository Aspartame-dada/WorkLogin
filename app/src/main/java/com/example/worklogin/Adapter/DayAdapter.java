package com.example.worklogin.Adapter;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.worklogin.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.ViewHolder> {
    private final Context context;
    private final List<Day> data;

    public DayAdapter(Context context, List<Day> data, OnItemClickListener listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    private DayAdapter.OnItemClickListener listener;



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.day_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Day day=data.get(position);
holder.imageView.setImageResource(day.getBitmap());
    holder.tvYear.setText(day.getName());
        holder.itemView.setOnClickListener(v -> {
            listener.onItemClick(position);

        });
    }

    @Override
    public int getItemCount() {

        return data.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        ImageView imageView;
        TextView tvYear,tvTotal;
        Bitmap bitmap2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.iv);
            layout=itemView.findViewById(R.id.linear_day);
            tvYear=itemView.findViewById(R.id.rv_name);
        }
    }

}
