package com.innova.ticketsapp.actividades;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.innova.ticketsapp.R;

public class SplashScreenActivity extends AppCompatActivity {
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_splash_screen);
        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                SplashScreenActivity.this.startActivity(new Intent((Context)SplashScreenActivity.this, com.innova.ticketsapp.MainActivity.class));
                SplashScreenActivity.this.finish();
            }
        },1500L);
    }
}