package com.ioanoanea.pdbook.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ioanoanea.pdbook.R;

public class BookmarksViewHolder extends RecyclerView.ViewHolder {

    public TextView bookmarkPageNumber;
    public TextView bookmarkText;
    public ImageView icon;

    public BookmarksViewHolder(@NonNull View itemView) {
        super(itemView);

        bookmarkPageNumber = itemView.findViewById(R.id.bookmark_page);
        bookmarkText = itemView.findViewById(R.id.bookmark_text);
        icon = itemView.findViewById(R.id.icon);
    }
}
