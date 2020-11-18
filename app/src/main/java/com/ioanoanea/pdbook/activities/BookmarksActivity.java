package com.ioanoanea.pdbook.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ioanoanea.pdbook.R;
import com.ioanoanea.pdbook.adapters.BookmarksAdapter;
import com.ioanoanea.pdbook.managers.Library;
import com.ioanoanea.pdbook.managers.ProgressManager;
import com.ioanoanea.pdbook.objects.Book;

import java.util.ArrayList;

public class BookmarksActivity extends AppCompatActivity {

    private Button exit;
    private RecyclerView recyclerView;
    private BookmarksAdapter bookmarksAdapter;
    private Intent intent;
    private Library library;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmarks);

        initializing();
        setList();

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookmarksActivity.super.onBackPressed();
            }
        });
    }


    /** initializing all activity data, layout views, objects **/
    private void initializing(){
        // initializing views
        exit = findViewById(R.id.exit);
        recyclerView = findViewById(R.id.list);

        // initializing objects
        intent = getIntent();
        library = new Library(this);
        book = library.getBook(intent.getIntExtra("book",1));
    }

    /** set bookmarks list **/
    private void setList(){
        // get bookmarks list
        ProgressManager progressManager = new ProgressManager(this);
        ArrayList<Integer> bookmarks = progressManager.getBookmark(book.getId());

        // set bookmarks list adapter
        bookmarksAdapter = new BookmarksAdapter(this,bookmarks,book);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(bookmarksAdapter);
    }


}
