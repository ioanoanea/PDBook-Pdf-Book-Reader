package com.ioanoanea.pdbook.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ioanoanea.pdbook.R;
import com.ioanoanea.pdbook.widgets.AutoResizeTextView;

public class FinishedViewHolder extends RecyclerView.ViewHolder {

    public AutoResizeTextView title;
    public TextView author, authorName, bookTitle;
    public ImageView cover;
    public ProgressBar progress;

    public FinishedViewHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.title);
        author = itemView.findViewById(R.id.author);
        cover = itemView.findViewById(R.id.image_cover);
        authorName = itemView.findViewById(R.id.book_author);
        bookTitle = itemView.findViewById(R.id.book_title);
        progress = itemView.findViewById(R.id.progress_bar);

        //set list item size
        /* ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
        int height = itemView.getLayoutParams().height;
        ViewGroup.LayoutParams newLayoutParams = new ViewGroup.LayoutParams(displayInfo.getWidth()/2-20,height);
        itemView.setLayoutParams(newLayoutParams); */

    }
}
