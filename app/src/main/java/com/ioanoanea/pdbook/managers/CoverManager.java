package com.ioanoanea.pdbook.managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.ioanoanea.pdbook.objects.Cover;

import java.util.Random;

public class CoverManager {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public CoverManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("COVER",0);
        editor = sharedPreferences.edit();
    }

    public Cover getRandomCover(int nr){

        // get a random number
        Random random = new Random();
        int coverId = random.nextInt(4);

        // if selected cover number was selected before, get a different cover
        if(sharedPreferences.getInt("last_id",0) == coverId){
            // get a new different random cover
            return getRandomCover(nr);
        }
        /* verify if a cover was not selected for more than three times
        *  and if true, select that cover */
        else if(sharedPreferences.getInt("0",0) > 3){
            // increment unselected covers
            incrementCoverId(1);
            incrementCoverId(2);
            incrementCoverId(3);
            // return selected cover
            return setCover(0);
        } else if(sharedPreferences.getInt("1",0) > 3){
            // increment unselected covers
            incrementCoverId(0);
            incrementCoverId(2);
            incrementCoverId(3);
            // return selected cover
            return setCover(1);
        } else if(sharedPreferences.getInt("2",0) > 3){
            // increment unselected covers
            incrementCoverId(0);
            incrementCoverId(1);
            incrementCoverId(3);
            // return selected cover
            return setCover(2);
        } else if(sharedPreferences.getInt("3",0) > 3){
            // increment unselected covers
            incrementCoverId(0);
            incrementCoverId(1);
            incrementCoverId(2);
            // return selected cover
            return setCover(3);
        }
        /* if no cover needs to be selected,
        *  than return the random generated cover*/
        else if(coverId == 0){
            // increment unselected covers
            incrementCoverId(1);
            incrementCoverId(2);
            incrementCoverId(3);
            // return selected cover
            return setCover(0);
        } else if(coverId == 1){
            // increment unselected covers
            incrementCoverId(0);
            incrementCoverId(2);
            incrementCoverId(3);
            // return selected cover
            return setCover(1);
        } else if(coverId == 2){
            // increment unselected covers
            incrementCoverId(0);
            incrementCoverId(1);
            incrementCoverId(3);
            // return selected cover
            return setCover(2);
        } else {
            // increment unselected covers
            incrementCoverId(0);
            incrementCoverId(1);
            incrementCoverId(2);
            // return selected cover
            return setCover(3);
        }

    }

    private Cover setCover(int nr){
        // mark the type of cover selected
        editor.putInt(String.valueOf(nr), 0);
        editor.putInt("last_id", nr);
        editor.apply();

        // define new cover
        Cover cover = new Cover();

        // setting cover parameters
        cover.setCoverType(cover.DEFAULT);
        cover.setBackgroundResource(cover.backgroundColor.get(nr));
        cover.setTitleColor(cover.titleTextColor.get(nr));
        cover.setAuthorColor(cover.authorTextColor.get(nr));

        return cover;
    }

    private void incrementCoverId(int id){
        // increment unselected cover
        editor.putInt(String.valueOf(id),sharedPreferences.getInt(String.valueOf(id),0)+1);
    }

    public void initializing(int nr){
        // initializing selected cover id saved with 0
        for(int i = 0; i < nr; i++){
            editor.putInt(String.valueOf(i),0);
            editor.apply();
        }
    }

}
