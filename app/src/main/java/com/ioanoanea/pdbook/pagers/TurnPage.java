package com.ioanoanea.pdbook.pagers;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class TurnPage implements ViewPager.PageTransformer {

    @Override
    public void transformPage(@NonNull View page, float position) {
        // get page width
        int width = page.getWidth();

        if(position < -1) {
            // page if off-screen
            page.setAlpha(0f);
        }

        else if(position <= 0) {
            // fade the page in
            page.setAlpha(1f);
            page.setTranslationX(0);
            page.setTranslationZ(0f);
            page.setScaleX(1);
            page.setScaleY(1);
        }

        else if(position <= 1) {
            // use page transition when moving page
            page.setAlpha(1f - position);
            page.setTranslationX(width * - position);
            page.setTranslationZ(-20f);

        } else {
            // page is off-screen
            page.setAlpha(0);
        }
    }
}
