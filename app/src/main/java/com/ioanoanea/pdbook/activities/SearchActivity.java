package com.ioanoanea.pdbook.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.ioanoanea.pdbook.R;
import com.ioanoanea.pdbook.adapters.SearchAdapter;
import com.ioanoanea.pdbook.managers.Library;
import com.ioanoanea.pdbook.managers.SearchManager;
import com.ioanoanea.pdbook.objects.Book;
import com.ioanoanea.pdbook.objects.SearchItem;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {


    private SearchView searchView;
    private RecyclerView recyclerView;

    private Library library;
    private Book book;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        initializing();
        search("");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                    search(newText);
                return false;
            }
        });
    }


    /** initializing all activity data, layout views, objects */
    private void initializing(){
        // initializing views
        searchView = findViewById(R.id.search_bar);
        recyclerView = findViewById(R.id.list);

        // initializing objects
        library = new Library(this);
        intent = getIntent();
        book = library.getBook(intent.getIntExtra("book", 1));
    }


    public void search(String searchedValue){
        SearchManager searchManager = new SearchManager();
        ArrayList<SearchItem> searchItems;
        if(!searchedValue.equals(""))
            searchItems = searchManager.searchInBook(searchedValue,book);
        else searchItems = new ArrayList<>();
        SearchAdapter searchAdapter = new SearchAdapter(this,searchedValue,searchItems);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(searchAdapter);
    }
}
