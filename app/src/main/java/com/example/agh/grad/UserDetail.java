package com.example.agh.grad;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by agh on 11/04/17.
 */
public class UserDetail extends AppCompatActivity {
    private ProgressBar progressBar;
    private AppCompatEditText etUserName;
    private TextView personalInformation;
    private AppCompatEditText etName;
    private TextView agetv;
    private AppCompatEditText etAge;
    private TextView age;
    private TextView gender;
    private TextView txtEmail;
    private TextView email;
    private TextView txtPhone;
    private AppCompatEditText etPhoneNum;
    private TextView fbaccount;
    private AppCompatEditText etJobDesc;
    private AppCompatButton ConfirmChanges;
    String emailf;
    String Uidf;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {









        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_detail);
        initialize();


        /// get data from and fill fields with it

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
       // UserModel usermod = new UserModel("Ravi Tamada", "ravi@androidhive.info");
        if (user != null) {
            // User is signed in

 emailf = user.getEmail();
            Uidf = user.getUid();



        }
        email.setText(emailf);

        // on confirm

      //  DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");

// Creating new user node, which returns the unique key value
// new user node would be /users/$userid/
       // String userId = mDatabase.push().getKey();

// creating user object


// pushing user to 'users' node using the userId
     //   mDatabase.child(userId).setValue(user);

ConfirmChanges.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
       // String key = database.getReference("users").push().getKey();

        final FirebaseDatabase firedatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firedatabase.getReference("Users");

        Map<String, Object> userData = new HashMap<>();
        userData.put( "uid",Uidf);
        userData.put( "name", etName.getText().toString());
        userData.put( "JobDesc",etJobDesc.getText().toString());
        userData.put( "age",etAge.getText().toString());
        userData.put( "email",emailf);
        userData.put( "phone",Double.parseDouble(etPhoneNum.getText().toString()));
        // other properties here

        ref = ref.child(Uidf);
        ref.setValue(userData);



        Intent myIntent=   new Intent(UserDetail.this, AddOrSearch.class);


        startActivity(myIntent);

    }

});



    }


    private void initialize() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        etUserName = (AppCompatEditText) findViewById(R.id.etUserName);
        personalInformation = (TextView) findViewById(R.id.personalInformation);
        etName = (AppCompatEditText) findViewById(R.id.etName);
        agetv = (TextView) findViewById(R.id.agetv);
        etAge = (AppCompatEditText) findViewById(R.id.etAge);
        age = (TextView) findViewById(R.id.age);
        gender = (TextView) findViewById(R.id.gender);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        email = (TextView) findViewById(R.id.email);
        txtPhone = (TextView) findViewById(R.id.txtPhone);
        etPhoneNum = (AppCompatEditText) findViewById(R.id.etPhoneNum);
        fbaccount = (TextView) findViewById(R.id.fbaccount);
        etJobDesc = (AppCompatEditText) findViewById(R.id.etJobDesc);
        ConfirmChanges = (AppCompatButton) findViewById(R.id.ConfirmChanges);


    }

}
