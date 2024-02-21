package com.example.getpresensi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView textViewFullname;
    private TextView textViewUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Menerima data pengguna dari Intent
        String username = getIntent().getStringExtra("username");
        String fullName = getIntent().getStringExtra("fullName");

        // Menampilkan data pengguna di UI
        TextView textViewUsername = findViewById(R.id.textViewUsername);
        TextView textViewFullName = findViewById(R.id.textViewFullname);

        textViewUsername.setText(username);
        textViewFullName.setText(fullName);

        // Anda dapat mengambil data lain dari database dan menampilkannya di sini
        Button buttonEditProfile = findViewById(R.id.buttonEditProfile);
        buttonEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent untuk memulai EditProfileActivity
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("fullName", fullName);
                startActivity(intent);
            }
        });

    }

}


