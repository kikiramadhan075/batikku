package com.maza.batikku;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdapterBatik extends RecyclerView.Adapter<AdapterBatik.MyViewHolder> {
    private List<ModelBatik> mList;
    private Activity activity;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public AdapterBatik(List<ModelBatik> mList, Activity activity){
        this.mList = mList;
        this.activity = activity;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.item_batik, parent, false);
        return new AdapterBatik.MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ModelBatik data = mList.get(position);
        holder.tv_id.setText("NoId : " + data.getNoId());
        holder.tv_batik.setText("Batik : " + data.getBatik());
        holder.tv_asal.setText("Asal : " + data.getAsal());
        holder.tv_makna.setText("Makna : " + data.getMakna());
        holder.btn_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int iya) {
                        database.child("Batik").child(data.getKey()).removeValue().addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(activity, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(activity, "Data gagal dihapus", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setMessage("Apakah yakin mau menghapus? " + data.getBatik());
                builder.show();
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            FragmentManager manager = ((AppCompatActivity)activity).getSupportFragmentManager();
            DialogForm dialog = new DialogForm(
                    data.getNoId(),
                    data.getBatik(),
                    data.getAsal(),
                    data.getMakna(),
                    data.getKey(),
                    "Ubah"
            );
            dialog.show(manager, "form");
            return true;
        });




    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_id, tv_batik, tv_asal, tv_makna;
        ImageView btn_hapus;
        CardView card_hasil;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_batik = itemView.findViewById(R.id.tv_batik);
            tv_asal = itemView.findViewById(R.id.tv_asal);
            tv_makna = itemView.findViewById(R.id.tv_makna);
            card_hasil = itemView.findViewById(R.id.card_hasil);
            btn_hapus = itemView.findViewById(R.id.hapus);
        }
    }
}
