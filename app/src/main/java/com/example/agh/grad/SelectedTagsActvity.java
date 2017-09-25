package com.example.agh.grad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class SelectedTagsActvity extends AppCompatActivity {
RecyclerView recyclerViewTagSelected;
    recylerTagAdapter recylerTagAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_tags_actvity);
        recyclerViewTagSelected= (RecyclerView) findViewById(R.id.recylerTagSelected);
        recyclerViewTagSelected.setLayoutManager(new LinearLayoutManager(this));
        Intent i = getIntent();
        recylerTagAdapter = new recylerTagAdapter(i.getExtras().getStringArrayList("tagSelected"));
        recyclerViewTagSelected.setAdapter(recylerTagAdapter);


    }
}
