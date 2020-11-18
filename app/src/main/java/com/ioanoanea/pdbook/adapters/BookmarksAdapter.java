package com.ioanoanea.pdbook.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ioanoanea.pdbook.R;
import com.ioanoanea.pdbook.activities.BookmarksActivity;
import com.ioanoanea.pdbook.activities.Page;
import com.ioanoanea.pdbook.objects.Book;
import com.ioanoanea.pdbook.viewHolders.BookmarksViewHolder;
import com.ioanoanea.pdbook.viewHolders.NoItemsViewHolder;

import java.util.ArrayList;

public class BookmarksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Integer> list;
    private Book book;

    public BookmarksAdapter(Context context, ArrayList<Integer> list, Book book){
        this.context = context;
        this.list = list;
        this.book = book;
    }

    @Override
    public int getItemViewType(int position) {
        if(list.size() == 0 && position == 0)
            return 2;
        else return 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 1){
            View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_card,parent,false);
            return new BookmarksViewHolder(view);
        } else {
            View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.no_items,parent,false);
            return new NoItemsViewHolder(view);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if(holder.getItemViewType() == 2){
            final NoItemsViewHolder viewHolder = (NoItemsViewHolder) holder;

            viewHolder.text.setTextColor(context.getResources().getColor(R.color.colorTextSecondary));
            viewHolder.text.setText("No bookmarks");
        }
        else if(holder.getItemViewType() == 1){
            final BookmarksViewHolder viewHolder = (BookmarksViewHolder) holder;
            // set bookmarks list item
            viewHolder.bookmarkPageNumber.setText("At page " + String.valueOf(list.get(position)));
            viewHolder.bookmarkText.setText(getBookmarkText(position));

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // open book at specified page
                    ((Page) Page.activity).selectPage(list.get(position) + 1);
                    ((BookmarksActivity) context).finish();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if(list.size() == 0)
            return 1;
        else return list.size();
    }

    private String getBookmarkText(int position){
        String pageText = book.getPage(list.get(position));
        String bookmarkText;
        if(pageText.length() > 200)
            bookmarkText = pageText.substring(0,200);
        else bookmarkText = pageText;
        return bookmarkText.substring(0,bookmarkText.lastIndexOf(' ')) + " ...";
    }

}
