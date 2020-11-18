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

public class BookOptionsActivity extends AppCompatActivity {

    private ConstraintLayout window;
    private ConstraintLayout delete;
    private ConstraintLayout info;
    private Intent intent;
    private Library library;
    private int bookId;
    private int bookPosition;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_options);

        // change activity transition animation
        overridePendingTransition(R.anim.fade_id, R.anim.fade_out);

        initializing();
        resizeWindow();

        // when user clicks on delete button remove book and close this activity
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // delete book
                library.removeBook(bookId);
                // close this activity
                BookOptionsActivity.this.finish();
                // notify that book was is deleted
                Toast.makeText(BookOptionsActivity.this, "Deleted book", Toast.LENGTH_SHORT).show();
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BookOptionsActivity.this, InfoActivity.class).putExtra("book",bookId));
                BookOptionsActivity.this.finish();
            }
        });

    }


    /** initializing all activity data, layout views, objects **/
    private void initializing(){
        // initializing views
        window = findViewById(R.id.window);
        delete = findViewById(R.id.delete);
        info = findViewById(R.id.info);

        //initializing objects;
        intent = getIntent();
        library = new Library(this);
        bookId = intent.getIntExtra("book",1);
        bookPosition = intent.getIntExtra("position",1);
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

}
