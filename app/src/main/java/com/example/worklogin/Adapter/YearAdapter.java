package com.example.worklogin.Adapter;

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


public class YearAdapter extends RecyclerView.Adapter<YearAdapter.ViewHolder> {
   private final Context context;
   private final List<Year> data;
    private OnItemClickListener listener;

    public YearAdapter(Context context, List<Year> data, OnItemClickListener listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.year_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Year year=data.get(position);
        holder.tvYear.setText(year.getYear()+"");
        holder.tvTotal.setText( year.getTotalTime());
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
        TextView tvYear,tvTotal;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout=itemView.findViewById(R.id.linear_year);
            tvYear=itemView.findViewById(R.id.tv_year);
            tvTotal=itemView.findViewById(R.id.tv_total);
        }
    }
}
