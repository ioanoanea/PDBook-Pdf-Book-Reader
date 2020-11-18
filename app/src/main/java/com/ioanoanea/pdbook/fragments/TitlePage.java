package com.ioanoanea.pdbook.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.ioanoanea.pdbook.R;
import com.ioanoanea.pdbook.activities.Page;
import com.ioanoanea.pdbook.managers.ProgressManager;
import com.ioanoanea.pdbook.objects.Book;

import java.util.Objects;

public class TitlePage extends Fragment {

    private TextView text;
    private ConstraintLayout background;
    private Bundle bundle;
    private ProgressManager progressManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.prologue_page,container,false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // initializing data
        initializing(view);

        // set text
        text.setText(bundle.getString("text"));

        // set progress
        final Book book = ((Page) Objects.requireNonNull(getActivity())).book;

        // when user click on the page show options
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Page) Objects.requireNonNull(getActivity())).showOptions();
            }
        });
    }

    private void initializing(View view){
        // initializing views
        text = view.findViewById(R.id.text);
        background = view.findViewById(R.id.background);

        // initializing objects
        bundle = getArguments();
        progressManager = new ProgressManager(getContext());
    }
}
