package com.ioanoanea.pdbook.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ioanoanea.pdbook.R;
import com.ioanoanea.pdbook.adapters.InProgressAdapter;
import com.ioanoanea.pdbook.managers.Library;
import com.ioanoanea.pdbook.managers.ProgressManager;
import com.ioanoanea.pdbook.objects.BookListItem;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Objects;

public class InProgressFragment extends Fragment {

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private AdView adView;
    private Library library;
    private InProgressAdapter customAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.in_progress_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // initializing data
        initializing(view);

        // show books
        showBooks();

        // set refresh layout
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        updateInProgressBookList();
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
                //showBooks();
                //refreshLayout.setRefreshing(false);
            }

        });

        // initializing ads
        //initializeAds();

    }

    /** initialize all data */
    private void initializing(View view){
        // initializing views
        refreshLayout = view.findViewById(R.id.refresh);
        recyclerView = view.findViewById(R.id.list);
        //adView = view.findViewById(R.id.adView);

        // initializing objects
        library = new Library(getContext());
    }

    /** get books data from library book list and display books
     *  display ony in progress books */
    private void showBooks(){

        try {
            ArrayList<BookListItem> listItems = getInProgressBooks();
            //set adapter for 'myBooks' list
            customAdapter = new InProgressAdapter(getContext(), listItems);
            if(customAdapter.getItemViewType(0) == 2)
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
            else recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
            recyclerView.setAdapter(customAdapter);

            refreshLayout.setRefreshing(false);

        } catch (Exception e) {
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    /** get list with books in progress data
     *  this does not return complete books
     *  returns only required data, to use few memory */
    private ArrayList<BookListItem> getInProgressBooks(){

        try {
            // create a list with  books from library
            ArrayList<BookListItem> inProgressBooks = new ArrayList<>();

            // initializing a progress manager
            ProgressManager progressManager = new ProgressManager(getContext());
            // determine book list order based on last opening
            ArrayList<Integer> listOrder = progressManager.getListOrder();
            int length = listOrder.size();

            // get each book from library which is still in progress
            for(int i = 0; i < length; i++){
                BookListItem listItem = library.getBookListItem(listOrder.get(i));
                int progress = progressManager.getProgress(listItem.getId());
                if(progress > 0 && progress <= listItem.getNumberOfPages())
                    inProgressBooks.add(listItem);
            }

            return inProgressBooks;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /** update displayed book list data */
    private void updateInProgressBookList(){
        customAdapter = new InProgressAdapter(getContext(), Objects.requireNonNull(getInProgressBooks()));
    }

    /** initializing mobile ads */
    private void initializeAds(){
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
    }
}
