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

public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.ViewHolder> {
private final Context context;
private final List<Month> data;
private OnItemClickListener listener;

public MonthAdapter(Context context, List<Month> data, OnItemClickListener listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
        }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.month_item, parent,
                false);

        return new ViewHolder(view);
        }

    @Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Month month=data.get(position);
        holder.tvYear.setText(month.getMonth());
        holder.tvTotal.setText(month.getTotaltimes());
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
        layout=itemView.findViewById(R.id.linear_month);
        tvYear=itemView.findViewById(R.id.rv_day);
        tvTotal=itemView.findViewById(R.id.tv_total_times);
    }
}
}
