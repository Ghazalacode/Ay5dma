package com.example.agh.grad;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class serviceDetailActivity extends AppCompatActivity {
    String serviceName, serviceProvider, serviceShortDescrption, serviceLikeCounter, serviceDislikeCounter, serviceFullDecrption, serviceLocation, servicePhoneNumber;
    TextView tvServiceName, tvServiceProvider, tvServiceShortDescrption, tvServiceLikeCounter, tvServiceDislikeCounter, tvServiceFullDescrption, tvSerivcePhoneNumber, tvServiceLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);
        Intent intent = getIntent();




        serviceName = intent.getStringExtra("serviceName");
        serviceProvider = intent.getStringExtra("serviceProvider");
        serviceShortDescrption = intent.getStringExtra("serviceShortDescrption");
        serviceLikeCounter = intent.getStringExtra(String.valueOf("serviceLikeCounter"));
        serviceDislikeCounter = intent.getStringExtra(String.valueOf("serviceDislikeCounter"));
        serviceFullDecrption = intent.getStringExtra(String.valueOf("serviceFullDescrption"));
        serviceLocation = intent.getStringExtra(String.valueOf("ServiceLocation"));
        servicePhoneNumber = intent.getStringExtra(String.valueOf("servicephoneNumber"));


        tvServiceName = (TextView) findViewById(R.id.serviceName);
        tvServiceProvider = (TextView) findViewById(R.id.serviceProvider);
        tvServiceShortDescrption = (TextView) findViewById(R.id.serviceShortDescrption);
        tvServiceLikeCounter = (TextView) findViewById(R.id.tvLikeCounter);
        tvServiceDislikeCounter = (TextView) findViewById(R.id.tvDislikeCounter);
        tvServiceFullDescrption = (TextView) findViewById(R.id.serviceFullDecrption);
        tvServiceLocation = (TextView) findViewById(R.id.LocationDetails);
        tvSerivcePhoneNumber = (TextView) findViewById(R.id.PhoneNum);

        tvServiceName.setText(serviceName);
        tvServiceProvider.setText(serviceProvider);
        tvServiceShortDescrption.setText(serviceShortDescrption);
        tvServiceLikeCounter.setText(serviceLikeCounter);
        tvServiceDislikeCounter.setText(serviceDislikeCounter);
        tvSerivcePhoneNumber.setText(servicePhoneNumber);
        tvServiceFullDescrption.setText(serviceFullDecrption);
        tvServiceLocation.setText(serviceLocation);

        Button btn_Catgory = (Button) findViewById(R.id.ShowWay);
        btn_Catgory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(serviceDetailActivity.this, CatgoryTagActivity.class);
                startActivity(i);
            }
        });
        Button btn_call = (Button) findViewById(R.id.btnCall);
        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + servicePhoneNumber));

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
            }
        });
    }
}
