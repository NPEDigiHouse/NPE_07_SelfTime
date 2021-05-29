package com.example.selftimeapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.example.selftimeapp.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
               Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                // start activity
                startActivity(intent);
                finish();
            }},2000);
    }
}
