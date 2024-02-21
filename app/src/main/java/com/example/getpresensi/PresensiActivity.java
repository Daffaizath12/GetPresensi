package com.example.getpresensi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

        // Di dalam onCreate method
        Button buttonCekRiwayatPresensi = findViewById(R.id.buttonCekRiwayatPresensi);
        buttonCekRiwayatPresensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mendapatkan nama pengguna dari intent
                String username = getIntent().getStringExtra("username");

                // Membuka RiwayatPresensiActivity dan mengirimkan username
                Intent intent = new Intent(PresensiActivity.this, RiwayatPresensiActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

    }
}