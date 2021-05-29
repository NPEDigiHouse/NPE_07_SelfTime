package com.example.selftimeapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.selftimeapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddPlanActivity extends AppCompatActivity implements View.OnClickListener {

    // widgets
    private EditText etJudul, etKeterangan;
    private ImageView btnBack, btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);

        // initialize widgets
        btnBack = findViewById(R.id.ib_back_add);
        btnSave = findViewById(R.id.ib_save_add);
        etJudul = findViewById(R.id.et_judul_add);
        etKeterangan = findViewById(R.id.et_keterangan_add);

        btnBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back_add:
                finish();
                break;
            case R.id.ib_save_add:
                saveData();
                break;
        }
    }

    private void saveData() {
        Map<String, Object> noteMap = new HashMap<String, Object>() {{
            put("judul", etJudul.getText().toString());
            put("keterangan", etKeterangan.getText().toString());
        }};

        // add note data to firebase
        FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("plans")
                .push()
                .setValue(noteMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AddPlanActivity.this, "Plan berhasil ditambahkan.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}