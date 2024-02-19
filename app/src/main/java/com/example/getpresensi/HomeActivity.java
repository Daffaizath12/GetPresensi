package com.example.getpresensi;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Menerima data lokasi dan waktu dari MainActivity
        double latitude = getIntent().getDoubleExtra("latitude", 0.0);
        double longitude = getIntent().getDoubleExtra("longitude", 0.0);

        // Menginisialisasi TextView
        TextView locationTextView = findViewById(R.id.location_info_textview);
        TextView timeTextView = findViewById(R.id.time_info_textview);

        // Menampilkan data lokasi pengguna
        String locationInfo = "Lokasi: " + latitude + ", " + longitude;
        locationTextView.setText(locationInfo);

        // Mendapatkan waktu presensi saat ini (Anda dapat menggunakan library atau cara lain sesuai kebutuhan aplikasi Anda)
        String currentTime = getCurrentTime();

        // Menampilkan waktu presensi pengguna
        String timeInfo = "Waktu Presensi: " + currentTime;
        timeTextView.setText(timeInfo);
    }

    // Metode untuk mendapatkan waktu saat ini (contoh sederhana, sesuaikan sesuai kebutuhan aplikasi Anda)
    private String getCurrentTime() {
        // Implementasi sederhana untuk mendapatkan waktu saat ini
        // Misalnya, Anda dapat menggunakan SimpleDateFormat atau library waktu lainnya
        return "12:00 PM"; // Contoh: mengembalikan waktu dalam format jam:menit AM/PM
    }
}
