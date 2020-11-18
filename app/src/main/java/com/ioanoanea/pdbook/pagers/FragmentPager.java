package com.ioanoanea.pdbook.pagers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ioanoanea.pdbook.fragments.FinishedFragment;
import com.ioanoanea.pdbook.fragments.InProgressFragment;
import com.ioanoanea.pdbook.fragments.MyBooksFragment;

public class FragmentPager extends FragmentPagerAdapter {

    public FragmentPager(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                MyBooksFragment myBooksFragment = new MyBooksFragment();
                return myBooksFragment;
            case 1:
                InProgressFragment inProgressFragment = new InProgressFragment();
                return inProgressFragment;
            case 2:
                FinishedFragment finishedFragment = new FinishedFragment();
                return  finishedFragment;
             default:
                 return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "My books";
            case 1:
                return "In progress";
            case 2:
                return  "Finished";
            default:
                return null;
        }
    }


}
