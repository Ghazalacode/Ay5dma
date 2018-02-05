package com.example.agh.grad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.agh.grad.Adapters.recylerServiceAdapter;
import com.example.agh.grad.Models.Services;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    List<String> arrayServices = new ArrayList<>();

    RecyclerView recyclerViewServices;
    recylerServiceAdapter recylerServiceAdapter;
   ArrayList<Services> newServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = this.getIntent();
      //  arrayServices= intent.getStringArrayListExtra("newServices");
     //   System.out.println(arrayServices.size());
       newServices = Parcels.unwrap(intent.getParcelableExtra(SearchServices.PARCELER_TAG)) ;

    Log.e( "onCreate: ",newServices.toString() );
        recyclerViewServices = (RecyclerView) findViewById(R.id.recylerViewServiceDetail);
        recyclerViewServices.setLayoutManager(new LinearLayoutManager(this));
        recylerServiceAdapter = new recylerServiceAdapter(newServices, getApplicationContext());
        recyclerViewServices.setAdapter(recylerServiceAdapter);


}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        // Here is where we are going to implement the filter logic


        if ( TextUtils.isEmpty ( query ) ) {
            recylerServiceAdapter.getFilter().filter("");
        } else {
            recylerServiceAdapter.getFilter().filter(query.toString());
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }




}
