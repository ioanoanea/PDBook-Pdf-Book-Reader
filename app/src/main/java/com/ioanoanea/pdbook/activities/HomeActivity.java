package com.ioanoanea.pdbook.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.ioanoanea.pdbook.R;
import com.ioanoanea.pdbook.pagers.FragmentPager;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity {

    public LinearLayout window;
    private ViewPager viewPager;
    private FragmentPager fragmentPager;
    private TabLayout tabs;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // initializing data
        initializing();

        // set container pages
        fragmentPager = new FragmentPager(getSupportFragmentManager());
        viewPager.setAdapter(fragmentPager);
        viewPager.setOffscreenPageLimit(3);

        // set tab navigation bar
        tabs.setupWithViewPager(viewPager);

        /** If application don't don't have permission to access current location, check permission for location
         *  location is used to generate custom ads */
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},1);
        }

        // initialize ads
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

    }

    /** initializing all activity data, layout views, objects **/
    private void initializing(){
        // initializing views
        viewPager = findViewById(R.id.pager);
        tabs = findViewById(R.id.main_tabs);
        window = findViewById(R.id.window);
    }




}
