package com.example.getpresensi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RiwayatPresensiActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RiwayatPresensiAdapter adapter;
    private List<RiwayatPresensi> riwayatPresensiList;
    private TextView textViewNoData;
    private ImageView riwayat_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_presensi);

        recyclerView = findViewById(R.id.recyclerView);
        textViewNoData = findViewById(R.id.textViewNoData);
        riwayat_back = findViewById(R.id.riwayat_back);

        riwayat_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        riwayatPresensiList = new ArrayList<>();
        adapter = new RiwayatPresensiAdapter(this, riwayatPresensiList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // Mendapatkan username pengguna yang sedang login
        String username = getIntent().getStringExtra("username");

        // Membuat instance DatabaseHelper
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        // Mendapatkan riwayat presensi berdasarkan username
        List<RiwayatPresensi> riwayatPresensiByUsername = dbHelper.getRiwayatPresensiByUsername(username);

        // Menampilkan data di RecyclerView jika ada
        if (riwayatPresensiByUsername != null && !riwayatPresensiByUsername.isEmpty()) {
            riwayatPresensiList.addAll(riwayatPresensiByUsername);
            adapter.notifyDataSetChanged();
        } else {
            // Tidak ada data riwayat presensi, tampilkan pesan
            recyclerView.setVisibility(View.GONE);
            textViewNoData.setVisibility(View.VISIBLE);
        }
    }
}
