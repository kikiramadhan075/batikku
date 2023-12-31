package com.maza.batikku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InputBatik extends AppCompatActivity {
    EditText NoId;
    EditText Batik, Asal, Makna;
    ProgressBar progressBar;
    Button Simpan, btnTest;

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_batik);
        progressBar = findViewById(R.id.progres_simpan);
        progressBar.setVisibility(View.GONE);
        NoId = findViewById(R.id.input_kode);
        Batik = findViewById(R.id.input_nama);
        Asal = findViewById(R.id.asal_batik);
        Makna = findViewById(R.id.makna_batik);
        Simpan = findViewById(R.id.button_simpan);
        btnTest = findViewById(R.id.btnTest);

        Simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getNoId = NoId.getText().toString();
                String getBatik = Batik.getText().toString();
                String getAsal = Asal.getText().toString();
                String getMakna = Makna.getText().toString();

                if(getNoId.isEmpty()){
                    NoId.setError("Masukkan No. ID Batik");
                }else if(getBatik.isEmpty()){
                    Batik.setError("Masukkan nama batik");
                }else {
                    database.child("Batik").push().setValue(new ModelBatik(getNoId,getBatik,getAsal,getMakna)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(InputBatik.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(InputBatik.this, ListBatik.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(InputBatik.this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ListBatik.class));
            }
        });
    }
}