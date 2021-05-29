package com.example.selftimeapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.selftimeapp.R;
import com.example.selftimeapp.adapters.MyNoteAdapter;
import com.example.selftimeapp.models.Note;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListPlanActivity extends AppCompatActivity implements MyNoteAdapter.OnItemClick, View.OnClickListener {

    // recycler attr
    private RecyclerView rvMyNote;
    private MyNoteAdapter adapter;
    private List<Note> list;

    // widgets
    private FloatingActionButton btnAdd;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_plan);

        // initialize widgets
        btnAdd = findViewById(R.id.btn_add);
        btnBack = findViewById(R.id.ib_back);

        // create adapter with empty list
        list = new ArrayList<>();
        adapter = new MyNoteAdapter(list, this);

        // set recyclerview
        rvMyNote = findViewById(R.id.rv_my_note);
        rvMyNote.setLayoutManager(new LinearLayoutManager(this));
        rvMyNote.setAdapter(adapter);

        // update list
        updateList();

        // if button clicked
        btnAdd.setOnClickListener(this);
        btnBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                Intent goToAddPlan = new Intent(this, AddPlanActivity.class);
                startActivity(goToAddPlan);
                break;
            case R.id.ib_back:
                finish();
                break;
        }
    }

    private void updateList() {
        // get user plans reference
        DatabaseReference userPlansRef = FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("plans");
        userPlansRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    // update the adapter's list
                    Note note = new Note();
                    note.setJudul(data.child("judul").getValue().toString());
                    note.setKeterangan(data.child("keterangan").getValue().toString());
                    list.add(note);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ListPlanActivity.this, "Terjadi kesalahan pada database.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void btnEditClicked(int position) {
        Intent goToEdit = new Intent(ListPlanActivity.this, EditPlanActivity.class);
        goToEdit.putExtra(EditPlanActivity.EXTRA_JUDUL, list.get(position).getJudul());
        goToEdit.putExtra(EditPlanActivity.EXTRA_KETERANGAN, list.get(position).getKeterangan());
        startActivity(goToEdit);
    }

    @Override
    public void btnDeleteClicked(int position) {
        // get note reference
        DatabaseReference userPlansRef = FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("plans");

        // delete plan
        userPlansRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int temp = 0;
                for (DataSnapshot data : snapshot.getChildren()) {
                    if (position == temp) {
                        String key = data.getKey();
                        if (key != null) {
                            userPlansRef.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(ListPlanActivity.this, "Tim berhasil dihapus", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ListPlanActivity.this, ListPlanActivity.class));
                                    finish();
                                }
                            });
                        }
                    }
                    temp++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}