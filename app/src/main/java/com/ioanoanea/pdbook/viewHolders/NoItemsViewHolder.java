package com.ioanoanea.pdbook.viewHolders;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ioanoanea.pdbook.R;

public class NoItemsViewHolder extends RecyclerView.ViewHolder {

    public TextView text;

    public NoItemsViewHolder(@NonNull View itemView) {
        super(itemView);

        text = itemView.findViewById(R.id.text);

        // set item view width to match parent
        ViewGroup.LayoutParams params = itemView.getLayoutParams();
        params.width = -1;
        itemView.setLayoutParams(params);
    }
}
