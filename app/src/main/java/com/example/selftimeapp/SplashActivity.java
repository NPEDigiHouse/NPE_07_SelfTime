package com.example.selftimeapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        // set timer for 2 seconds
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
//                String userId = Util.getUserIdLocal(SplashScreen.this);
//                Intent intent;
//                if (userId.isEmpty()) {
//                    intent = new Intent(SplashActivity.this, LoginActivity.class);
//                } else {
//                    intent = new Intent(SplashActivity.this, MainActivity.class);
//                }
               Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                // start activity
                startActivity(intent);
                finish();
            }},2000);
    }
}
