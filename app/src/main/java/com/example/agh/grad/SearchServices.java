package com.example.agh.grad;

import android.annotation.SuppressLint;
import android.content.Context;
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

import com.example.agh.grad.Models.Services;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by agh on 24/06/17.
 */
public class SearchServices extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener mLocationListener;
    Location mCurrentLocation;
    float LOCATION_REFRESH_DISTANCE = 1;
    Integer LOCATION_REFRESH_TIME = 100;

    @BindView(R.id.Tags)
     EditText Tags;

    @BindView(R.id.btnRate)
     AppCompatButton btnRate;

    @BindView(R.id.btnLocation)
     AppCompatButton btnLocation;
    private TextView serName;
     List<String> tagList = new ArrayList<String>();
    final List<DataSnapshot> rateServices = new ArrayList<DataSnapshot>();
    final List<DataSnapshot> locationServices = new ArrayList<DataSnapshot>();



    final HashMap< DataSnapshot,Integer> orderRateServices = new HashMap<DataSnapshot,Integer>();
    final HashMap< DataSnapshot,Float> orderLocationServices = new HashMap<DataSnapshot,Float>();

            DatabaseReference   dbRefernce = FirebaseDatabase.getInstance().getReference("Services");

    ChildEventListener RateListener;
    ChildEventListener LocationListener;
    public static String PARCELER_TAG= "newServices";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_services);
        ButterKnife.bind(this);

        final Intent intentX = this.getIntent();


        serName = (TextView) findViewById(R.id.serName);
        mCurrentLocation = new Location("");


        if (intentX.hasExtra("tagSelected")) {

            String s = intentX.getStringArrayListExtra("tagSelected").toString();
            tagList=intentX.getStringArrayListExtra("tagSelected");
            String regex = "\\[|\\]";
            s = s.replaceAll(regex, "");
            s = s.trim();


            Tags.setText(s);

        }



        Tags.setOnClickListener(new View.OnClickListener() {


            public void onClick(View view) {
                Intent intent =
                        new Intent(SearchServices.this, ChooseTags.class);
                intent.putExtra("class", "SearchServices");

                startActivity(intent);
            }
        });

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
            Criteria crit = new Criteria();
            crit.setAccuracy(Criteria.ACCURACY_FINE);
            crit.setPowerRequirement(Criteria.POWER_LOW);
            // Gets the best matched provider, and only if it's on

            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            String provider = locationManager.getBestProvider(crit, true);
            locationManager.requestLocationUpdates(provider, LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, mLocationListener);


            mCurrentLocation = locationManager.getLastKnownLocation(provider);

        }


        System.out.println(mCurrentLocation.toString());


        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

// Attach an listener to read the data at our posts reference
                // it keeps listening because we used    addValueEventListener   not    addListenerForSingleValueEvent()

                dbRefernce.addValueEventListener(new ValueEventListener() {
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
                dbRefernce.addValueEventListener(new ValueEventListener() {
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
        if(RateListener!=null){
            dbRefernce.removeEventListener(RateListener);
        }
        if(LocationListener!=null){
            dbRefernce.removeEventListener(LocationListener);
        }


    }

    @SuppressLint("LongLogTag")
    public void findServicesWithTags(String type , DataSnapshot dataSnapshot){
        locationServices.clear();
        rateServices.clear();
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


                            locationServices.add(eventSnapshot);
                            Log.e("findServicesWithTags  locationServices.size()",locationServices.toString());

                        }
                        if (type.equals("rate")){


                            rateServices.add(eventSnapshot);
                            Log.e("findServicesWithTags rateServices.size()",rateServices.toString());

                        }




                    }

                    //else {  System.out.println( "mfesh elklam dah so8" );};
                }

                //  else {  System.out.println( "mfesh elklam dah kber" );}
            }
        }



    }

  @SuppressLint("LongLogTag")
  public void   sortRateServices(){
orderRateServices.clear();
      Integer[] array;

      Log.e(" sortRateServices rateServices.size()"  ,String.valueOf(rateServices.size()));

      array = new Integer[rateServices.size()];

      for (int i = 0; i < rateServices.size(); i++) {
          String thisLike = rateServices.get(i).child("likes").getValue().toString().replace("\"", " ").trim();

          Integer likes = Integer.parseInt(thisLike);
          String thisDis = rateServices.get(i).child("dislikes").getValue().toString().replace("\"", " ").trim();


          Integer Dislikes = Integer.parseInt(thisDis);
          orderRateServices.put(rateServices.get(i),likes - Dislikes );
          System.out.println(likes - Dislikes);
      }


      int x = 0;
      for (Object value : orderRateServices.values()) {

          array[x] = (Integer) value;
          Log.e("likes - Dislikes", array[x].toString());

          x++;
      }


      Arrays.sort(array, Collections.reverseOrder());

      final DataSnapshot[] sortedArray = new DataSnapshot[rateServices.size()];

      for (int i = 0; i < rateServices.size(); i++) {
          sortedArray[i] = (DataSnapshot) getKeyFromValue(orderRateServices ,array[i]);
          orderRateServices.values().remove(array[i]);


      }



      List<Services> serv  = new ArrayList<Services>();

      for (int i = 0; i <sortedArray.length ; i++) {
          Services someShityServices =sortedArray[i].getValue(Services.class);

          serv.add(someShityServices);
      }





      Log.e( "someShityServices: ", serv.toString());

      Intent intent = new Intent(SearchServices.this, MainActivity.class);


      intent.putExtra(SearchServices.PARCELER_TAG,Parcels.wrap(serv ));

      startActivity(intent);
      finish();


   /*   System.out.println("sortedArray" + sortedArray.toString());
      serName.setText("");
      serName.setText(Arrays.asList(sortedArray).toString());

      System.out.println(Arrays.asList(sortedArray).toString());*/

  }

    @SuppressLint("LongLogTag")
    public void   sortLocationServices(){
        orderLocationServices.clear();
        Log.e( "sortLocationServices orderLocationsize: ",String.valueOf(orderLocationServices.size()) );
        NumberFormat _format = NumberFormat.getInstance(Locale.US);
        Number lat = null;
        Number longt = null;

       // serName.setText(locationServices.toString());
        final Float[] array;

        array = new Float[locationServices.size()];


        for (int i = 0; i < locationServices.size(); i++) {
            Location serLocation = new Location("");

            try {

                lat = _format.parse(locationServices.get(i).child("latit").getValue().toString());
                longt = _format.parse(locationServices.get(i).child("longit").getValue().toString());
                double lat_double = Double.parseDouble(lat.toString());
                double long_double = Double.parseDouble(longt.toString());

                serLocation.setLatitude(lat_double);//your coords of course
                serLocation.setLongitude(long_double);
                System.err.println("Double Value is :" + serLocation.toString());
            } catch (ParseException e) {

            }


            Float distance = mCurrentLocation.distanceTo(serLocation);


            orderLocationServices.put( locationServices.get(i),distance);
            Log.e("distance is " ,distance.toString() );
        }
        Log.e( "orderLocationsize: ",String.valueOf(orderLocationServices.size()) );
        Log.e( "orderLocationServices: ", orderLocationServices.toString());


        int x = 0;
        for (Object value : orderLocationServices.values()) {

            array[x] = (float) value;

            System.out.println(array[x] + "  " + x);
            x++;
        }

        Arrays.sort(array);
        Log.e("sorted array " ,Arrays.asList(array).toString() );

        final DataSnapshot[] sortedArrayr = new DataSnapshot[locationServices.size()];

        for (int i = 0; i < locationServices.size(); i++) {
            sortedArrayr[i] =(DataSnapshot) getKeyFromValue(orderLocationServices ,array[i]);
            orderLocationServices.values().remove(array[i]);



        }
        List<Services> serv  = new ArrayList<Services>();

        for (int i = 0; i <sortedArrayr.length ; i++) {
            Services someShityServices =sortedArrayr[i].getValue(Services.class);

       serv.add(someShityServices);
        }


        Log.e( "someShityServices: ", serv.toString());

      Intent intent = new Intent(SearchServices.this, MainActivity.class);

        intent.putExtra(SearchServices.PARCELER_TAG,Parcels.wrap(serv ));

        startActivity(intent);
        finish();
       // Log.e(" nldgk ",  sortedArrayr[0].getKey().toString());
//        System.out.println(Arrays.asList(sortedArrayr).toString());

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public static Object getKeyFromValue(Map hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }
}


