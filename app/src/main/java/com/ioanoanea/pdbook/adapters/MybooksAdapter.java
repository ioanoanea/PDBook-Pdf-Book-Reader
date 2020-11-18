package com.ioanoanea.pdbook.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ioanoanea.pdbook.R;
import com.ioanoanea.pdbook.activities.BookOptionsActivity;
import com.ioanoanea.pdbook.managers.ProgressManager;
import com.ioanoanea.pdbook.activities.Page;
import com.ioanoanea.pdbook.objects.BookListItem;
import com.ioanoanea.pdbook.objects.DisplayInfo;
import com.ioanoanea.pdbook.viewHolders.MyBooksViewHolder;
import com.ioanoanea.pdbook.viewHolders.NoItemsViewHolder;
import com.ioanoanea.pdbook.widgets.AutoResizeTextView;

import java.util.ArrayList;

public class MybooksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public ArrayList<BookListItem> list;
    private ArrayList<AutoResizeTextView> titles = new ArrayList<>();
    private Context context;
    private int length;

    public MybooksAdapter(Context context, ArrayList<BookListItem> list){
        this.list = list;
        this.context = context;
        length = list.size() - 1;
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
            View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_cover, parent,false);

            // initialize viewHolder
            MyBooksViewHolder viewHolder = new MyBooksViewHolder(view);

            // set book width and height
            DisplayInfo displayInfo = new DisplayInfo(context);
            viewHolder.itemView.setLayoutParams(new ViewGroup.LayoutParams(displayInfo.getWidth() / 2 - 20,viewHolder.itemView.getLayoutParams().height));

            return viewHolder;

        } else if(viewType == 2) {
            View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.no_items, parent,false);
            return new NoItemsViewHolder(view);
        } else return null;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {
        final int position = length - i;

        if(holder.getItemViewType() == 2){
            final NoItemsViewHolder viewHolder = (NoItemsViewHolder) holder;

            viewHolder.text.setTextColor(context.getResources().getColor(R.color.colorTextSecondary));
            viewHolder.text.setText("There are no books here");
        }
        else if(holder.getItemViewType() == 1){
            final MyBooksViewHolder viewHolder = (MyBooksViewHolder) holder;

            try{
                // set the views with book information
                viewHolder.title.setText(list.get(position).getTitle());
                viewHolder.author.setText(list.get(position).getAuthor());
                viewHolder.cover.setBackgroundColor(context.getResources().getColor(list.get(position).getCover().getBackgroundResource()));
                viewHolder.title.setTextColor(context.getResources().getColor(list.get(position).getCover().getTitleColor()));
                viewHolder.bookTitle.setText(list.get(position).getTitle());
                viewHolder.authorName.setText(list.get(position).getAuthor());

                // open book on book cover click
                viewHolder.cover.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.startActivity(new Intent(context, Page.class).putExtra("book",list.get(position).getId()));
                        ProgressManager progressManager = new ProgressManager(context);
                        progressManager.updateListOrder(list.get(position).getId());
                    }
                });

                // show options menu on cover long click
                viewHolder.cover.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        Intent intent = new Intent(context, BookOptionsActivity.class);
                        intent.putExtra("book", list.get(position).getId());
                        intent.putExtra("position", i);
                        context.startActivity(intent);
                        return false;
                    }
                });


                // open book on book title click
                viewHolder.bookTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.startActivity(new Intent(context, Page.class).putExtra("book",list.get(position).getId()));
                        ProgressManager progressManager = new ProgressManager(context);
                        progressManager.updateListOrder(list.get(position).getId());
                    }
                });

                //Toast.makeText(context, String.valueOf(list.get(position).getId()), Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(context, "something went wrong!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public int getItemCount() {
        if(list.size() == 0)
            return 1;
        else return list.size();
    }


}
