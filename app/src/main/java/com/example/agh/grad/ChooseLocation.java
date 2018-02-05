package com.example.agh.grad;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by agh on 06/02/17.
 */

public class ChooseLocation extends AppCompatActivity
        /*implements *//*OnMapReadyCallback,*//*
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener */ {
    //    public Toolbar mToolbar;
    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;


    double Lat, Lng;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    @BindView(R.id.myEditText)
    EditText etPlace;
    @BindView(R.id.tvLocationName)
    TextView tvLocationName;
    @BindView(R.id.tvLocationAddres)
    TextView tvLocationAddres;
    @BindView(R.id.tvLocationCoo)
    TextView tvLocationCoo;
    @BindView(R.id.btnConfirmLocation)
    AppCompatButton btnConfirmLocation;
    @BindView(R.id.btnGoMap)
    AppCompatButton btnGoMap;


    private LinearLayout containerToolbar;


    Place place;

    String tag;

    private static final int PLACE_PICKER_REQUEST = 1;
    private static LatLngBounds BOUNDS_MOUNTAIN_VIEW;
    private String TAG = "ChooseLocation";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private Context mContext = ChooseLocation.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag = getIntent().getStringExtra(" tvTagchosen");

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_choose_location);
        ButterKnife.bind(this);


        etPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(ChooseLocation.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }

            }


        });


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                Lat = 0;
                Lng = 0;
            } else {
                Lat = extras.getDouble("latitude");
                Lng = extras.getDouble("longtitude");
            }
        } else {
            Lat = (double) savedInstanceState.getSerializable("latitude");

            Lng = (double) savedInstanceState.getSerializable("longtitude");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

////////              mGoogleMap.clear();
                place = PlaceAutocomplete.getPlace(this, data);


                tvLocationName.setText(place.getName());
                tvLocationAddres.setText(place.getAddress());
                tvLocationCoo.setText(place.getLatLng().latitude + "," + place.getLatLng().longitude);
                btnConfirmLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent myIntent = new Intent(ChooseLocation.this, AddService.class);


                        myIntent.putExtra("tvLocationName1", place.getName().toString());
                        myIntent.putExtra("tvLocationAddres1", place.getAddress().toString());
                        myIntent.putExtra("lat", String.valueOf(place.getLatLng().latitude));
                        myIntent.putExtra("long", String.valueOf(place.getLatLng().longitude));
                        myIntent.putExtra("tvTagchosen", tag);

                        startActivity(myIntent);
                    }
                });


                Toast.makeText(ChooseLocation.this, place.getLatLng().toString(),
                        Toast.LENGTH_LONG).show();

                ///////   LatLng origin =new LatLng(mGoogleMap.getMyLocation().getLatitude(),mGoogleMap.getMyLocation().getLongitude());
                LatLng dest = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);


            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                //  Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }


    @Override
    public void onPause() {
        super.onPause();

    }


    @OnClick(R.id.btnGoMap)
    public void onViewClicked() {
        Intent myIntent = new Intent(ChooseLocation.this, MapAct.class);

        BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
                new LatLng(place.getLatLng().latitude, place.getLatLng().longitude), new LatLng(place.getLatLng().latitude, place.getLatLng().longitude));

        try {
            PlacePicker.IntentBuilder intentBuilder =
                    new PlacePicker.IntentBuilder();
            intentBuilder.setLatLngBounds(BOUNDS_MOUNTAIN_VIEW);
            Intent intent = intentBuilder.build(this);
            startActivityForResult(intent, PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }
    /*    myIntent.putExtra("tvLocationName1", place.getName().toString());
        myIntent.putExtra("tvLocationAddres1", place.getAddress().toString());
        myIntent.putExtra("GPlat", String.valueOf(place.getLatLng().latitude));
        myIntent.putExtra("GPlong", String.valueOf(place.getLatLng().longitude));
        myIntent.putExtra("tvTagchosen", tag);

        startActivity(myIntent);*/


    /*  Checking if Google play services is ok
       before starting an activity that require it */

    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(mContext);

        if (available == ConnectionResult.SUCCESS) {
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog((Activity) mContext, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}

