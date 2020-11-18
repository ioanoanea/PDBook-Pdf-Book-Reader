package com.ioanoanea.pdbook.objects;

import android.graphics.Bitmap;

import com.ioanoanea.pdbook.R;

import java.util.ArrayList;

public class Cover {

    private int coverType;
    private int titleColor;
    private int authorColor;
    private int backgroundResource;
    private Bitmap backgroundBitmap;
    public int CUSTOM = 2;
    public int DEFAULT = 1;
    public ArrayList<Integer> backgroundColor = new ArrayList<>();
    public ArrayList<Integer> titleTextColor = new ArrayList<>();
    public ArrayList<Integer> authorTextColor = new ArrayList<>();

    public Cover(){
        // set default cover resources
        // background
        backgroundColor.add(R.color.coverOne);
        backgroundColor.add(R.color.coverTwo);
        backgroundColor.add(R.color.coverThree);
        backgroundColor.add(R.color.coverFour);

        // title color
        titleTextColor.add(R.color.titleOne);
        titleTextColor.add(R.color.titleOne);
        titleTextColor.add(R.color.titleTwo);
        titleTextColor.add(R.color.titleOne);

        // author color
        authorTextColor.add(R.color.author);
        authorTextColor.add(R.color.author);
        authorTextColor.add(R.color.author);
        authorTextColor.add(R.color.author);
    }

    public void setCoverType(int coverType) {
        this.coverType = coverType;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    public void setAuthorColor(int authorColor) {
        this.authorColor = authorColor;
    }

    public void setBackgroundResource(int backgroundResource) {
        this.backgroundResource = backgroundResource;
    }

    public void setBackgroundBitmap(Bitmap backgroundBitmap) {
        this.backgroundBitmap = backgroundBitmap;
    }


    public int getCoverType() {
        return coverType;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public int getAuthorColor() {
        return authorColor;
    }

    public int getBackgroundResource() {
        return backgroundResource;
    }

    public Bitmap getBackgroundBitmap() {
        return backgroundBitmap;
    }


}
