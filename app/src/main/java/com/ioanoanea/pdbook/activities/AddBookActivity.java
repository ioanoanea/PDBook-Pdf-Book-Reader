package com.ioanoanea.pdbook.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.FileUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ioanoanea.pdbook.R;
import com.ioanoanea.pdbook.managers.CoverManager;
import com.ioanoanea.pdbook.managers.Library;
import com.ioanoanea.pdbook.managers.ProgressManager;
import com.ioanoanea.pdbook.objects.Book;
import com.ioanoanea.pdbook.objects.Bookmark;
import com.ioanoanea.pdbook.objects.FileInfo;
import com.ioanoanea.pdbook.objects.FilePath;
import com.ioanoanea.pdbook.pdf.PdfEditor;

import java.io.File;
import java.util.Arrays;

public class AddBookActivity extends AppCompatActivity {

    private ConstraintLayout window;
    private EditText title, author, path;
    private Button importBook, bookFile;
    private Library library;
    private ProgressManager progressManager;
    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_book);

        initializing();
        resizeWindow();

        // set action for 'import book' button
        importBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if(validateData(path.getText().toString(),title.getText().toString(),author.getText().toString())){
                //    addBook(fileUri,title.getText().toString(),author.getText().toString());
                //}

                if(fileUri != null && validateData(title.getText().toString(), author.getText().toString()))
                    addBook(fileUri,title.getText().toString(),author.getText().toString());
                else Toast.makeText(AddBookActivity.this, "file not found!", Toast.LENGTH_SHORT).show();

            }
        });



            chooseFile(1);


        /*// set action for 'choose pdf file button'
        bookFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if external storage permission is granted than choose book file, else check permission
                if(ActivityCompat.checkSelfPermission(AddBookActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(AddBookActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                    chooseFile(1);
                else {
                    ActivityCompat.requestPermissions(AddBookActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                    ActivityCompat.requestPermissions(AddBookActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                }
            }
        });*/

    }


    /** initializing all data, layout views, objects **/
    private void initializing(){
        // initializing views
        window = findViewById(R.id.window);
        //path = findViewById(R.id.path);
        title = findViewById(R.id.name);
        author = findViewById(R.id.author);
        importBook = findViewById(R.id.import_book);
        //bookFile = findViewById(R.id.file);

        // initializing objects
        library = new Library(this);
        progressManager = new ProgressManager(this);
    }

    private void resizeWindow() {
        window.post(new Runnable() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void run() {
                int width = window.getWidth();
                int height = window.getHeight();

                width = width - width / 8;
                getWindow().setLayout(width,height);
                getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
            }
        });
    }

    /** select pdf file **/
    public void chooseFile(int code){
        Intent intent = new Intent();

        try {
            // set possible types for file
            intent.setType("application/pdf");

            // get file
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select picture"), code);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            // initializing a progress dialog to show while choosing file
            final ProgressDialog progressDialog = new ProgressDialog(this);


            @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Uri> task = new AsyncTask<Void, Void, Uri>() {
                @Override
                protected Uri doInBackground(Void... voids) {
                    try {
                        // get file uri
                        return data.getData();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                @Override
                protected void onPreExecute() {
                    progressDialog.setMessage("getting file ...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }


                @Override
                protected void onPostExecute(Uri uri) {
                    // select file uri
                    fileUri = uri;

                    // dismiss progress dialog when file found
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                }
            };

            task.execute();

            /*@SuppressLint("StaticFieldLeak") AsyncTask<Void, Void,  String> task = new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... voids) {
                    // set path for new book
                    try {
                        FilePath filePath = new FilePath();
                        return filePath.getPath(AddBookActivity.this,uri);
                        //path.setText(filePath.getPath(AddBookActivity.this,uri));
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                @Override
                protected void onPreExecute() {
                    // showing progress dialog while book is being uploaded
                    progressDialog.setMessage("Getting file ...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }

                @Override
                protected void onPostExecute(String s) {
                    fileUri = uri;
                    // get file path
                    if(s != null)
                        path.setText(s);
                    else Toast.makeText(AddBookActivity.this, "File not found!", Toast.LENGTH_SHORT).show();
                    // dismiss progress dialog when finished uploading
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                }
            };

            task.execute();*/

        }
    }


    /** Validate book data
     *  verify if path or title or author is null
     *  verify if book file exists */
    private Boolean validateData(String title, String author){

        //File file = new File(path);
        //String pathExtension = String.valueOf(path.charAt(path.length() - 3)) + String.valueOf(path.charAt(path.length() - 2)) + String.valueOf(path.charAt(path.length() - 1));

        /*// if path is null, notify user and return false
        if (path.equals("")){
            Toast.makeText(this, "Book file cannot be null", Toast.LENGTH_SHORT).show();
            return false;
        }
        // if file is not pdf, notify user and return false
        else if (!pathExtension.equals("pdf") && !pathExtension.equals("PDF")){
            Toast.makeText(this, "Incompatible file", Toast.LENGTH_SHORT).show();
            return false;
        }
        // if file does not exists, notify user and return false
        else if (!file.exists()){
            Toast.makeText(this, "File not found!", Toast.LENGTH_SHORT).show();
            return false;
        }*/
        // if title is null, notify user and return false
        if (title.equals("")) {
            Toast.makeText(this, "Title cannot be null!", Toast.LENGTH_SHORT).show();
            return false;
        }
        // if author name is null, notify user and return null
        else if(author.equals("")){
            Toast.makeText(this, "Author cannot be null!", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }


    /** get a pdf file from a path, convert the specified file to application book format,
    *  add the book to application library **/
    private void addBook(final Uri uri, final String title, final String author){

        // verify if external storage permission is already granted
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
              && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){

            // initializing progress dialog
            final ProgressDialog progressDialog = new ProgressDialog(this);

            @SuppressLint("StaticFieldLeak") AsyncTask<Void,Void,Void> task = new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {

                    try {

                        // initializing new pdf editor
                        PdfEditor pdfEditor = new PdfEditor(AddBookActivity.this);

                        // initializing new virtual book and set content extracted text from pdf document
                        Book book = new Book(pdfEditor.getTextFrom(uri));

                        // set book information
                        book.setId(progressManager.getNumberOfBooks());
                        book.setTitle(title);
                        book.setAuthor(author);

                        // set a random cover from standard application covers
                        CoverManager coverManager = new CoverManager(AddBookActivity.this);
                        book.setCover(coverManager.getRandomCover(4));

                        // add book to library
                        library.addNewBook(book);

                        // set book progress
                        progressManager.addProgress(0);
                        progressManager.createBookMak(new Bookmark());

                    } catch (Exception e) {
                        //Toast.makeText(AddBookActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    return null;
                }

                @Override
                protected void onPreExecute() {
                    // showing progress dialog while book is being uploaded
                    progressDialog.setMessage("importing book...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    // dismiss progress dialog when finished uploading
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    Toast.makeText(AddBookActivity.this, "Successful added!", Toast.LENGTH_SHORT).show();
                    AddBookActivity.super.onBackPressed();
                }
            };

            task.execute();

        } else {
            // if permission is not granted check permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            // initializing standard covers
            CoverManager coverManager = new CoverManager(this);
            coverManager.initializing(4);
        }

    }



}
