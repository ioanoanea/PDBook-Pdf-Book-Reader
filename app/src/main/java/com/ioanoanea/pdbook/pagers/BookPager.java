package com.ioanoanea.pdbook.pagers;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ioanoanea.pdbook.fragments.TitlePage;
import com.ioanoanea.pdbook.fragments.FragmentPage;

public class BookPager extends FragmentPagerAdapter {

    private int nrPages;
    private int id;
    private String bookTitle;

    public BookPager(@NonNull FragmentManager fm, String bookTitle, int id, int nrPages) {
        super(fm);
        this.nrPages = nrPages;
        this.id = id;
        this.bookTitle = bookTitle;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        if(position == 0){
            Fragment fragment = new TitlePage();
            Bundle bundle = new Bundle();
            bundle.putString("text", bookTitle);
            bundle.putInt("progress",position);

            fragment.setArguments(bundle);

            return fragment;
        } else if(position == nrPages + 1){
            Fragment fragment = new TitlePage();
            Bundle bundle = new Bundle();
            bundle.putString("text","Congratulations!" + "\n" + "You have finished that book.");
            bundle.putInt("progress",position);

            fragment.setArguments(bundle);

            return fragment;
        } else {
            // set every book page
            Fragment fragment= new FragmentPage();
            Bundle bundle =  new Bundle();
            bundle.putInt("pageNumber",position);
            bundle.putInt("id", id);
            bundle.putInt("progress",position);
            fragment.setArguments(bundle);

            return fragment;
        }

    }

    @Override
    public int getCount() {
        return nrPages + 2;
    }


}
