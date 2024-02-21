package com.example.getpresensi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class PresensiActivity extends AppCompatActivity {

    private TextView textViewUsername, textViewLocation, textViewDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presensi);

        textViewUsername = findViewById(R.id.textViewUsername);
        textViewLocation = findViewById(R.id.textViewLocation);
        textViewDateTime = findViewById(R.id.textViewDateTime);

        // Mendapatkan informasi yang dikirim dari MainActivity
        String username = getIntent().getStringExtra("username");
        String location = getIntent().getStringExtra("location");
        String dateTime = getIntent().getStringExtra("dateTime");

        // Menampilkan informasi di tampilan presensi
        textViewUsername.setText("Nama Pengguna: " + username);
        textViewLocation.setText("Lokasi Presensi: " + location);
        textViewDateTime.setText("Waktu & Tanggal Presensi: " + dateTime);
    }
}