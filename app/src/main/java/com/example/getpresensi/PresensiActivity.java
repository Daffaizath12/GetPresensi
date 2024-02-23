package com.example.getpresensi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PresensiActivity extends AppCompatActivity {

    private TextView textViewUsername, textViewLocation, textViewDateTime;
    private ImageView presensi_back;

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

//        // Menampilkan informasi di tampilan presensi
        textViewUsername.setText("Nama Pengguna: " + username);
        textViewLocation.setText("Lokasi Presensi: " + location);
        textViewDateTime.setText("Waktu dan Tanggal Presensi: " + dateTime);

        // Di dalam onCreate method
        Button buttonCekRiwayatPresensi = findViewById(R.id.buttonCekRiwayatPresensi);
        buttonCekRiwayatPresensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mendapatkan nama pengguna dari intent
                String username = getIntent().getStringExtra("username");

                // Membuka RiwayatPresensiActivity dan mengirimkan username
                Intent intent = new Intent(PresensiActivity.this, RiwayatPresensiActivity.class);
                intent.putExtra("location", location);
                intent.putExtra("dateTime", dateTime);
                intent.putExtra("username", username);
                intent.putExtra("fullName", username);
                startActivity(intent);
            }
        });

        presensi_back = findViewById(R.id.presensi_back);
        presensi_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mendapatkan nama pengguna dari intent
                String username = getIntent().getStringExtra("username");
                String fullName = getIntent().getStringExtra("fullName");

                // Kembali ke MainActivity dan mengirimkan fullName
                Intent intent = new Intent(PresensiActivity.this, MainActivity.class);
                intent.putExtra("fullName", fullName);
                startActivity(intent);
                finish(); // Menutup PresensiActivity
            }
        });

    }
}