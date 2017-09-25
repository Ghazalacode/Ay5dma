package com.example.agh.grad;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by cz on 17/06/17.
 */

public class recylerServiceAdapter extends RecyclerView.Adapter<recylerServiceAdapter.ViewHolder> {
    service[] service;
    Context context;
    ItemClickListner clickListner;

    public recylerServiceAdapter(service[] service, Context context) {
        this.service = service;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_service_detial, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        context= parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvServiceName.setText(service[position].getServiceName());
        holder.tvServiceDisrption.setText(service[position].getServiceDesrption());
        holder.tvServiceProvider.setText(service[position].getServiceProvider());


        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tvLikeCounter.setText(String.valueOf(service[position].getLikeCounter() + 1));
                holder.btnLike.setClickable(false);
                holder.btnDislike.setClickable(false);
            }
        });
        holder.btnDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tvDislikeCounter.setText(String.valueOf(service[position].getDislikeCounter() + 1));
                holder.btnDislike.setClickable(false);
                holder.btnLike.setClickable(false);
            }
        });
        holder.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + service[position].getPhoneNumber()));

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                context.startActivity(callIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return service.length;
    }

    public void setClickListener(ItemClickListner itemClickListener) {
        this.clickListner = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvServiceName, tvServiceProvider, tvServiceDisrption, tvLikeCounter, tvDislikeCounter;

        Button btnLike, btnDislike, btnCall;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);


            tvServiceName = (TextView) itemView.findViewById(R.id.serviceName);
            tvServiceProvider = (TextView) itemView.findViewById(R.id.serviceProvider);
            tvServiceDisrption = (TextView) itemView.findViewById(R.id.serviceShortDescrption);

            tvLikeCounter = (TextView) itemView.findViewById(R.id.tvLikeCounter);
            tvDislikeCounter = (TextView) itemView.findViewById(R.id.tvDislikeCounter);

            btnLike = (Button) itemView.findViewById(R.id.btnLike);
            btnDislike = (Button) itemView.findViewById(R.id.btnDislike);
            btnCall = (Button) itemView.findViewById(R.id.btnCall);

        }

        @Override
        public void onClick(View v) {
            clickListner.onClick(v, getAdapterPosition());
        }
    }
}
