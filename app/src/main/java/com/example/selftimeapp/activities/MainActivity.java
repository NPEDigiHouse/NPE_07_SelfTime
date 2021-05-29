package com.example.selftimeapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.selftimeapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
import static android.content.Intent.FLAG_ACTIVITY_FORWARD_RESULT;
import static android.content.Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvNama;
    private LinearLayout toNote, toMeet, toMateri, toClassroom;
    private FirebaseUser user;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // auth
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");

        toNote = findViewById(R.id.ll_note);
        toMeet = findViewById(R.id.ll_video_conf);
        toMateri = findViewById(R.id.ll_sumber);
        toClassroom = findViewById(R.id.ll_classroom);

        tvNama = findViewById(R.id.name);

        toNote.setOnClickListener(this);
        toMeet.setOnClickListener(this);
        toMateri.setOnClickListener(this);
        toClassroom.setOnClickListener(this);

        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("name").getValue(String.class);
                tvNama.setText(name);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_note:
                Intent toNote = new Intent(MainActivity.this, ListPlanActivity.class);
                startActivity(toNote);
                break;

                case R.id.ll_video_conf:
                Intent toVideo = new Intent(Intent.ACTION_VIEW, Uri.parse("https://meet.google.com/"));
                toVideo.setFlags(FLAG_ACTIVITY_FORWARD_RESULT &FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET&FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                startActivity(toVideo);
                break;

                case R.id.ll_classroom:
                    Intent toClassroom = new Intent(Intent.ACTION_VIEW, Uri.parse("https://classroom.google.com/"));
                    toClassroom.setFlags(FLAG_ACTIVITY_FORWARD_RESULT &FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET&FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                    startActivity(toClassroom);
                break;

                case R.id.ll_sumber:
                    Intent toYoutube = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query=android+studio+learn"));
                    toYoutube.setFlags(FLAG_ACTIVITY_FORWARD_RESULT &FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET&FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                    startActivity(toYoutube);
                break;
        }
    }
}