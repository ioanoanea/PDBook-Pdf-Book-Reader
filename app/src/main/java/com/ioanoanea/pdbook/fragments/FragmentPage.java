package com.ioanoanea.pdbook.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.ioanoanea.pdbook.R;
import com.ioanoanea.pdbook.activities.Page;
import com.ioanoanea.pdbook.managers.Library;
import com.ioanoanea.pdbook.objects.Book;

import java.util.Objects;

public class FragmentPage extends Fragment {

    private TextView pageNumber;
    private TextView content;
    private Button addBookmark;
    private Button removeBookmark;
    private ConstraintLayout pageBackground;
    private NestedScrollView scrollView;

    private Library library;
    private Book book;
    private Bundle bundle;

    private int currentPageNumber;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.page_fragment,container,false);
    }

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // initializing all data
        initializing(view);

        // set page content
        pageNumber.setText(Integer.toString(currentPageNumber));
        content.setText(book.getPage(currentPageNumber));
        content.setTextSize(((Page) Objects.requireNonNull(getActivity())).textSize);


        // verify if page has a bookmark and if affirmative display it
         if (((Page) getActivity()).checkBookmark(currentPageNumber)){
            removeBookmark.setVisibility(View.VISIBLE);
            addBookmark.setVisibility(View.INVISIBLE);
        }

        /** when user touch top-right corner of the page display a
         *  bookmark icon in top-right corner of the page **/
        // when the invisible add-bookmark button is pressed
        addBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set add-bookmark button invisible
                addBookmark.setVisibility(View.INVISIBLE);
                // set remove-bookmark button visible
                removeBookmark.setVisibility(View.VISIBLE);
                // add bookmark
                ((Page) Objects.requireNonNull(getActivity())).addBookmark(currentPageNumber);
            }
        });

        // when the remove-bookmark button is pressed
        removeBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set remove-bookmark button invisible
                removeBookmark.setVisibility(View.INVISIBLE);
                // set add-bookmark button visible
                addBookmark.setVisibility(View.VISIBLE);
                // remove bookmark
                ((Page) Objects.requireNonNull(getActivity())).removeBookmark(currentPageNumber);
            }
        });


        // when user click on the page show options
        pageBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Page) Objects.requireNonNull(getActivity())).showOptions();
            }
        });


    }

    /** initializing all data, layout views and objects needed **/
    private void initializing(View view){
        // set views
        content = view.findViewById(R.id.content);
        pageNumber = view.findViewById(R.id.page_nr);
        addBookmark = view.findViewById(R.id.add_bookmark);
        removeBookmark = view.findViewById(R.id.remove_bookmark);
        pageBackground = view.findViewById(R.id.window);
        scrollView = view.findViewById(R.id.scrollView);

        // initializing objects
        library = new Library(Objects.requireNonNull(getContext()));
        book = ((Page) Objects.requireNonNull(getActivity())).book;
        bundle = getArguments();
        assert bundle != null;
        currentPageNumber = bundle.getInt("pageNumber");
    }


}
