package com.ioanoanea.pdbook.viewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ioanoanea.pdbook.R;

public class ChaptersViewHolder extends RecyclerView.ViewHolder {

    public TextView chapterName;
    public TextView chapterPage;

    public ChaptersViewHolder(@NonNull View itemView) {
        super(itemView);

        chapterName = itemView.findViewById(R.id.chapter_name);
        chapterPage = itemView.findViewById(R.id.chapter_page);
    }
}
