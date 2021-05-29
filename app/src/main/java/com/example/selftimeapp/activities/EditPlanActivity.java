package com.example.selftimeapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.selftimeapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditPlanActivity extends AppCompatActivity implements View.OnClickListener {

    // extras
    public static final String EXTRA_POSITION = "extra_position";
    public static final String EXTRA_JUDUL = "extra_judul";
    public static final String EXTRA_KETERANGAN = "extra_keterangan";

    // widgets
    private ImageView btnBack, btnSave;
    private EditText etJudul, etKeterangan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_plan);

        // initialize widgets
        btnBack = findViewById(R.id.ib_back_update);
        btnSave = findViewById(R.id.ib_save_update);
        etJudul = findViewById(R.id.et_judul_update);
        etKeterangan = findViewById(R.id.et_keterangan_update);

        // set data
        setData();

        // if button clicked
        btnBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back_update:
                finish();
                break;
            case R.id.ib_save_update:
                saveData();
                break;
        }
    }

    private void setData() {
        String judul = getIntent().getStringExtra(EXTRA_JUDUL);
        etJudul.setText(judul);
        String keterangan = getIntent().getStringExtra(EXTRA_KETERANGAN);
        etKeterangan.setText(keterangan);
    }

    private void saveData() {
        String newJudul = etJudul.getText().toString();
        String newKeterangan = etKeterangan.getText().toString();
        DatabaseReference noteRef = FirebaseDatabase.getInstance().getReference()
                .child("notes");
        noteRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int position = getIntent().getIntExtra(EXTRA_POSITION, 0);
                int temp = 0;
                for (DataSnapshot data : snapshot.getChildren()) {
                    if (position == temp) {
                        String key = data.getKey();
                        if (key != null) {
                            noteRef.child(key).child("judul").setValue(newJudul);
                            noteRef.child(key).child("keterangan").setValue(newKeterangan);
                        }
                    }
                    temp++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditPlanActivity.this, "Terjadi kesalahan pada database.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}