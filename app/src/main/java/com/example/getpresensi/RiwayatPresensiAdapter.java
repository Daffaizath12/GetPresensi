package com.example.getpresensi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RiwayatPresensiAdapter extends RecyclerView.Adapter<RiwayatPresensiAdapter.ViewHolder> {

    private Context context;
    private List<RiwayatPresensi> riwayatPresensiList;

    public RiwayatPresensiAdapter(Context context, List<RiwayatPresensi> riwayatPresensiList) {
        this.context = context;
        this.riwayatPresensiList = riwayatPresensiList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_riwayat_presensi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RiwayatPresensi riwayatPresensi = riwayatPresensiList.get(position);
        holder.textViewLocation.setText("Lokasi: " + riwayatPresensi.getLocation());
        holder.textViewDateTime.setText("Waktu & Tanggal: " + riwayatPresensi.getDateTime());
    }

    @Override
    public int getItemCount() {
        return riwayatPresensiList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewLocation, textViewDateTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
            textViewDateTime = itemView.findViewById(R.id.textViewDateTime);
        }
    }
}

