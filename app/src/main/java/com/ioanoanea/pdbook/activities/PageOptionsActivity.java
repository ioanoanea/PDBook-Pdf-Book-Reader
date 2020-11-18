package com.ioanoanea.pdbook.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ioanoanea.pdbook.R;
import com.ioanoanea.pdbook.managers.Library;
import com.ioanoanea.pdbook.objects.Book;

import java.util.ArrayList;

public class PageOptionsActivity extends AppCompatActivity {

    private ConstraintLayout window;
    private ConstraintLayout chapters;
    private ConstraintLayout bookmarks;
    private ConstraintLayout editFont;
    private ConstraintLayout search;
    private Intent intent;
    private Library library;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_options);

        // change activity transition animation
        overridePendingTransition(R.anim.fade_id, R.anim.fade_out);

        // hide navigation
        final View overlay = findViewById(R.id.window);
        overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);

        initializing();
        resizeWindow();

        chapters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PageOptionsActivity.this, ChaptersActivity.class).putExtra("book",book.getId());
                startActivity(intent);
                PageOptionsActivity.this.finish();
            }
        });

        bookmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PageOptionsActivity.this, BookmarksActivity.class).putExtra("book", book.getId());
                startActivity(intent);
                PageOptionsActivity.this.finish();
            }
        });

        editFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PageOptionsActivity.this, FontSizeActivity.class).putExtra("book", book.getId());
                startActivity(intent);
                PageOptionsActivity.this.finish();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PageOptionsActivity.this, SearchActivity.class).putExtra("book", book.getId());
                startActivity(intent);
                PageOptionsActivity.this.finish();
            }
        });

    }

    /** initializing all activity data, layout views, objects **/
    private void initializing(){
        // initializing views
        window = findViewById(R.id.window);
        chapters = findViewById(R.id.chapters);
        bookmarks = findViewById(R.id.bookmarks);
        editFont = findViewById(R.id.font_size);
        search = findViewById(R.id.search);

        // initializing objects
        intent = getIntent();
        library = new Library(this);
        book = library.getBook(intent.getIntExtra("book",1));
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


    /** show chapters list **/
    private void getChapters(){
        ArrayList<Integer> chapters = book.getChapters();

        Toast.makeText(this, String.valueOf(chapters.get(0) + " " + chapters.get(1) + " " + chapters.get(2) ), Toast.LENGTH_SHORT).show();
    }
}
