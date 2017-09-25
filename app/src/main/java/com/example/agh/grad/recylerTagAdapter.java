package com.example.agh.grad;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cz on 19/06/17.
 */

public class recylerTagAdapter extends RecyclerView.Adapter<recylerTagAdapter.ViewHolder> {
    ArrayList<String> tags = new ArrayList<String>();

    public recylerTagAdapter(ArrayList<String> tags)
    {
        this.tags = tags;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyeler_list_tag_choosen, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvTagChossen.setText(tags.get(position));
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTagChossen;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTagChossen = (TextView) itemView.findViewById(R.id.tvTagChossen);
        }
    }
}
