package com.CNSI.OctopusPlayer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity {

    Intent intent;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        intent = new Intent(this, MainActivity.class);


        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable()  {
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 500);


    }
}
