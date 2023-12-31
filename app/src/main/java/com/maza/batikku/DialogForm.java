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

public class DialogForm extends DialogFragment {
    String NoId, Batik, Asal, Makna, key, pilih;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public DialogForm(String noId, String batik, String asal, String makna, String key, String pilih) {
        NoId = noId;
        Batik = batik;
        Asal = asal;
        Makna = makna;
        this.key = key;
        this.pilih = pilih;
    }

    TextView tnoid, tbatik, tasal, tmakna;
    Button button_simpan;
    ProgressBar progressBar;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_input_batik, container, false);
        progressBar = view.findViewById(R.id.progres_simpan);
        progressBar.setVisibility(View.GONE);
        tnoid = view.findViewById(R.id.input_kode);
        tbatik = view.findViewById(R.id.input_nama);
        tasal = view.findViewById(R.id.asal_batik);
        tmakna = view.findViewById(R.id.makna_batik);
        button_simpan = view.findViewById(R.id.button_simpan);

        tnoid.setText(NoId);
        tbatik.setText(Batik);
        tasal.setText(Asal);
        tmakna.setText(Makna);
        button_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NoId = tnoid.getText().toString();
                String aBatik = tbatik.getText().toString();
                String bAsal = tasal.getText().toString();
                String aMakna = tmakna.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                if (pilih.equals("Ubah")){
                    database.child("Batik").child(key).setValue(new ModelBatik(NoId, aBatik, bAsal, aMakna)).addOnSuccessListener(new OnSuccessListener<Void>() {
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
