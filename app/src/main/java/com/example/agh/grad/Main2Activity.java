package com.example.agh.grad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity implements ItemClickListner{
    List<String> arrayServices = new ArrayList<>();


    service[] services = {
            new service("السلام للسيارات", "the","2221573","مركز متكامل لصيانة السيارات وبيع جميع قطع الغيار","Sharm El-Sheikh, Qesm Sharm Ash Sheikh, South Sinai Governorate, Egypt","مركز متكامل لصيانة السيارات وبيع جميع قطع الغيار من خلال مجموعة من الحرفيين والمختصين باسعار مميزة في شرم الشيخ "),
            new service("مركز كار هوم ", "مركز متكامل لصيانة السيارات وبيع جميع قطع الغيار ", "the","01212312455","Mansoura, Mansoura Qism 2, Mansoura, Dakahlia Governorate, Egypt","مركز متكامل لصيانة السيارات وبيع جميع قطع الغيار من خلال مجموعة متميزة من المختصين والحرفيين "),
            new service("ميكانيكي الترعة", "لتصليح جميع انواع السيارات والموتسيكلات","the","012123124","Al Teraa St, Mansoura Qism 2, Mansoura, Dakahlia","مرحبا"),
    };
    RecyclerView recyclerViewServiceDetail;
    recylerServiceAdapter recylerServiceAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = this.getIntent();
        arrayServices= intent.getStringArrayListExtra("stringArrayService");
        //   System.out.println(arrayServices.size());
        ArrayList<DataSnapshot> arrayList = new ArrayList<>();


        recyclerViewServiceDetail = (RecyclerView) findViewById(R.id.recylerViewServiceDetail);
        recyclerViewServiceDetail.setLayoutManager(new LinearLayoutManager(this));
        recylerServiceAdapter = new recylerServiceAdapter(services, getApplicationContext());
        recyclerViewServiceDetail.setAdapter(recylerServiceAdapter);
        recylerServiceAdapter.setClickListener(this);
    }

    public void onClick(View view, int position) {
        Intent intent = new Intent(Main2Activity.this, serviceDetailActivity.class);
        intent.putExtra("serviceName", services[position].getServiceName());
        intent.putExtra("serviceProvider", services[position].getServiceProvider());
        intent.putExtra("serviceShortDescrption", services[position].getServiceDesrption());
        intent.putExtra("serviceLikeCounter", String.valueOf(services[position].getLikeCounter()));
        intent.putExtra("serviceDislikeCounter", String.valueOf(services[position].getDislikeCounter()));
        intent.putExtra("serviceFullDescrption",String.valueOf(services[position].getServiceFullDesrption()));
        intent.putExtra("ServiceLocation",String.valueOf(services[position].getLocation()));
        intent.putExtra("servicephoneNumber",String.valueOf(services[position].getPhoneNumber()));

        startActivity(intent);
    }
}
