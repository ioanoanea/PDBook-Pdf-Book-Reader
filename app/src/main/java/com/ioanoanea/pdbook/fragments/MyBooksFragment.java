package com.ioanoanea.pdbook.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ioanoanea.pdbook.R;
import com.ioanoanea.pdbook.adapters.MybooksAdapter;
import com.ioanoanea.pdbook.activities.AddBookActivity;
import com.ioanoanea.pdbook.managers.Library;
import com.ioanoanea.pdbook.objects.BookListItem;

import java.util.ArrayList;
import java.util.Objects;

public class MyBooksFragment extends Fragment {

    private Button read;
    private Library library;
    private RecyclerView recyclerView;
    private MybooksAdapter customAdapter;
    private SwipeRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_books_fragment,container,false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // initializing all onjects
        initializing(view);

        // show books
        showBooks();

        // refresh displayed book list on refresh
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        // update book list
                        updateMyBooksList();
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        // if there is no books change recyclerview layout manager
                        if(customAdapter.getItemViewType(0) == 2)
                            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
                        else recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
                        // display new books data
                        recyclerView.setAdapter(customAdapter);
                        // stop refreshing
                        refreshLayout.setRefreshing(false);
                    }
                };

                task.execute();

            }
        });


        //set action for 'read' button
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 // if external storage permission is granted than choose book file, else check permission
                if(ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                  && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                    //open 'add book' pop up window
                    startActivity(new Intent(getActivity(), AddBookActivity.class));
                else {
                    ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                }
            }
        });
    }

    /** initialize all data */
    private void initializing(View view){
        // initializing views
        read = view.findViewById(R.id.read);
        recyclerView = view.findViewById(R.id.list);
        refreshLayout = view.findViewById(R.id.refresh);

        // initializing objects
        library = new Library(getContext());
    }

    /** get books data from library book list and display books
     *  display all books */
    private void showBooks(){

        try {
            /* get a list with all books data from library
            *  to not use so much memory get only data required about books */
            ArrayList<BookListItem> myBooks = library.getBookListItems();

            //set adapter for 'myBooks' list
            customAdapter = new MybooksAdapter(getContext(),myBooks);
            if(customAdapter.getItemViewType(0) == 2)
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
            else recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
            recyclerView.setAdapter(customAdapter);

        } catch (Exception e) {
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    /** update displayed book list data */
    private void updateMyBooksList(){
        customAdapter = new MybooksAdapter(getContext(),library.getBookListItems());
    }

}
