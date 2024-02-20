package com.example.getpresensi;

// PresensiAdapter.java
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PresensiAdapter extends RecyclerView.Adapter<PresensiAdapter.ViewHolder> {

    private final Context context;
    private final List<Presensi> presensiList;

    public PresensiAdapter(Context context, List<Presensi> presensiList) {
        this.context = context;
        this.presensiList = presensiList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_presensi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Presensi presensi = presensiList.get(position);
        holder.textLocation.setText("Lokasi: " + presensi.getLatitude() + ", " + presensi.getLongitude());
        holder.textDate.setText("Tanggal: " + presensi.getDate());
        holder.textTime.setText("Waktu: " + presensi.getTime());
    }

    @Override
    public int getItemCount() {
        return presensiList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textDate, textTime, textLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textLocation = itemView.findViewById(R.id.text_location);
            textDate = itemView.findViewById(R.id.text_date);
            textTime = itemView.findViewById(R.id.text_time);
        }
    }
}

