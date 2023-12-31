package com.maza.batikku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView btnInput, btnListBatik, btnPegawai, btnListPegawai, textUsername;
    private FirebaseUser firebaseUser;
    private ImageView btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnInput = findViewById(R.id.btnInput);
        btnListBatik = findViewById(R.id.btnListBatik);
        btnPegawai = findViewById(R.id.btnPegawai);
        btnListPegawai = findViewById(R.id.btnOwner);
        btnLogout = findViewById(R.id.btnLogout);
        textUsername = findViewById(R.id.textUsername);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {
            textUsername.setText(firebaseUser.getDisplayName());
        }else{
            textUsername.setText("Login Gagal!");
        }


        btnInput.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), InputToko.class));
        });
        btnListBatik.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), InputBatik.class));
        });
        btnListPegawai.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ListPegawai.class));
        });
        btnPegawai.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), InputPegawai.class));
        });
        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        });
    }
}