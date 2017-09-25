package com.example.agh.grad;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

/**
 * Created by agh on 15/06/17.
 */
public class AddOrSearch extends AppCompatActivity {


    AppCompatButton AddService;
     AppCompatButton SearchSearvices;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.add_or_search);
        AddService = (AppCompatButton) findViewById(R.id.AddService);
        SearchSearvices = (AppCompatButton) findViewById(R.id.SearchSearvices);

        AddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent=   new Intent(AddOrSearch.this, AddService.class);


                startActivity(myIntent);

            }
        });

        SearchSearvices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent=   new Intent(AddOrSearch.this, SearchServices.class);


                startActivity(myIntent);

            }
        });

    }






}
