package com.ioanoanea.pdbook.objects;

import android.content.Context;

import com.ioanoanea.pdbook.activities.HomeActivity;

public class DisplayInfo {

    private Context context;
    public DisplayInfo(Context context){
        this.context = context;
    }

    public int getWidth() {
        return ((HomeActivity) context).window.getWidth();
    }

    public int getHeight(){
        return ((HomeActivity) context).window.getHeight();
    }
}
