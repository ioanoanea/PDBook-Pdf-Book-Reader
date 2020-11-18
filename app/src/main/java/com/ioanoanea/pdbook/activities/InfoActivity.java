package com.ioanoanea.pdbook.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ioanoanea.pdbook.R;
import com.ioanoanea.pdbook.managers.Library;
import com.ioanoanea.pdbook.managers.ProgressManager;
import com.ioanoanea.pdbook.objects.Book;

public class InfoActivity extends AppCompatActivity {

    private LinearLayout window;
    private TextView title;
    private TextView author;
    private TextView pages;
    private TextView chapters;
    private TextView bookmarks;

    private Intent intent;
    private Library library;
    private Book book;
    private ProgressManager progressManager;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        // change activity transition animation
        overridePendingTransition(R.anim.fade_id, R.anim.fade_out);

        initializing();
        resizeWindow();

        // set data
        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        pages.setText(String.valueOf(book.getNumberOfPages()));
        chapters.setText(String.valueOf(book.getChapters().size()));
        int bookmarkNumber = progressManager.getBookmark(book.getId()).size();
        if (bookmarkNumber == 0)
            bookmarks.setText("No bookmarks");
        else bookmarks.setText(String.valueOf(bookmarkNumber));
    }

    /** initializing all activity data, layout views, objects **/
    private void initializing(){
        // initializing views
        window = findViewById(R.id.window);
        title = findViewById(R.id.title);
        author = findViewById(R.id.author);
        pages = findViewById(R.id.pages);
        chapters = findViewById(R.id.chapters);
        bookmarks = findViewById(R.id.bookmarks);

        // initializing objects
        intent = getIntent();
        library = new Library(this);
        book = library.getBook(intent.getIntExtra("book", 1));
        progressManager = new ProgressManager(this);
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

                getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
                getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
            }
        });
    }
}
