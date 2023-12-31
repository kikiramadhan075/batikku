package com.maza.batikku;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DialogPegawai extends DialogFragment {
    String Gambar, NoId, Nama, Tanggal, Alamat, Jk, Goldar, Keadaan, key, pilih;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public DialogPegawai(String gambar, String noId, String nama, String tanggal, String alamat, String jk, String goldar, String keadaan, String key, String pilih) {
        Gambar = gambar;
        NoId = noId;
        Nama = nama;
        Tanggal = tanggal;
        Alamat = alamat;
        Jk = jk;
        Goldar = goldar;
        Keadaan = keadaan;
        this.key = key;
        this.pilih = pilih;
    }

    TextView tgambar, tnoid, tnama, ttanggal, talamat, tjk, tgoldar, twni, twna, tkeadaan;
    Button button_simpan;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_input_pegawai, container, false);
        progressBar = view.findViewById(R.id.progres_simpan);
        progressBar.setVisibility(View.GONE);
        tgambar = view.findViewById(R.id.input_foto);
        tnoid = view.findViewById(R.id.input_noid);
        tnama = view.findViewById(R.id.input_nama);
        ttanggal = view.findViewById(R.id.input_tanggal);
        talamat = view.findViewById(R.id.input_alamat);
        tjk = view.findViewById(R.id.input_agama);
        tgoldar = view.findViewById(R.id.opsi_goldar);
        tkeadaan = view.findViewById(R.id.show_keadaan);
        button_simpan = view.findViewById(R.id.button_simpan);

        tgambar.setText(Gambar);
        tnoid.setText(NoId);
        tnama.setText(Nama);
        ttanggal.setText(Tanggal);
        talamat.setText(Alamat);
        tjk.setText(Jk);
        tgoldar.setText(Goldar);
        tkeadaan.setText(Keadaan);
        button_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Gambar = tgambar.getText().toString();
                String NoId = tnoid.getText().toString();
                String Nama = tnama.getText().toString();
                String Tanggal = ttanggal.getText().toString();
                String Alamat = talamat.getText().toString();
                String Jk = tjk.getText().toString();
                String Goldar = tgoldar.getText().toString();
                String Keadaan = tkeadaan.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                if (pilih.equals("Ubah")){
                    database.child("Pegawai").child(key).setValue(new ModelPegawai(Gambar, NoId, Nama, Tanggal, Alamat, Jk, Goldar, Keadaan )).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(view.getContext(),"Berhasil diubah",Toast.LENGTH_SHORT);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(view.getContext(),"Maaf gagal diubah",Toast.LENGTH_SHORT);
                        }
                    });
                }
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if(dialog != null){
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
