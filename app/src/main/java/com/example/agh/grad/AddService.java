package com.example.agh.grad;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
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

/**
 * Created by agh on 18/06/17.
 */
public class AddService extends AppCompatActivity {


    private EditText birthDate;
    private EditText etFullDescription;
    private EditText Tags;

    private TextView tvLocationName;
    private TextView tvLocationAddres;
    private TextView tvLocationCoo;
    private EditText locaionDesc;
    private EditText etPhoneNum;
    private AppCompatButton addService;
    private EditText etServiceName;
    private TextView  tvTagchosen;

    private EditText Short;
    String lat, longt =" xvvx" , dataString="hf", endingMark= "######";
    private EditText likes;
    private EditText dislikes;
    private ImageView companyLogo;



    List<String> tagList = new ArrayList<String>();





    private AppCompatButton btnChooseLocation;


    String Uidf;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_service);

        initialize();
        Intent intent = this.getIntent();

///// reading from file and setting saved data


        if (intent .hasExtra("tvLocationName1") == false && intent .hasExtra("tagSelected")  == false ){

       writeToFile(" 111 ",getApplicationContext());
            System.out.println(" hola hola   em  hola hola");
        }


        String names[] =    {"etServiceName :","Short :","etFullDescription :","Tags :","likes :"
                ,"dislikes :","etPhoneNum :" , "tvLocationName :","tvLocationCoo :","tvLocationAddres :"};
        String file = readFromFile(this);
        System.out.println(file);


        if (file != ""){
            for (int i = 0; i < names.length; i++) {
                Pattern p = Pattern.compile(names[i]+"(.*?)"+endingMark);
                Matcher m = p.matcher(file);
                if (m.find()) {
                    switch (i){
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
                            String[]tokens = m.group(1).split(",");
                          tagList = new ArrayList<>(Arrays.asList(tokens));
                            System.out.println(tagList.toString());
                            Tags.setText(String.valueOf(tagList.size()));
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
                            if (string.contains(",")){

                                String[] parts = string.split(",");

                                lat = parts[0];
                                longt= parts[1];

                        }



                            break;
                        case 9:
                            tvLocationAddres.setText(m.group(1));

                            break;
                    }

                }
            }
        }

        //obtain  Intent Object send  from SenderActivity



  /* Obtain String from Intent  */
        if(intent .hasExtra("tvLocationName1")) {
            tvLocationName.setText( intent.getStringExtra("tvLocationName1"));
            tvLocationAddres.setText( intent.getStringExtra("tvLocationAddres1"));
            lat=    intent.getStringExtra("lat" );
            longt=   intent.getStringExtra("long" );

            tvLocationCoo.setText(lat+","+ longt);


        }
       else if(intent .hasExtra("tagSelected")) {
            String s = intent.getStringArrayListExtra("tagSelected").toString();
            tagList = intent.getStringArrayListExtra("tagSelected");
            String regex = "\\[|\\]";
            s = s.replaceAll(regex, "");

            Tags.setText(s);

        }









        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in

            Uidf = user.getUid();


        }


        btnChooseLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent myIntent=   new Intent(AddService.this, MapAct.class);


                startActivity(myIntent);
            }
        });
        Tags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =
                        new Intent(AddService.this, CatgoryTagActivity.class);
                intent.putExtra("class","AddService");
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
        likes.setText("6457");
        dislikes.setText("55");
        Tags.setText("حرف:جنجار");

    }
});
        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                // String key = database.getReference("users").push().getKey();

                final FirebaseDatabase firedatabase = FirebaseDatabase.getInstance();
                DatabaseReference ref = firedatabase.getReference("Services");





                Map<String, Object> userData = new HashMap<>();
                userData.put( "uid",Uidf);
                userData.put( "name", etServiceName.getText().toString());
                userData.put( "ShortDesc",Short.getText().toString());
                userData.put( "FullDesc",etFullDescription.getText().toString());

                userData.put( "phone",Double.parseDouble(etPhoneNum.getText().toString()));

                userData.put( "Likes",likes.getText().toString());
                userData.put( "Dislikes",dislikes.getText().toString());

                userData.put( "lat",lat);
                userData.put( "long",longt);

                userData.put( "LocationName",tvLocationName.getText().toString());
                userData.put( "LocationAddres",tvLocationAddres.getText().toString());

                // other properties here

                ref = ref.child(etServiceName.getText().toString());



                ref.push();
                ref.setValue(userData);


//                ref.child("Tags").child("health_oo").setValue(true);
//                ref.child("Tags").child("health_lkj").setValue(true);

                for (int i = 0; i < tagList.size(); i++) {

                    System.out.println( tagList.get(i).trim());
                    ref.child("Tags").child(tagList.get(i).trim()).setValue(true);
                }



                writeToFile(" ",getApplicationContext());


                Intent myIntent=   new Intent(AddService.this, AddOrSearch.class);


                startActivity(myIntent);

            }
        });




    }

    @Override
    protected void onStop() {
        super.onStop();

        if (!TextUtils.isEmpty(etServiceName.getText().toString())){
dataString = " etServiceName : " + etServiceName.getText().toString() + endingMark;
        }
        if (!TextUtils.isEmpty(Short.getText().toString())){
            dataString  += " \nShort : " + Short.getText().toString() + endingMark;
        }
        if (!TextUtils.isEmpty(etFullDescription.getText().toString())){
            dataString  += " \netFullDescription : " + etFullDescription.getText().toString() + endingMark;
        }
        if (!TextUtils.isEmpty(Tags.getText().toString())){
            dataString  += " \nTags : " + Tags.getText().toString() + endingMark;
        }

        if (!TextUtils.isEmpty(likes.getText().toString())){
            dataString  += " \nlikes : " + likes.getText().toString() + endingMark;
        }
        if (!TextUtils.isEmpty(dislikes.getText().toString())){
            dataString  += " \ndislikes : " + dislikes.getText().toString() + endingMark;
        }
        if (!TextUtils.isEmpty(etPhoneNum.getText().toString())){
            dataString  += " \netPhoneNum : " + etPhoneNum.getText().toString() + endingMark;
        }

        if (!TextUtils.isEmpty(tvLocationCoo.getText().toString()) &&
                !TextUtils.isEmpty(tvLocationAddres.getText().toString())&&
                !TextUtils.isEmpty(tvLocationName.getText().toString()) ){
            dataString  += " \ntvLocationCoo : " + tvLocationCoo.getText().toString() + endingMark;
            dataString  += " \ntvLocationAddres : " + tvLocationAddres.getText().toString() + endingMark;
            dataString  += " \ntvLocationName : " + tvLocationName.getText().toString() + endingMark;
        }
        System.out.println(dataString);
        writeToFile(dataString,this);


    }

    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("temp.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("temp.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    private void initialize() {


        companyLogo = (ImageView) findViewById(R.id.companyLogo);


        etServiceName = (EditText) findViewById(R.id.etServiceName);
        etServiceName.setText("");
        companyLogo = (ImageView) findViewById(R.id.companyLogo);

        Short = (EditText) findViewById(R.id.Short);
        Short .setText("");
        etFullDescription = (EditText) findViewById(R.id.etFullDescription);
        Short.setText("");
        Tags = (EditText) findViewById(R.id.Tags);
        btnChooseLocation = (AppCompatButton) findViewById(R.id.btnChooseLocation);
        tvLocationName = (TextView) findViewById(R.id.tvLocationName);
        tvLocationAddres = (TextView) findViewById(R.id.tvLocationAddres);
        tvLocationCoo = (TextView) findViewById(R.id.tvLocationCoo);
        //locaionDesc = (EditText) findViewById(R.id.locaion_desc);
        etPhoneNum = (EditText) findViewById(R.id.etPhoneNum);
        etPhoneNum .setText("");

        likes = (EditText) findViewById(R.id.likes);
        likes.setText("");
        dislikes = (EditText) findViewById(R.id.dislikes);
        dislikes.setText("");

        addService = (AppCompatButton) findViewById(R.id.btnAddService);
        tvTagchosen=(TextView) findViewById(R.id.tagSelected);

    }
}
