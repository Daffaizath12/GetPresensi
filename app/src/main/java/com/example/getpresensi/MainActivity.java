package com.example.getpresensi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private MapView mapView;
    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationClient;
    private TextView textViewWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewWelcome = findViewById(R.id.textViewWelcome);
        String username = getIntent().getStringExtra("fullName"); // Mengambil nama pengguna dari intent
        textViewWelcome.setText("Welcome, " + username + "!");

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        Button buttonHadirSekarang = findViewById(R.id.buttonHadirSekarang);
        buttonHadirSekarang.setOnClickListener(new View.OnClickListener() {
            // Dalam metode onClick untuk buttonHadirSekarang
            @Override
            public void onClick(View v) {
                // Mendapatkan nama pengguna dari intent
                String username = getIntent().getStringExtra("fullName");

                // Mendeklarasikan final variabel yang dapat diakses dari dalam inner class
                final String[] currentLocation = {"Lokasi terkini belum didapatkan"};

                // Cek apakah izin lokasi sudah diberikan sebelumnya
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    // Izin sudah diberikan, coba mendapatkan lokasi terkini
                    fusedLocationClient.getLastLocation()
                            .addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    if (location != null) {
                                        // Lokasi berhasil didapatkan
                                        double latitude = location.getLatitude();
                                        double longitude = location.getLongitude();

                                        // Mengonversi koordinat menjadi alamat menggunakan Geocoder
                                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                                        try {
                                            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                                            if (addresses != null && !addresses.isEmpty()) {
                                                Address address = addresses.get(0);
                                                currentLocation[0] = address.getAddressLine(0); // Mendapatkan alamat lengkap
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        // Mendapatkan waktu dan tanggal sekarang
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss", Locale.getDefault());
                                        String dateTime = sdf.format(new Date());

                                        // Simpan data presensi ke dalam database
                                        DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);
                                        dbHelper.addRiwayatPresensi(username, currentLocation[0], dateTime);

                                        // Navigasi ke halaman PresensiActivity dan mengirimkan informasi
                                        Intent intent = new Intent(MainActivity.this, PresensiActivity.class);
                                        intent.putExtra("location", currentLocation[0]);
                                        intent.putExtra("username", username);
                                        intent.putExtra("dateTime", dateTime);
                                        startActivity(intent);
                                    } else {
                                        // Tidak dapat mendapatkan lokasi terkini
                                        Toast.makeText(MainActivity.this, "Location not available", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    // Izin belum diberikan, minta izin lokasi kepada pengguna
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            LOCATION_PERMISSION_REQUEST_CODE);
                }
            }
        });

//        Button buttonCekRiwayatPresensi = findViewById(R.id.buttonCekRiwayatPresensi);
//        buttonCekRiwayatPresensi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, RiwayatPresensiActivity.class);
//                startActivity(intent);
//            }
//        });

        // Ketika tombol Cek Profile diklik
        Button buttonCekProfile = findViewById(R.id.buttonCekProfile);
        buttonCekProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = getIntent().getStringExtra("fullName");

                // Membuat instance DatabaseHelper
                DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);

                // Memanggil fungsi untuk mencari username berdasarkan fullName
                String username = dbHelper.getUsernameByFullName(fullName);

                // Jika username ditemukan, kirim ke ProfileActivity
                if (username != null) {
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("fullName", fullName);
                    startActivity(intent);
                } else {
                    // Jika tidak ditemukan, beri pesan kesalahan
                    Toast.makeText(MainActivity.this, "Username not found", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Minta izin lokasi jika belum diberikan
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Jika izin sudah diberikan, dapatkan lokasi pengguna
            getLastLocation();
        }
    }


    private void getLastLocation() {
        // Cek apakah izin lokasi sudah diberikan sebelumnya
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Izin sudah diberikan, coba mendapatkan lokasi terkini
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                // Lokasi berhasil didapatkan, tampilkan di peta
                                LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                googleMap.addMarker(new MarkerOptions().position(userLocation).title("You are here"));
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
                            } else {
                                // Tidak dapat mendapatkan lokasi terkini
                                Toast.makeText(MainActivity.this, "Location not available", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            // Izin belum diberikan, minta izin lokasi kepada pengguna
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                // Izin diberikan, dapatkan lokasi terkini
                getLastLocation();
            } else {
                // Izin ditolak, beri tahu pengguna
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
