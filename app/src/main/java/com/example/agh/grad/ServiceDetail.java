package com.example.agh.grad;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.agh.grad.Adapters.ImagesAdapter;
import com.example.agh.grad.Models.Services;

import org.parceler.Parcels;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by agh on 15/06/17.
 */
public class ServiceDetail extends AppCompatActivity {
    Services newService;
    @BindView(R.id.companyLogo)
    ImageView companyLogo;
    @BindView(R.id.serviceName)
    TextView serviceName;
    @BindView(R.id.serviceProvider)
    TextView serviceProvider;
    @BindView(R.id.btnLike)
    AppCompatButton btnLike;
    @BindView(R.id.tvLikeCounter)
    AppCompatTextView tvLikeCounter;
    @BindView(R.id.btnDislike)
    AppCompatButton btnDislike;
    @BindView(R.id.tvDislikeCounter)
    AppCompatTextView tvDislikeCounter;
    @BindView(R.id.btnCall)
    AppCompatButton btnCall;

    @BindView(R.id.serviceFullDecrption)
    TextView serviceDetails;

    @BindView(R.id.LocationDetails)
    TextView locationDetails;

    @BindView(R.id.PhoneNum)
    TextView phoneNum;
    @BindView(R.id.ShowWay)
    AppCompatButton ShowWay;

    Double Lat, Lng;




    Map<String, String> Images;
    Map<String, String> mapImages;
  /*  @BindView(R.id.rvImagesdt)
    RecyclerView rvImagesdt;*/
    ImagesAdapter imadap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);
        ButterKnife.bind(this);


        newService = Parcels.unwrap(this.getIntent().getParcelableExtra(SearchServices.PARCELER_TAG));


        serviceName.setText(newService.getName());
        serviceProvider.setText(newService.getServiceProvider());
        tvLikeCounter.setText(String.valueOf(newService.getLikes()));
        tvDislikeCounter.setText(String.valueOf(newService.getDislikes()));
        serviceDetails.setText(newService.getServiceFullDesrption());
        locationDetails.setText(newService.getLatit() + newService.getLongit());
        phoneNum.setText(newService.getPhoneNumber().toString());
        Lat = Double.parseDouble(newService.getLatit());
        Lng = Double.parseDouble(newService.getLongit());

/*       check where is recycelr      */
 /*      if (newService.getImages() != null)
       { Images = newService.getImages();

           rvImagesdt.setVisibility(View.VISIBLE);
           rvImagesdt.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
           imadap = new ImagesAdapter(new ArrayList<String>(Images.values()), getApplicationContext());

           rvImagesdt.setAdapter(imadap);
           imadap.notifyDataSetChanged();
           Log.e("Images: ", Images.toString());


           Log.e("onCreate: ", newService.toString());
       }*/




    }



    @OnClick({R.id.btnLike, R.id.btnDislike, R.id.ShowWay, R.id.btnCall})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLike:


                break;


            case R.id.btnDislike:
                break;


            case R.id.ShowWay:

                Intent intent = new Intent(ServiceDetail.this, MapAct.class);


                intent.putExtra("latit", Lat);
                intent.putExtra("longit", Lng);

                startActivity(intent);
                finish();


                break;


            case R.id.btnCall:


                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + newService.getPhoneNumber()));

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);


                break;


        }


    }


}
