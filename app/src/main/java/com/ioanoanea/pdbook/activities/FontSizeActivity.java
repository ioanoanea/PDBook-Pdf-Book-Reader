package com.ioanoanea.pdbook.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ioanoanea.pdbook.R;

public class FontSizeActivity extends AppCompatActivity {

    private TextView text;
    private SeekBar sizeBar;
    private TextView ok;
    private ConstraintLayout window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.font_size);

        // change activity transition animation
        overridePendingTransition(R.anim.fade_id, R.anim.fade_out);

        // hide navigation
        final View overlay = findViewById(R.id.window);
        overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);

        initializing();
        resizeWindow();

        // select text size
        text.setTextSize(((Page) Page.activity).textSize);
        sizeBar.setProgress((int) (((Page) Page.activity).textSize * 2));

        // change text size on sizeBare progress change
        sizeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    // set font size
                    float size = progress / 2;
                    text.setTextSize(size);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // nothing to do yet
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // nothing to do yet
            }
        });

        // change page font size when user clicks ok
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // determine book current page
                int page = ((Page) Page.activity).viewPager.getCurrentItem();
                // set font size
                ((Page) Page.activity).setFontSize(sizeBar.getProgress() / 2);
                // reload book at current page with new font size
                ((Page) Page.activity).viewPager.setAdapter(((Page) Page.activity).bookPager);
                ((Page) Page.activity).viewPager.setCurrentItem(page);
                // finish that activity
                FontSizeActivity.this.finish();
            }
        });
    }


    /** initializing all activity data, layout views, objects **/
    private void initializing(){
        // initializing views
        text = findViewById(R.id.text);
        sizeBar = findViewById(R.id.seekBar);
        ok = findViewById(R.id.ok);
        window = findViewById(R.id.window);
    }


    /** set window width and height **/
    private void resizeWindow(){

        window.post(new Runnable() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void run() {
                int width = window.getWidth();
                int height = window.getHeight();
                width = width - width / 8;

                getWindow().setLayout(width, height);
                getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
            }
        });
    }
}
