package com.example.getpresensi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        // Dapatkan data lokasi, tanggal, dan waktu dari MainActivity
        double latitude = getIntent().getDoubleExtra("latitude", 0.0);
        double longitude = getIntent().getDoubleExtra("longitude", 0.0);
        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");

        // Atur TextViews dengan data presensi
        TextView presensiTextView = findViewById(R.id.presensi_text_view);
        presensiTextView.setText("Sudah Melakukan Presensi");

        TextView locationTextView = findViewById(R.id.location_text_view);
        locationTextView.setText("Lokasi: " + latitude + ", " + longitude);

        TextView dateTextView = findViewById(R.id.date_text_view);
        dateTextView.setText("Tanggal: " + date);

        TextView timeTextView = findViewById(R.id.time_text_view);
        timeTextView.setText("Waktu: " + time);

        Button riwayatButton = findViewById(R.id.riwayat_button);
        riwayatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, RiwayatPresensiActivity.class);
                startActivity(intent);
            }
            
        });
    }
}
