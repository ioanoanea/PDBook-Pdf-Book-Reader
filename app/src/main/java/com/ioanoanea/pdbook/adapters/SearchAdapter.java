package com.ioanoanea.pdbook.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ioanoanea.pdbook.R;
import com.ioanoanea.pdbook.activities.Page;
import com.ioanoanea.pdbook.activities.SearchActivity;
import com.ioanoanea.pdbook.objects.SearchItem;
import com.ioanoanea.pdbook.viewHolders.NoItemsViewHolder;
import com.ioanoanea.pdbook.viewHolders.SearchViewHolder;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<SearchItem> list;
    private String searchValue;

    public SearchAdapter(Context context, String searchValue, ArrayList<SearchItem> list){
        this.context = context;
        this.list = list;
        this.searchValue = searchValue;
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
            View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_card, parent,false);
            return new SearchViewHolder(view);
        } else {
            View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.no_items, parent,false);
            return new NoItemsViewHolder(view);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if(holder.getItemViewType() == 2){
            final NoItemsViewHolder viewHolder = (NoItemsViewHolder) holder;

            viewHolder.text.setTextColor(context.getResources().getColor(R.color.colorTextSecondary));
            viewHolder.text.setText("Nothing to show");
        }
        else if(holder.getItemViewType() == 1){
            final SearchViewHolder viewHolder = (SearchViewHolder) holder;

            // set page
            viewHolder.searchedPage.setText("At page " + String.valueOf(list.get(position).getPage()));
            // set text
            viewHolder.searchedText.setText(Html.fromHtml(getFinalString(list.get(position).getSearchedText())));

            // go to page when user clicks on search result
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((Page) Page.activity).viewPager.setCurrentItem(list.get(position).getPage());
                    ((SearchActivity) context).finish();
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


    /** recreate search result text
     *  make searched text bold from rest of sequence */
    private String getFinalString(String string){
        return string.replaceAll(searchValue,"<b><font color=\"#4B4B4B\">" + searchValue + "</font></b>");
    }
}
