package com.ioanoanea.pdbook.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ioanoanea.pdbook.R;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        /** display this splash screen 1 second  */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // start Home Activity
                Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
                SplashScreen.this.startActivity(intent);
                SplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

        // initializing mobile ads
        //MobileAds.initialize(this, "ca-app-pub-3650822610871452~1109312518");
    }
}
