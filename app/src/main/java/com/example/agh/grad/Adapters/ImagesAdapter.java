package com.example.agh.grad.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.agh.grad.R;
import com.example.agh.grad.Utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImagesAdapter  extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {

    ArrayList<String> Images;
    Context context;


    public ImagesAdapter(ArrayList<String> Images, Context context) {
        this.Images = Images;
        this.context = context;

    }

    @Override
    public ImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.images_recycler_item, parent, false);
        ImagesAdapter.ViewHolder viewHolder = new ImagesAdapter.ViewHolder(v);

        context= parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Glide.with(context)
                .load(Uri.parse(Images.get(position))) // Uri of the picture
                .into(holder.imageView);


      /*  Glide.with(context)
                .load(Base64.decode(Images.get(position), Base64.DEFAULT))
                .into(holder.imageView);*/

        //holder.imageView.s
        // / set image from position and list

    }

    @Override
    public int getItemCount() {
        return Images.size();
    }








    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

           @BindView(R.id.imaage)
        ImageView imageView;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);

        }

        @Override
        public void onClick(View v) {

            Utils.showImage(context , Uri.parse(Images.get(getAdapterPosition())));
        }
    }


}