package com.example.selftimeapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.selftimeapp.R;
import com.example.selftimeapp.adapters.MyNoteAdapter;
import com.example.selftimeapp.models.Note;

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

    }

    @Override
    public void btnDeleteClicked(int position) {

    }
}