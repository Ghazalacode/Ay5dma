package com.example.agh.grad;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by agh on 24/06/17.
 */
public class SearchServices extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener mLocationListener;
    Location mCurrentLocation;
    float LOCATION_REFRESH_DISTANCE = 1;
    Integer LOCATION_REFRESH_TIME = 100;
    private EditText Tags;
    private AppCompatButton btnRate;
    private AppCompatButton btnLocation;
    private TextView serName;
     List<String> tagList = new ArrayList<String>();
    final List<DataSnapshot> rateServices = new ArrayList<DataSnapshot>();
    final List<DataSnapshot> locationServices = new ArrayList<DataSnapshot>();



    final HashMap<Integer, DataSnapshot> orderRateServices = new HashMap<Integer, DataSnapshot>();
    final HashMap<Float, DataSnapshot> orderLocationServices = new HashMap<Float, DataSnapshot>();

            DatabaseReference   dbRefernce = FirebaseDatabase.getInstance().getReference("Services");

    ChildEventListener RateListener;
    ChildEventListener LocationListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.search_services);
        final Intent intentX = this.getIntent();

        Tags = (EditText) findViewById(R.id.Tags);
        btnRate = (AppCompatButton) findViewById(R.id.btnRate);
        btnLocation = (AppCompatButton) findViewById(R.id.btnLocation);
        serName = (TextView) findViewById(R.id.serName);
        mCurrentLocation = new Location("");

        Tags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =
                        new Intent(SearchServices.this, CatgoryTagActivity.class);
                intent.putExtra("class", "SearchServices");

                startActivity(intent);
            }
        });
        if (intentX.hasExtra("tagSelected")) {

            String s = intentX.getStringArrayListExtra("tagSelected").toString();
            tagList=intentX.getStringArrayListExtra("tagSelected");
            String regex = "\\[|\\]";
            s = s.replaceAll(regex, "");
            s = s.trim();


            Tags.setText(s);

        }



        final List<Integer> finalOrderlocationServices = new ArrayList<Integer>();
        final List<Integer> finalOrderRateServices = new ArrayList<Integer>();







        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            float LOCATION_REFRESH_DISTANCE = 1;
            Integer LOCATION_REFRESH_TIME = 100;

            mLocationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    mCurrentLocation = location;
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, mLocationListener);
            Criteria crit = new Criteria();
            crit.setAccuracy(Criteria.ACCURACY_FINE);
            crit.setPowerRequirement(Criteria.POWER_LOW);
// Gets the best matched provider, and only if it's on
            String provider = locationManager.getBestProvider(crit, true);
            mCurrentLocation = locationManager.getLastKnownLocation(provider);

        }


        System.out.println(mCurrentLocation.toString());


        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

// Attach an listener to read the data at our posts reference
                // it keeps listening because we used    addValueEventListener   not    addListenerForSingleValueEvent()

                dbRefernce.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //serName.setText(dataSnapshot.toString());


                   findServicesWithTags("rate",dataSnapshot);

                        if (!rateServices.isEmpty()) {
                            serName.setText(rateServices.toString());

                            sortRateServices();


                        } else {
                            serName.setText("empty empty ");
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                RateListener=        dbRefernce.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                        serName.setText(dataSnapshot.toString());
                        for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) { //
                            for (DataSnapshot childEventSnapshot : eventSnapshot.getChildren()) {
                                if (childEventSnapshot.hasChildren()) {
                                    Integer x=0;
                                    for (int i = 0; i <tagList.size() ; i++) {
                                        System.out.println(tagList.size() );
                                        Log.i( "onChildAdded: ", String.valueOf(tagList.size()));

                                        if (childEventSnapshot.hasChild(tagList.get(i))){
                                            System.out.println( tagList.get(i));
                                            x++;
                                        }
                                    }
                                    if (x.equals(tagList.size()) )

                                    {

                                        rateServices.add(eventSnapshot);

                                        System.out.println("lololololololololololololololololy");
                                    }

                                }

                            }
                        }

                        if (!rateServices.isEmpty()) {
                            serName.setText(rateServices.toString());

                           sortRateServices();


                        } else {
                       serName.setText("empty empty ");
                        }

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




/*
                Collection c;

                Iterator iter = c.iterator();

                Object first = iter.next();*/


/*
                Query query = FirebaseDatabase.getInstance().getReference("Services")*//*.orderByChild("user_id").equalTo("user_1")*//*;
                query.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) { //
                            System.out.println("GetChildren: "+eventSnapshot.child("name").getValue());

                           *//* for (DataSnapshot childEventSnapshot : eventSnapshot.getChildren()) {

                                System.out.println("GetChildren: "+childEventSnapshot.child("Tags").child("Education_365").getValue());
                            }*//*
                        }

                    }
*/



             /*   for (int i = 0; i < rateServices.size(); i++) {
                    int likes =  (int)     rateServices.get(i).child("Likes").getValue();
                    int Dislikes =  (int)    rateServices.get(i).child("Dislikes").getValue();
                    orderRateServices.put(

                    rateServices.get(i),likes-Dislikes);

                }*/


            }


        });

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // remove rootRef.removeEventListener();
                dbRefernce.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                       findServicesWithTags("location" ,dataSnapshot );




                        if (!locationServices.isEmpty()) {
                      sortLocationServices();

                      /*      for (int i = 0; i < sortedArrayr.length; i++) {
                                stringArrayServices.add(i, sortedArrayr[i].toString());

                            }*/

                                                              /*     Intent i = new Intent(SearchServices.this, MainActivity.class);
                                                                   i.putStringArrayListExtra("stringArrayService", (ArrayList<String>) stringArrayServices);

                                                                   startActivity(i);*/

                        } else {
                            serName.setText("empty empty ");
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            LocationListener=    dbRefernce.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    System.out.println( dataSnapshot.getChildren().toString());

                  //  for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) { //
                        for (DataSnapshot childEventSnapshot : dataSnapshot.getChildren()) {
                            if (childEventSnapshot.hasChildren()) {
                                Integer x=0;
                                for (int i = 0; i <tagList.size() ; i++) {
                                    System.out.println(tagList.size() );

                                    if (childEventSnapshot.hasChild(tagList.get(i))){
                                        System.out.println( tagList.get(i));
                                        x++;
                                    }
                                }
                                if (x.equals(tagList.size()) ) {

                                    locationServices.add(dataSnapshot);

                                    System.out.println("lololololololololololololololololy");
                                }

                                //else {  System.out.println( "mfesh elklam dah so8" );};
                            }

                            //  else {  System.out.println( "mfesh elklam dah kber" );}
                        }
                //    }



                    if (!locationServices.isEmpty()) {
                       sortLocationServices();


                    } else {
                        serName.setText("empty empty ");
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        dbRefernce.removeEventListener(RateListener);
        dbRefernce.removeEventListener(LocationListener);
    }

    public void findServicesWithTags(String type , DataSnapshot dataSnapshot){

        for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) { //
            for (DataSnapshot childEventSnapshot : eventSnapshot.getChildren()) {
                if (childEventSnapshot.hasChildren()) {
                    Integer x=0;
                    for (int i = 0; i <tagList.size() ; i++) {
                        System.out.println("tagList.size()" +tagList.size() );

                        if (childEventSnapshot.hasChild(tagList.get(i))){
                            System.out.println( tagList.get(i));
                            x++;
                        }
                    }
                    if (x.equals(tagList.size()) ) {

                        if (type.equals("location")){
                            locationServices.clear();

                            locationServices.add(eventSnapshot);
                            System.out.println("locationServices.size()"+locationServices.size());
                        }
                        if (type.equals("rate")){
                            rateServices.clear();

                            rateServices.add(eventSnapshot);
                            System.out.println("rateServices.size()"+rateServices.size());
                        }




                    }

                    //else {  System.out.println( "mfesh elklam dah so8" );};
                }

                //  else {  System.out.println( "mfesh elklam dah kber" );}
            }
        }



    }

  public void   sortRateServices(){
orderRateServices.clear();
      Integer[] array;
      System.out.println("rateServices.size()" + rateServices.size());
      array = new Integer[rateServices.size()];

      for (int i = 0; i < rateServices.size(); i++) {
          String thisLike = rateServices.get(i).child("Likes").getValue().toString().replace("\"", " ").trim();

          Integer likes = Integer.parseInt(thisLike);
          String thisDis = rateServices.get(i).child("Dislikes").getValue().toString().replace("\"", " ").trim();


          Integer Dislikes = Integer.parseInt(thisDis);
          orderRateServices.put(likes - Dislikes, rateServices.get(i));
          System.out.println(likes - Dislikes);
      }


      int x = 0;
      for (Object value : orderRateServices.keySet()) {

          array[x] = (Integer) value;
          System.out.println(" likes - Dislikes  ");
          System.out.println(array[x]);
          x++;
      }


      Arrays.sort(array, Collections.reverseOrder());

      final DataSnapshot[] sortedArray = new DataSnapshot[rateServices.size()];

      for (int i = 0; i < rateServices.size(); i++) {
          sortedArray[i] = orderRateServices.get(array[i]);

      }
      serName.setText("");
      serName.setText(Arrays.asList(sortedArray).toString());

      System.out.println(Arrays.asList(sortedArray).toString());

  }

    public void   sortLocationServices(){
        orderLocationServices.clear();
        NumberFormat _format = NumberFormat.getInstance(Locale.US);
        Number lat = null;
        Number longt = null;

        serName.setText(locationServices.toString());
        final float[] array;

        array = new float[locationServices.size()];


        for (int i = 0; i < locationServices.size(); i++) {
            Location serLocation = new Location("");

            try {
                lat = _format.parse(locationServices.get(i).child("lat").getValue().toString());
                longt = _format.parse(locationServices.get(i).child("long").getValue().toString());
                double lat_double = Double.parseDouble(lat.toString());
                double long_double = Double.parseDouble(longt.toString());

                serLocation.setLatitude(lat_double);//your coords of course
                serLocation.setLongitude(long_double);
                System.err.println("Double Value is :" + lat_double);
            } catch (ParseException e) {

            }


            float distance = mCurrentLocation.bearingTo(serLocation);


            orderLocationServices.put(distance, locationServices.get(i));
            System.out.println(distance);
        }


        int x = 0;
        for (Object value : orderLocationServices.keySet()) {

            array[x] = (float) value;

            System.out.println(array[x] + "  " + x);
            x++;
        }


        List<DataSnapshot> arrayServices = new ArrayList<DataSnapshot>();
        List<String> stringArrayServices = new ArrayList<String>();

        Arrays.sort(array);

        final DataSnapshot[] sortedArrayr = new DataSnapshot[locationServices.size()];

        for (int i = 0; i < locationServices.size(); i++) {
            sortedArrayr[i] = orderLocationServices.get(array[i]);

        }
        serName.setText("");
        serName.setText(Arrays.asList(sortedArrayr).toString());
        System.out.println(Arrays.asList(sortedArrayr).toString());

    }
}


