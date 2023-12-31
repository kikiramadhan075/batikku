//package com.maza.batikku;
//
//import static android.text.TextUtils.isEmpty;
//
//import androidx.activity.result.ActivityResult;
//import androidx.activity.result.ActivityResultCallback;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.annotation.NonNull;
//import androidx.annotation.RequiresApi;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.DatePickerDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.text.SimpleDateFormat;
//
//public class InputOwner extends AppCompatActivity {
//    TextView ouputTanggal;
//    CheckBox mampu, tidak_mampu;
//    EditText NoId;
//    EditText Nama, Alamat;
//    Spinner Agama;
//    RadioButton radioButton;
//    RadioGroup goldar;
//    Button Simpan, btnLogout, inputTanggal;
//    ImageView Gambar;
//    ProgressBar progressBar;
//    DatePickerDialog datePickerDialog;
//    SimpleDateFormat dateFormatter;
//
//    String getNoID, getNama, getTanggal, getAlamat, getAgama, getGoldar, getGambar, getKeadaan, cbox;
//
//    //    Input Foto
//    ActivityResultLauncher<Intent> activityResultLauncher;
//    Uri fotoUrl;
//
//    //   Firebase Connection
//    DatabaseReference getReference;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_input_owner);
//        //        Get ID
//
//        progressBar = findViewById(R.id.progres_simpan);
//        progressBar.setVisibility(View.GONE);
//        NoId = findViewById(R.id.input_noid);
//        Nama = findViewById(R.id.input_nama);
//        Alamat = findViewById(R.id.input_alamat);
//        Agama = findViewById(R.id.input_agama);
//        goldar = findViewById(R.id.opsi_goldar);
//        Simpan = findViewById(R.id.button_simpan);
//        btnLogout = findViewById(R.id.btn_logout);
//        Gambar = findViewById(R.id.input_foto);
//        inputTanggal = findViewById(R.id.input_tanggal);
//        ouputTanggal = findViewById(R.id.output_tanggal);
//        mampu = findViewById(R.id.mampu);
//        tidak_mampu = findViewById(R.id.tidak_mampu);
//        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
//
//        mampu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                cbox = "Mampu";
//            }
//        });
//
//        tidak_mampu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                cbox = "Tidak Mampu";
//            }
//        });
//
//
//        //      Mendapatkan Data dari Firebase
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        getReference = database.getReference();
//
//
////        Ketika Tanggal Dipencet
//        inputTanggal.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onClick(View view) {
//                TampilTanggal();
//            }
//        });
//
//
////        Ketika Gambar Di Klik
//        Gambar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                activityResultLauncher.launch(intent);
//            }
//        });
//        //      Get Foto
//        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//            @Override
//            public void onActivityResult(ActivityResult result) {
//                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
//                    fotoUrl = result.getData().getData();
//                    Gambar.setImageURI(fotoUrl);
//                    Log.e("Data Lokasi Foto : ", String.valueOf(fotoUrl));
//
//                    Simpan.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            progressBar.setVisibility(View.VISIBLE);
//                            StorageReference filePath = storage.getReference().child("ImagePost").child(fotoUrl.getLastPathSegment());
//                            filePath.putFile(fotoUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                    Task<Uri> dowload = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Uri> task) {
//                                            getGambar = task.getResult().toString();
//                                            getNoID = NoId.getText().toString();
//                                            getNama = Nama.getText().toString();
//                                            getTanggal = ouputTanggal.getText().toString();
//                                            getAgama = Agama.getSelectedItem().toString();
//                                            getAlamat = Alamat.getText().toString();
//                                            getKeadaan = cbox;
//                                            int selectedRating = goldar.getCheckedRadioButtonId();
//                                            radioButton = goldar.findViewById(selectedRating);
//                                            getGoldar = (String) radioButton.getText();
//
//                                            if (checkInput() == true) {
//                                                progressBar.setVisibility(View.GONE);
//                                                Toast.makeText(getContext(), "Data tidak boleh Kosong!", Toast.LENGTH_SHORT).show();
//                                            } else {
//                                                checkUser();
//                                            }
//                                        }
//                                    });
//                                }
//                            });
//                        }
//                    });
//                }
//            }
//        });
//
//        Simpan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (checkInput() == true) {
//                    progressBar.setVisibility(View.GONE);
//                    Toast.makeText(getContext(), "Data tidak boleh Kosong!", Toast.LENGTH_SHORT).show();
//                } else {
//                    progressBar.setVisibility(View.VISIBLE);
//                    checkUser();
//                }
//            }
//        });
//
//
////
//    }
//
//    private void TampilTanggal() {
//        Tanggal tanggal = new Tanggal();
//        tanggal.show(getSupportFragmentManager(), "data");
//        tanggal.setOnDateClickListener(new Tanggal.onDateClickListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                String tahun = "" + datePicker.getYear();
//                String bulan = "" + (datePicker.getMonth() + 1);
//                String hari = "" + datePicker.getDayOfMonth();
//                String text = hari + " - " + bulan + " - " + tahun;
//                ouputTanggal.setText(text);
//            }
//        });
//    }
//
//    private Context getContext() {
//
//        return null;
//    }
//
//
//
//
//    //
//    private boolean checkInput() {
//        boolean emtpyInput = false;
//        if (isEmpty(getGambar)||isEmpty(getNoID) ||isEmpty(getNama) ||isEmpty(getTanggal)||isEmpty(getAgama)
//                ||isEmpty(getAlamat)
//                ||isEmpty(getGoldar)){
//            emtpyInput = true;
//        }
//        return emtpyInput;
//    }
//
//    private void checkUser() {
//        if (isEmpty(getGambar)||isEmpty(getNoID) ||isEmpty(getNama)||isEmpty(getTanggal)||isEmpty(getAgama)
//                ||isEmpty(getAlamat)
//                ||isEmpty(getGoldar)){
//            Toast.makeText(getContext(), "Data tidak boleh Kosong!", Toast.LENGTH_SHORT).show();
//        }else{
//            getReference.child("Admin").child("Data Owner").push()
//                    .setValue(new data_batikku(getNoID,getNama,getTanggal,getAgama,getAlamat,
//                            getGoldar,getKeadaan ,getGambar)).addOnSuccessListener(new OnSuccessListener<Void>() {
//                @Override
//                public void onSuccess(Void unused) {
//                    startActivity(new Intent(getApplicationContext(), ToastToko.class));
//                    Gambar.setImageResource(R.mipmap.ic_launcher);
//                    NoId.setText("");
//                    Nama.setText("");
//                    ouputTanggal.setText("");
//                    Alamat.setText("");
//                    progressBar.setVisibility(View.GONE);
//                }
//            });
//
//        }
//}
//package com.maza.batikku;
//
//import static android.text.TextUtils.isEmpty;
//
//import androidx.activity.result.ActivityResult;
//import androidx.activity.result.ActivityResultCallback;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.annotation.NonNull;
//import androidx.annotation.RequiresApi;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.DatePickerDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.text.SimpleDateFormat;
//
//public class InputOwner extends AppCompatActivity {
//    TextView ouputTanggal;
//    CheckBox mampu, tidak_mampu;
//    EditText NoId;
//    EditText Nama, Alamat;
//    Spinner Agama;
//    RadioButton radioButton;
//    RadioGroup goldar;
//    Button Simpan, btnLogout, inputTanggal;
//    ImageView Gambar;
//    ProgressBar progressBar;
//    DatePickerDialog datePickerDialog;
//    SimpleDateFormat dateFormatter;
//
//    String getNoID, getNama, getTanggal, getAlamat, getAgama, getGoldar, getGambar, getKeadaan, cbox;
//
//    //    Input Foto
//    ActivityResultLauncher<Intent> activityResultLauncher;
//    Uri fotoUrl;
//
//    //   Firebase Connection
//    DatabaseReference getReference;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_input_owner);
//        //        Get ID
//
//        progressBar = findViewById(R.id.progres_simpan);
//        progressBar.setVisibility(View.GONE);
//        NoId = findViewById(R.id.input_noid);
//        Nama = findViewById(R.id.input_nama);
//        Alamat = findViewById(R.id.input_alamat);
//        Agama = findViewById(R.id.input_agama);
//        goldar = findViewById(R.id.opsi_goldar);
//        Simpan = findViewById(R.id.button_simpan);
//        btnLogout = findViewById(R.id.btn_logout);
//        Gambar = findViewById(R.id.input_foto);
//        inputTanggal = findViewById(R.id.input_tanggal);
//        ouputTanggal = findViewById(R.id.output_tanggal);
//        mampu = findViewById(R.id.mampu);
//        tidak_mampu = findViewById(R.id.tidak_mampu);
//        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
//
//        mampu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                cbox = "Mampu";
//            }
//        });
//
//        tidak_mampu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                cbox = "Tidak Mampu";
//            }
//        });
//
//
//        //      Mendapatkan Data dari Firebase
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        getReference = database.getReference();
//
//
////        Ketika Tanggal Dipencet
//        inputTanggal.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onClick(View view) {
//                TampilTanggal();
//            }
//        });
//
//
////        Ketika Gambar Di Klik
//        Gambar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                activityResultLauncher.launch(intent);
//            }
//        });
//        //      Get Foto
//        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//            @Override
//            public void onActivityResult(ActivityResult result) {
//                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
//                    fotoUrl = result.getData().getData();
//                    Gambar.setImageURI(fotoUrl);
//                    Log.e("Data Lokasi Foto : ", String.valueOf(fotoUrl));
//
//                    Simpan.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            progressBar.setVisibility(View.VISIBLE);
//                            StorageReference filePath = storage.getReference().child("ImagePost").child(fotoUrl.getLastPathSegment());
//                            filePath.putFile(fotoUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                    Task<Uri> dowload = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Uri> task) {
//                                            getGambar = task.getResult().toString();
//                                            getNoID = NoId.getText().toString();
//                                            getNama = Nama.getText().toString();
//                                            getTanggal = ouputTanggal.getText().toString();
//                                            getAgama = Agama.getSelectedItem().toString();
//                                            getAlamat = Alamat.getText().toString();
//                                            getKeadaan = cbox;
//                                            int selectedRating = goldar.getCheckedRadioButtonId();
//                                            radioButton = goldar.findViewById(selectedRating);
//                                            getGoldar = (String) radioButton.getText();
//
//                                            if (checkInput() == true) {
//                                                progressBar.setVisibility(View.GONE);
//                                                Toast.makeText(getContext(), "Data tidak boleh Kosong!", Toast.LENGTH_SHORT).show();
//                                            } else {
//                                                checkUser();
//                                            }
//                                        }
//                                    });
//                                }
//                            });
//                        }
//                    });
//                }
//            }
//        });
//
//        Simpan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (checkInput() == true) {
//                    progressBar.setVisibility(View.GONE);
//                    Toast.makeText(getContext(), "Data tidak boleh Kosong!", Toast.LENGTH_SHORT).show();
//                } else {
//                    progressBar.setVisibility(View.VISIBLE);
//                    checkUser();
//                }
//            }
//        });
//
//
////
//    }
//
//    private void TampilTanggal() {
//        Tanggal tanggal = new Tanggal();
//        tanggal.show(getSupportFragmentManager(), "data");
//        tanggal.setOnDateClickListener(new Tanggal.onDateClickListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                String tahun = "" + datePicker.getYear();
//                String bulan = "" + (datePicker.getMonth() + 1);
//                String hari = "" + datePicker.getDayOfMonth();
//                String text = hari + " - " + bulan + " - " + tahun;
//                ouputTanggal.setText(text);
//            }
//        });
//    }
//
//    private Context getContext() {
//
//        return null;
//    }
//
//
//
//
//    //
//    private boolean checkInput() {
//        boolean emtpyInput = false;
//        if (isEmpty(getGambar)||isEmpty(getNoID) ||isEmpty(getNama) ||isEmpty(getTanggal)||isEmpty(getAgama)
//                ||isEmpty(getAlamat)
//                ||isEmpty(getGoldar)){
//            emtpyInput = true;
//        }
//        return emtpyInput;
//    }
//
//    private void checkUser() {
//        if (isEmpty(getGambar)||isEmpty(getNoID) ||isEmpty(getNama)||isEmpty(getTanggal)||isEmpty(getAgama)
//                ||isEmpty(getAlamat)
//                ||isEmpty(getGoldar)){
//            Toast.makeText(getContext(), "Data tidak boleh Kosong!", Toast.LENGTH_SHORT).show();
//        }else{
//            getReference.child("Admin").child("Data Owner").push()
//                    .setValue(new data_batikku(getNoID,getNama,getTanggal,getAgama,getAlamat,
//                            getGoldar,getKeadaan ,getGambar)).addOnSuccessListener(new OnSuccessListener<Void>() {
//                @Override
//                public void onSuccess(Void unused) {
//                    startActivity(new Intent(getApplicationContext(), ToastToko.class));
//                    Gambar.setImageResource(R.mipmap.ic_launcher);
//                    NoId.setText("");
//                    Nama.setText("");
//                    ouputTanggal.setText("");
//                    Alamat.setText("");
//                    progressBar.setVisibility(View.GONE);
//                }
//            });
//
//        }
//}
//package com.maza.batikku;
//
//import static android.text.TextUtils.isEmpty;
//
//import androidx.activity.result.ActivityResult;
//import androidx.activity.result.ActivityResultCallback;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.annotation.NonNull;
//import androidx.annotation.RequiresApi;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.DatePickerDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.text.SimpleDateFormat;
//
//public class InputOwner extends AppCompatActivity {
//    TextView ouputTanggal;
//    CheckBox mampu, tidak_mampu;
//    EditText NoId;
//    EditText Nama, Alamat;
//    Spinner Agama;
//    RadioButton radioButton;
//    RadioGroup goldar;
//    Button Simpan, btnLogout, inputTanggal;
//    ImageView Gambar;
//    ProgressBar progressBar;
//    DatePickerDialog datePickerDialog;
//    SimpleDateFormat dateFormatter;
//
//    String getNoID, getNama, getTanggal, getAlamat, getAgama, getGoldar, getGambar, getKeadaan, cbox;
//
//    //    Input Foto
//    ActivityResultLauncher<Intent> activityResultLauncher;
//    Uri fotoUrl;
//
//    //   Firebase Connection
//    DatabaseReference getReference;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_input_owner);
//        //        Get ID
//
//        progressBar = findViewById(R.id.progres_simpan);
//        progressBar.setVisibility(View.GONE);
//        NoId = findViewById(R.id.input_noid);
//        Nama = findViewById(R.id.input_nama);
//        Alamat = findViewById(R.id.input_alamat);
//        Agama = findViewById(R.id.input_agama);
//        goldar = findViewById(R.id.opsi_goldar);
//        Simpan = findViewById(R.id.button_simpan);
//        btnLogout = findViewById(R.id.btn_logout);
//        Gambar = findViewById(R.id.input_foto);
//        inputTanggal = findViewById(R.id.input_tanggal);
//        ouputTanggal = findViewById(R.id.output_tanggal);
//        mampu = findViewById(R.id.mampu);
//        tidak_mampu = findViewById(R.id.tidak_mampu);
//        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
//
//        mampu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                cbox = "Mampu";
//            }
//        });
//
//        tidak_mampu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                cbox = "Tidak Mampu";
//            }
//        });
//
//
//        //      Mendapatkan Data dari Firebase
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        getReference = database.getReference();
//
//
////        Ketika Tanggal Dipencet
//        inputTanggal.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onClick(View view) {
//                TampilTanggal();
//            }
//        });
//
//
////        Ketika Gambar Di Klik
//        Gambar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                activityResultLauncher.launch(intent);
//            }
//        });
//        //      Get Foto
//        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//            @Override
//            public void onActivityResult(ActivityResult result) {
//                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
//                    fotoUrl = result.getData().getData();
//                    Gambar.setImageURI(fotoUrl);
//                    Log.e("Data Lokasi Foto : ", String.valueOf(fotoUrl));
//
//                    Simpan.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            progressBar.setVisibility(View.VISIBLE);
//                            StorageReference filePath = storage.getReference().child("ImagePost").child(fotoUrl.getLastPathSegment());
//                            filePath.putFile(fotoUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                    Task<Uri> dowload = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Uri> task) {
//                                            getGambar = task.getResult().toString();
//                                            getNoID = NoId.getText().toString();
//                                            getNama = Nama.getText().toString();
//                                            getTanggal = ouputTanggal.getText().toString();
//                                            getAgama = Agama.getSelectedItem().toString();
//                                            getAlamat = Alamat.getText().toString();
//                                            getKeadaan = cbox;
//                                            int selectedRating = goldar.getCheckedRadioButtonId();
//                                            radioButton = goldar.findViewById(selectedRating);
//                                            getGoldar = (String) radioButton.getText();
//
//                                            if (checkInput() == true) {
//                                                progressBar.setVisibility(View.GONE);
//                                                Toast.makeText(getContext(), "Data tidak boleh Kosong!", Toast.LENGTH_SHORT).show();
//                                            } else {
//                                                checkUser();
//                                            }
//                                        }
//                                    });
//                                }
//                            });
//                        }
//                    });
//                }
//            }
//        });
//
//        Simpan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (checkInput() == true) {
//                    progressBar.setVisibility(View.GONE);
//                    Toast.makeText(getContext(), "Data tidak boleh Kosong!", Toast.LENGTH_SHORT).show();
//                } else {
//                    progressBar.setVisibility(View.VISIBLE);
//                    checkUser();
//                }
//            }
//        });
//
//
////
//    }
//
//    private void TampilTanggal() {
//        Tanggal tanggal = new Tanggal();
//        tanggal.show(getSupportFragmentManager(), "data");
//        tanggal.setOnDateClickListener(new Tanggal.onDateClickListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                String tahun = "" + datePicker.getYear();
//                String bulan = "" + (datePicker.getMonth() + 1);
//                String hari = "" + datePicker.getDayOfMonth();
//                String text = hari + " - " + bulan + " - " + tahun;
//                ouputTanggal.setText(text);
//            }
//        });
//    }
//
//    private Context getContext() {
//
//        return null;
//    }
//
//
//
//
//    //
//    private boolean checkInput() {
//        boolean emtpyInput = false;
//        if (isEmpty(getGambar)||isEmpty(getNoID) ||isEmpty(getNama) ||isEmpty(getTanggal)||isEmpty(getAgama)
//                ||isEmpty(getAlamat)
//                ||isEmpty(getGoldar)){
//            emtpyInput = true;
//        }
//        return emtpyInput;
//    }
//
//    private void checkUser() {
//        if (isEmpty(getGambar)||isEmpty(getNoID) ||isEmpty(getNama)||isEmpty(getTanggal)||isEmpty(getAgama)
//                ||isEmpty(getAlamat)
//                ||isEmpty(getGoldar)){
//            Toast.makeText(getContext(), "Data tidak boleh Kosong!", Toast.LENGTH_SHORT).show();
//        }else{
//            getReference.child("Admin").child("Data Owner").push()
//                    .setValue(new data_batikku(getNoID,getNama,getTanggal,getAgama,getAlamat,
//                            getGoldar,getKeadaan ,getGambar)).addOnSuccessListener(new OnSuccessListener<Void>() {
//                @Override
//                public void onSuccess(Void unused) {
//                    startActivity(new Intent(getApplicationContext(), ToastToko.class));
//                    Gambar.setImageResource(R.mipmap.ic_launcher);
//                    NoId.setText("");
//                    Nama.setText("");
//                    ouputTanggal.setText("");
//                    Alamat.setText("");
//                    progressBar.setVisibility(View.GONE);
//                }
//            });
//
//        }
//}
