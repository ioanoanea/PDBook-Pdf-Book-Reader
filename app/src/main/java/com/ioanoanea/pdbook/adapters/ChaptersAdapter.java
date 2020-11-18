package com.ioanoanea.pdbook.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ioanoanea.pdbook.R;
import com.ioanoanea.pdbook.activities.ChaptersActivity;
import com.ioanoanea.pdbook.activities.Page;
import com.ioanoanea.pdbook.viewHolders.ChaptersViewHolder;
import com.ioanoanea.pdbook.viewHolders.NoItemsViewHolder;

import java.util.ArrayList;

public class ChaptersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private int bookId;
    private ArrayList<Integer> list;

    public ChaptersAdapter(Context context, int bookId, ArrayList<Integer> list){
        this.context = context;
        this.bookId = bookId;
        this.list = list;
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
            View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter_card,parent,false);
            return new ChaptersViewHolder(view);
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
            viewHolder.text.setText("this book has no chapters");
        }
        else if(holder.getItemViewType() == 1){
            final ChaptersViewHolder viewHolder = (ChaptersViewHolder) holder;
            // set each chapter text
            viewHolder.chapterName.setText("Chapter " + String.valueOf(position + 1));
            viewHolder.chapterPage.setText("Starts at page " + list.get(position));

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // open book at specified page
                    ((Page) Page.activity).selectPage(list.get(position) + 1);
                    ((ChaptersActivity) context).finish();
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
}
