package com.example.agh.grad;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agh.grad.Adapters.ImagesAdapter;
import com.example.agh.grad.Utils.PrefSingleton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by agh on 18/06/17.
 */
public class AddService extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {


    @BindView(R.id.gallery)
    ImageView gallery;
    @BindView(R.id.camera)
    ImageView camera;
    @BindView(R.id.imagesLinear)
    LinearLayout imagesLinear;


    @BindView(R.id.etServiceName)
    EditText etServiceName;
    @BindView(R.id.Short)
    EditText Short;
    @BindView(R.id.etFullDescription)
    EditText etFullDescription;
    @BindView(R.id.companyLogo)
    TextView companyLogo;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.Tags)
    EditText Tags;
    @BindView(R.id.tagSelected)
    TextView tagSelected;
    @BindView(R.id.btnChooseLocation)
    AppCompatButton btnChooseLocation;
    @BindView(R.id.tvLocationName)
    TextView tvLocationName;
    @BindView(R.id.tvLocationAddres)
    TextView tvLocationAddres;
    @BindView(R.id.tvLocationCoo)
    TextView tvLocationCoo;
    @BindView(R.id.likes)
    EditText likes;
    @BindView(R.id.dislikes)
    EditText dislikes;
    @BindView(R.id.etPhoneNum)
    EditText etPhoneNum;
    @BindView(R.id.btnAddService)
    AppCompatButton btnAddService;

    String lat, longt = " xvvx", dataString = "hf", endingMark = "######";

    @BindView(R.id.rvImages)
    RecyclerView rvImages;
    ImagesAdapter imgAdapter;


    List<Uri> mSelected = new ArrayList<Uri>();
    ArrayList<String> mSelectedString = new ArrayList<String>();

    List<String> tagList = new ArrayList<String>();
    ArrayList<String> imageList = new ArrayList<String>();
    String regex = "\\[|\\]";

    String file;


    String Uidf;

    int PICK_IMAGE_MULTIPLE = 1;


// Good practice indicating cases with good named variables
  /*  int ACCESS_READ = 1;
    int ACCESS_CREATE = 2;
    */

    String imageEncoded;
    List<String> imagesEncodedList;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;
    @BindView(R.id.addcoordinatorLayout)
    CoordinatorLayout addcoordinatorLayout;
    @BindView(R.id.placeSnackBar)
    CoordinatorLayout placeSnackBar;

    public Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        ButterKnife.bind(this);


        mToolbar = (Toolbar) findViewById(R.id.toolbardd);

       setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);

        drawerFragment.setDrawerListener(this);

        Intent intent = this.getIntent();

        //  rvImages.setVisibility(View.INVISIBLE);


///// reading from file and setting saved data

        if (!intent.hasExtra("tvLocationName1") && !intent.hasExtra("tagSelected")) {

            writeToFile(" 111 ", getApplicationContext());
            System.out.println(" hola hola   em  hola hola");
        }


        String names[] = {"etServiceName :", "Short :", "etFullDescription :", "Tags :", "likes :"
                , "dislikes :", "etPhoneNum :", "tvLocationName :", "tvLocationCoo :", "tvLocationAddres :"};
        file = readFromFile(this);
        System.out.println(file);

        if (file != "") {
            setFieldsFromFile(names);
        }

        //obtain  Intent Object send  from SenderActivity

        /* Obtain String from Intent  */
        if (intent.hasExtra("tvLocationName1")) {
            tvLocationName.setText(intent.getStringExtra("tvLocationName1"));
            tvLocationAddres.setText(intent.getStringExtra("tvLocationAddres1"));
            lat = intent.getStringExtra("lat");
            longt = intent.getStringExtra("long");

            tvLocationCoo.setText(lat + "," + longt);


        } else if (intent.hasExtra("tagSelected")) {


            String s = intent.getStringArrayListExtra("tagSelected").toString();

            s = s.replaceAll(regex, "");
            tagList = new ArrayList<String>(Arrays.asList(s));

            Tags.setText(s);

        }


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in

            Uidf = user.getUid();


        }



        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_favorites:
                        Toast.makeText(AddService.this, "FAVORTITES", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.action_map:
                        Toast.makeText(AddService.this, "Map", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.action_search:
                        Toast.makeText(AddService.this, "Search", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return true;
            }
        });
        btnChooseLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent myIntent = new Intent(AddService.this, ChooseLocation.class);
                startActivity(myIntent);
            }
        });

        Tags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =
                        new Intent(AddService.this, ChooseTags.class);
                intent.putExtra("class", "AddService");
                startActivity(intent);

            }
        });


        companyLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etServiceName.setText("trrrrn");
                Short.setText("njd");
                etFullDescription.setText("dfjk");
                etPhoneNum.setText("04598");
                likes.setText("6157");
                dislikes.setText("55");
                Tags.setText("حرف:جنجار");

            }
        });

        if (null != PrefSingleton.getPref("images", AddService.this)) {

            // because gson cannot reflect ArrayList<Uri>
            Gson gson = new Gson();
            String jsonText = PrefSingleton.getPref("images", AddService.this);
            mSelectedString = gson.fromJson(jsonText, new TypeToken<ArrayList<String>>() {
            }.getType());
            mSelected.clear();
            for (String uriString : mSelectedString
                    ) {
                mSelected.add(Uri.parse(uriString));

            }

            rvImages.setVisibility(View.VISIBLE);
            rvImages.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            imgAdapter = new ImagesAdapter(mSelectedString, getApplicationContext());

            rvImages.setAdapter(imgAdapter);
            imgAdapter.notifyDataSetChanged();
        }


        btnAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //TODO  check if fields is empty and make shaky red


                final FirebaseDatabase firedatabase = FirebaseDatabase.getInstance();
                DatabaseReference ref = firedatabase.getReference("Services");


                Map<String, Object> userData = new HashMap<>();
                userData.put("serviceProvider", Uidf);
                userData.put("name", etServiceName.getText().toString());
                userData.put("serviceShortDesrption", Short.getText().toString());
                userData.put("serviceFullDesrption", etFullDescription.getText().toString());

                userData.put("phoneNumber", Double.parseDouble(etPhoneNum.getText().toString().trim()));

                userData.put("likes", Long.parseLong(likes.getText().toString().trim()));
                userData.put("dislikes", Long.parseLong(dislikes.getText().toString().trim()));

                userData.put("latit", lat);
                userData.put("longit", longt);

                userData.put("locationName", tvLocationName.getText().toString());
                userData.put("locationAddress", tvLocationAddres.getText().toString());

                // other properties here

                ref = ref.child(etServiceName.getText().toString());


                ref.push();
                final DatabaseReference finalRef = ref;
                ref.setValue(userData, new DatabaseReference.CompletionListener() {


                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                        if (databaseError == null) {


                            Log.e("mSelected.toString: ", mSelected.toString());

                            for (int i = 0; i < mSelected.size(); i++) {

                                StorageReference storageReference =
                                        FirebaseStorage.getInstance()
                                                .getReference(Uidf)
                                                .child(etServiceName.getText().toString())
                                                .child(mSelected.get(i).getLastPathSegment());
                                final String photoName = mSelected.get(i).getLastPathSegment();

                                storageReference.putFile(mSelected.get(i)).addOnCompleteListener(AddService.this,
                                        new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    Log.e("Download Url", task.getResult().getMetadata().getDownloadUrl()
                                                            .toString());
                                                    finalRef.child("images").child(photoName)
                                                            .setValue(task.getResult().getMetadata().getDownloadUrl()
                                                                    .toString());


                                                } else {
                                                    Log.e("Image upload", task.getException().toString());
                                                }

                                            }
                                        });


                            }

                        } else {
                            Log.w("onComplete Database", "Unable to write message to database.",
                                    databaseError.toException());
                        }


                    }
                });

                PrefSingleton.removePref("images", AddService.this);

                for (int i = 0; i < tagList.size(); i++) {

                    Log.e("tagList.get(i) ", tagList.get(i).trim());
                    ref.child("tags").child(tagList.get(i).trim()).setValue(true);
                }
                Snackbar.make(placeSnackBar,
                        " Service Added Successfully Service Added Successfully Service Added Successfully" +

                                "Service Added SuccessfullyService Added SuccessfullyService Added Successfully", Snackbar.LENGTH_LONG)
                        .setAction("Go Back ", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                Intent myIntent = new Intent(AddService.this, AddOrSearch.class);


                                startActivity(myIntent);
                            }
                        }).show();


             /*   Log.e( "onClick: ",imageList.toString() );

                Gson gson = new Gson();
                String jsonText = PrefSingleton.getPref("images",AddService.this);
                imageList = gson.fromJson(jsonText, new TypeToken<ArrayList<String>>(){}.getType());

                for (int i = 0; i < imageList.size(); i++) {

                    Log.e(String.valueOf(i), imageList.get(i).trim());

                    //this will return arraylist when calling service >>> because of the integer values of keys
                    ref.child("images").child(String.valueOf(i)).setValue(imageList.get(i).trim());
                }*/


                writeToFile(" ", getApplicationContext());


            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();

        if (!TextUtils.isEmpty(etServiceName.getText().toString())) {
            dataString = " etServiceName : " + etServiceName.getText().toString() + endingMark;
        }
        if (!TextUtils.isEmpty(Short.getText().toString())) {
            dataString += " \nShort : " + Short.getText().toString() + endingMark;
        }
        if (!TextUtils.isEmpty(etFullDescription.getText().toString())) {
            dataString += " \netFullDescription : " + etFullDescription.getText().toString() + endingMark;
        }
        if (!TextUtils.isEmpty(Tags.getText().toString())) {
            dataString += " \nTags : " + Tags.getText().toString() + endingMark;
        }

        if (!TextUtils.isEmpty(likes.getText().toString())) {
            dataString += " \nlikes : " + likes.getText().toString() + endingMark;
        }
        if (!TextUtils.isEmpty(dislikes.getText().toString())) {
            dataString += " \ndislikes : " + dislikes.getText().toString() + endingMark;
        }
        if (!TextUtils.isEmpty(etPhoneNum.getText().toString())) {
            dataString += " \netPhoneNum : " + etPhoneNum.getText().toString() + endingMark;
        }

        if (!TextUtils.isEmpty(tvLocationCoo.getText().toString()) &&
                !TextUtils.isEmpty(tvLocationAddres.getText().toString()) &&
                !TextUtils.isEmpty(tvLocationName.getText().toString())) {
            dataString += " \ntvLocationCoo : " + tvLocationCoo.getText().toString() + endingMark;
            dataString += " \ntvLocationAddres : " + tvLocationAddres.getText().toString() + endingMark;
            dataString += " \ntvLocationName : " + tvLocationName.getText().toString() + endingMark;
        }
        System.out.println(dataString);
        writeToFile(dataString, this);


    }


    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("temp.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("temp.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    void setFieldsFromFile(String names[]) {
        for (int i = 0; i < names.length; i++) {
            Pattern p = Pattern.compile(names[i] + "(.*?)" + endingMark);
            Matcher m = p.matcher(file);
            if (m.find()) {
                switch (i) {
                    case 0:
                        etServiceName.setText(m.group(1));
                        break;
                    case 1:
                        Short.setText(m.group(1));
                        break;
                    case 2:
                        etFullDescription.setText(m.group(1));
                        break;
                    case 3:
                        String[] tokens = m.group(1).split(",");
                        tagList = new ArrayList<>(Arrays.asList(tokens));
                        System.out.println(tagList.toString());
                        Tags.setText(tagList.toString().trim().replaceAll(regex, ""));
                        break;
                    case 4:
                        likes.setText(m.group(1));
                        break;
                    case 5:
                        dislikes.setText(m.group(1));
                        break;
                    case 6:
                        etPhoneNum.setText(m.group(1));
                        break;
                    case 7:
                        tvLocationName.setText(m.group(1));
                        break;
                    case 8:
                        tvLocationCoo.setText(m.group(1));
                        String string = m.group(1);
                        if (string.contains(",")) {

                            String[] parts = string.split(",");

                            lat = parts[0];
                            longt = parts[1];

                        }


                        break;
                    case 9:
                        tvLocationAddres.setText(m.group(1));

                        break;
                }

            }
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       /* if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }*/

        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, AddOrSearch.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void onActivityResult(final int requestCode, final int resultCode, final Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);


        switch (requestCode) {
            case 1:


                new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... params) {

                        if (requestCode == 1 && resultCode == RESULT_OK) {

                            mSelected.clear();
                            mSelectedString.clear();
                            mSelected = Matisse.obtainResult(imageReturnedIntent);

                            for (Uri uri : mSelected) {

                                mSelectedString.add(uri.toString());
                            }


                            Gson gson = new Gson();
                            String jsonText = gson.toJson(mSelectedString);
                            PrefSingleton.putPref("images", jsonText, getApplicationContext());




                        /*    for (int i = 0; i <mSelected.size() ; i++) {
                                try {


                                       wrong and heavy

                                   Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), mSelected.get(i));

                                    imageList.add(BitMapToString(bitmap));

                                    Gson gson = new Gson();

                                    String jsonText = gson.toJson(imageList);
                                PrefSingleton.putPref("images", jsonText, getApplicationContext());

                                } catch (IOException e) {

                                }


                            }*/


                            Log.d("Matisse", "mSelected: " + mSelected.toString());
                        }
                        return "done";

                    }

                    @Override
                    protected void onPostExecute(String msg) {

                        //do stuff

                        rvImages.setVisibility(View.VISIBLE);
                        rvImages.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                        imgAdapter = new ImagesAdapter(mSelectedString, getApplicationContext());

                        rvImages.setAdapter(imgAdapter);
                        imgAdapter.notifyDataSetChanged();

                        Log.e("onPostExecute: ", String.valueOf(imgAdapter.getItemCount()));


                    }
                }.execute(null, null, null);


                break;
            case 0:
                if (resultCode == RESULT_OK) {

                    Matisse.from(AddService.this)
                            .choose(MimeType.allOf())
                            .countable(true)
                            .maxSelectable(6)

                            .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                            .thumbnailScale(0.85f)
                            .imageEngine(new GlideEngine())
                            .forResult(1);
                }
                break;
        }
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }


    @OnClick({R.id.gallery, R.id.camera})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.gallery:
                Matisse.from(AddService.this)
                        .choose(MimeType.allOf())
                        .countable(true)
                        .maxSelectable(6)

                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideEngine())
                        .forResult(1);


                break;
            case R.id.camera:

                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);//zero can be replaced with any action code
                break;
        }
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {


    }
}


