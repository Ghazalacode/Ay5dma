package com.example.agh.grad.Adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.agh.grad.Models.Services;
import com.example.agh.grad.R;
import com.example.agh.grad.SearchServices;
import com.example.agh.grad.ServiceDetail;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cz on 17/06/17.
 */

public class recylerServiceAdapter extends RecyclerView.Adapter<recylerServiceAdapter.ViewHolder>  implements Filterable{
    ArrayList<Services> services;
    Context context;

    private List<Services> orig;

    public recylerServiceAdapter(ArrayList<Services> services, Context context) {
        this.services = services;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recyler_services, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        context= parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvServiceName.setText(services.get(position).getName());
        holder.tvServiceDisrption.setText(services.get(position).getServiceShortDesrption());
        holder.tvServiceProvider.setText(services.get(position).getServiceProvider());
        holder.tvLikeCounter.setText(String.valueOf(services.get(position).getLikes()));
        holder.tvDislikeCounter.setText(String.valueOf(services.get(position).getDislikes()));
        holder.tvLocationDescrption.setText(services.get(position).getLocationAddress());


        holder.tvServiceName.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        /*   Intent myIntent=   new Intent(context,SearchServices.class);
        context.startActivity(myIntent);*/
    }
});
        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FirebaseDatabase firedatabase = FirebaseDatabase.getInstance();
                DatabaseReference ref = firedatabase.getReference("Services");
                DatabaseReference wantedService =    ref.child(String.valueOf(services.get(holder.getAdapterPosition()).getName()));
         final  Long uplikes=    services.get(holder.getAdapterPosition()).getLikes()+1;

                wantedService.child("likes").setValue(uplikes, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        holder.tvLikeCounter.setText(String.valueOf(uplikes));
                    }
                });
             // TODO: 11/10/17  update on the database

                //// TODO: 05/12/17  Check state of buttons and act upon it

                holder.btnLike.setClickable(false);//// TODO: 05/12/17 set a clicked icon
                holder.btnDislike.setClickable(false);
            }
        });
        holder.btnDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseDatabase firedatabase = FirebaseDatabase.getInstance();
                DatabaseReference ref = firedatabase.getReference("Services");
                DatabaseReference wantedService =    ref.child(String.valueOf(services.get(holder.getAdapterPosition()).getName()));
                final  Long dislikes=    services.get(holder.getAdapterPosition()).getLikes()-1;

                wantedService.child("likes").setValue(dislikes, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        holder.tvLikeCounter.setText(String.valueOf(dislikes));
                    }
                });
            }
        });
        holder.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + services.get(holder.getAdapterPosition()).getPhoneNumber()));

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Ask for permission
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

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent=   new Intent(context,ServiceDetail.class);

                myIntent.putExtra(SearchServices.PARCELER_TAG, Parcels.wrap(services.get(holder.getAdapterPosition()) ));
                context.startActivity(myIntent);

                ((Activity)context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return services.size();
    }



    @Override
    public Filter getFilter() {

            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    final FilterResults oReturn = new FilterResults();
                    final List<Services> results = new ArrayList<Services>();
                    if (orig == null)
                        orig  = services;
                    if (constraint != null){
                        if(orig !=null & orig.size()>0 ){
                            for ( final Services g :orig) {
                                if (g.getName().toLowerCase().contains(constraint.toString()))
                                    results.add(g);
                            }
                        }
                        oReturn.values = results;
                    }
                    return oReturn;
                }
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    services = (ArrayList<Services>)results.values;
                    notifyDataSetChanged();

                }
            } ;};

        public class ViewHolder extends RecyclerView.ViewHolder  {
        @BindView (R.id.serviceName) TextView tvServiceName;
        @BindView (R.id.serviceProvider)TextView tvServiceProvider;
        @BindView (R.id.serviceShortDescrption)TextView tvServiceDisrption;
        @BindView (R.id.tvLikeCounter)TextView tvLikeCounter;
        @BindView (R.id.tvDislikeCounter)TextView tvDislikeCounter ;
        @BindView (R.id.locationDescrption)TextView tvLocationDescrption;

        @BindView (R.id.btnLike) Button btnLike;
        @BindView (R.id.btnDislike)  Button btnDislike;
        @BindView (R.id.btnCall) Button btnCall;
        @BindView (R.id.serviceCard) CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setTag(itemView);

            ButterKnife.bind(this, itemView);

        }


    }
}
