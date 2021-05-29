package com.example.selftimeapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.selftimeapp.R;
import com.example.selftimeapp.adapters.MyNoteAdapter;
import com.example.selftimeapp.models.Note;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListPlanActivity extends AppCompatActivity implements MyNoteAdapter.OnItemClick {

    // recycler attr
    private RecyclerView rvMyNote;
    private MyNoteAdapter adapter;
    private List<Note> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_plan);

        // create adapter with empty list
        list = new ArrayList<>();
        adapter = new MyNoteAdapter(list, this);

        // set recyclerview
        rvMyNote = findViewById(R.id.rv_my_note);
        rvMyNote.setLayoutManager(new LinearLayoutManager(this));
        rvMyNote.setAdapter(adapter);

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
        DatabaseReference notesRef = FirebaseDatabase.getInstance().getReference()
                .child("notes");
        notesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int pos = 0;
                for (DataSnapshot data : snapshot.getChildren()) {
                    if (pos == position) {
                        // delete value
                        if (data.getKey() != null) {
                            notesRef.child(data.getKey()).removeValue();
                        }
                    }
                    pos++;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ListPlanActivity.this, "Terjadi kesalahan pada database.", Toast.LENGTH_SHORT).show();
            }
        });
        Toast.makeText(this, "Note berhasil dihapus", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(ListPlanActivity.this, ListPlanActivity.class));
        finish();
    }
}