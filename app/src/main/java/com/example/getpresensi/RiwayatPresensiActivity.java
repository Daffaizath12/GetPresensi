package com.example.getpresensi;

// RiwayatPresensiActivity.java
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class RiwayatPresensiActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PresensiAdapter presensiAdapter;
    private List<Presensi> presensiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_presensi);

        recyclerView = findViewById(R.id.recycler_view);
        presensiList = new ArrayList<>();
        presensiAdapter = new PresensiAdapter(this, presensiList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(presensiAdapter);

        // Load data presensi dari database dan tampilkan pada RecyclerView
        loadPresensiData();
    }

    private void loadPresensiData() {
        DatabaseHelper db = new DatabaseHelper(this);
        Cursor cursor = db.getAllPresensi();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int latitudeIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LATITUDE);
                int longitudeIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LONGITUDE);
                int dateIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE);
                int timeIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_TIME);

                do {
                    double latitude = cursor.getDouble(latitudeIndex);
                    double longitude = cursor.getDouble(longitudeIndex);
                    String date = cursor.getString(dateIndex);
                    String time = cursor.getString(timeIndex);

                    Log.d("RiwayatPresensi", "Latitude: " + latitude + ", Longitude: " + longitude + ", Date: " + date + ", Time: " + time);

                    Presensi presensi = new Presensi(latitude, longitude, date, time);
                    presensiList.add(presensi);
                } while (cursor.moveToNext());
            } else {
                Log.d("RiwayatPresensi", "No data available in the cursor");
            }

            cursor.close();
        } else {
            Log.d("RiwayatPresensi", "Cursor is null");
        }

        presensiAdapter.notifyDataSetChanged();
    }
}

