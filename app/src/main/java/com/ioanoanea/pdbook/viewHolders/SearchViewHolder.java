package com.ioanoanea.pdbook.viewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ioanoanea.pdbook.R;

public class SearchViewHolder extends RecyclerView.ViewHolder {

    public TextView searchedText;
    public TextView searchedPage;

    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);

        searchedText = itemView.findViewById(R.id.text);
        searchedPage = itemView.findViewById(R.id.page);
    }
}
