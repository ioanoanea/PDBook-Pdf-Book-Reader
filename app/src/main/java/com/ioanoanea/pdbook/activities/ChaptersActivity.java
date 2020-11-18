package com.ioanoanea.pdbook.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ioanoanea.pdbook.R;
import com.ioanoanea.pdbook.adapters.ChaptersAdapter;
import com.ioanoanea.pdbook.managers.Library;
import com.ioanoanea.pdbook.objects.Book;

import java.util.ArrayList;

public class ChaptersActivity extends AppCompatActivity {

    private Button exit;
    private RecyclerView recyclerView;
    private Intent intent;
    private Library library;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chapters);

        initializing();

        // exit when click exit button
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChaptersActivity.super.onBackPressed();
            }
        });

        setList();
    }


    /** initializing all activity data, layout views, objects **/
    private void initializing(){
        // initializing views
        exit = findViewById(R.id.exit);
        recyclerView = findViewById(R.id.list);

        // initializing objects
        intent = getIntent();
        library = new Library(this);
        book = library.getBook(intent.getIntExtra("book", 1));
    }


    /** set chapters list **/
    private void setList(){
        // get chapters list
        ArrayList<Integer> chapters = book.getChapters();
        // set list adapter
        ChaptersAdapter chaptersAdapter = new ChaptersAdapter(this, book.getId(), chapters);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chaptersAdapter);
    }


}
