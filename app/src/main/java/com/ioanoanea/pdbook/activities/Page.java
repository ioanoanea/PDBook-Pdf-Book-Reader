package com.ioanoanea.pdbook.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

import com.ioanoanea.pdbook.R;
import com.ioanoanea.pdbook.managers.Library;
import com.ioanoanea.pdbook.managers.ProgressManager;
import com.ioanoanea.pdbook.objects.PercentCalculator;
import com.ioanoanea.pdbook.pagers.BookPager;
import com.ioanoanea.pdbook.objects.Book;
import com.ioanoanea.pdbook.pagers.TurnPage;

import java.util.ArrayList;

public class Page extends AppCompatActivity {

   public ViewPager viewPager;
   public SeekBar seekBar;
   public static Activity activity;
   private Button exit;
   private Button options;
   private Library library;
   private ProgressManager progressManager;
   public Book book;
   private Intent intent;
   private int bookId;
   public BookPager bookPager;
   public float textSize = 16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);

        // set fullscreen mode
        final View overlay = findViewById(R.id.container);
        overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);

        // initializing views
        initializing();

        // set book
        bookPager = new BookPager(getSupportFragmentManager(), book.getTitle(), bookId, book.getNumberOfPages());
        viewPager.setPageTransformer(true, new TurnPage());
        viewPager.setAdapter(bookPager);

        // open book at current progress
        viewPager.setCurrentItem(progressManager.getProgress(bookId));

        // when exit button is pressed exit book
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Page.this.finish();
            }
        });

        // when options button is clicked open options menu
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open optons activity
                Intent intent = new Intent(Page.this, PageOptionsActivity.class).putExtra("book",book.getId());
                startActivity(intent);
            }
        });

        // set progress bar progress
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    PercentCalculator percentCalculator = new PercentCalculator();
                    viewPager.setCurrentItem(percentCalculator.getNumberPercent(progress,book.getNumberOfPages()));
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

        // set book progress
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // nothing to do
            }

            @Override
            public void onPageSelected(int position) {
                // set progress
                progressManager.setProgress(book.getId(),viewPager.getCurrentItem());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // nothing to do
            }
        });

    }


    /** initializing all activity data, layout views, objects **/
    private void initializing(){
        // initializing views
        viewPager = findViewById(R.id.pager);
        exit = findViewById(R.id.exit);
        options = findViewById(R.id.options);
        seekBar = findViewById(R.id.seekBar);

        // initializing objects
        library = new Library(this);
        progressManager = new ProgressManager(this);
        intent = getIntent();
        bookId = intent.getIntExtra("book",1);
        book = library.getBook(bookId);
        activity = this;
    }


    /** and new page bookmark **/
    public void addBookmark(int page){
        // initializing progress manager
        ProgressManager progressManager = new ProgressManager(this);
        // add bookmark
        progressManager.addBookmark(book.getId(),page);
    }


    /** check if current page has a bookmark and if current page
        has a bookmark  display bookmark at top-right of the screen **/
    public boolean checkBookmark(int page){
        // initializing progress manager
        ProgressManager progressManager = new ProgressManager(this);
        // get bookmark list
        ArrayList<Integer> bookmarks = progressManager.getBookmark(book.getId());
        // search specified page bookmark
        return searchItem(0, bookmarks.size()-1, bookmarks, page);
    }


    /** remove a page bookmark from existing bookmark list **/
    public void removeBookmark(int page){
        // initializing progress manager
        ProgressManager progressManager = new ProgressManager(this);
        // remove bookmark
        progressManager.removeBookMark(book.getId(),page);
    }


    /** binary search function -> verify if a value exists
        in a specified sorted list **/
    public boolean searchItem(int start, int end, ArrayList<Integer> list, int searchedValue) {
        // determine the middle of list
        int middle = (start+end)/2;
        /* if searching interval is null, searched value not found
        *  return false */
        if(start > end)
            return false;
        /* if searched value equals to middle, searched values found
        *  return true */
        else if(searchedValue == list.get(middle))
            return true;
        /* if searched value is smaller than middle
        *  continue searching only on list items smaller than middle */
        else if(searchedValue < list.get(middle))
            return searchItem(start,middle-1,list,searchedValue);
        /* else searched value is bigger than middle
        *  continue searching only on list items bigger than middle */
        else return searchItem(middle+1,end,list,searchedValue);
    }


    /** Minimize page dimension and show more options while page is minimized
     *  if page is minimized set page fullscreen and hide options **/
    public void showOptions(){

        // initializing a percent calculator
        PercentCalculator percentCalculator = new PercentCalculator();

        // measure page
        viewPager.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // determine page layout parameters
        ViewGroup.LayoutParams params = viewPager.getLayoutParams();

         // if page is minimized make page fullscreen else minimize page
         if(params.width != -1 && params.height != -1) {
            params.width = -1;
            params.height = -1;
            viewPager.setLayoutParams(params);
        } else {
            // determine percent of width in function of screen size
            int percentWidth;
            if (viewPager.getHeight() / viewPager.getWidth() > 2)
                percentWidth = 38;
            else if (viewPager.getHeight() / viewPager.getWidth() < 1)
                percentWidth = 60;
            else percentWidth = 40;

            // determine percent of height in function of screen size
            int percentHeight;
            if(viewPager.getHeight() < 960)
                percentHeight = 50;
            else percentHeight = 70;

            // apply the new width and height to page
            params.width = percentCalculator.getNumberPercent(percentWidth, viewPager.getHeight());
            params.height = percentCalculator.getNumberPercent(percentHeight, viewPager.getHeight());
            viewPager.setLayoutParams(params);
        }

         // set seek bar progress
         seekBar.setProgress(percentCalculator.getPercent(viewPager.getCurrentItem(),book.getNumberOfPages()));
    }


    /** select and open a specified page **/
    public void selectPage(int page){
        viewPager.setCurrentItem(page-1);
    }


    /** set page font size */
    public void setFontSize(float size){
        this.textSize = size;
    }

}
