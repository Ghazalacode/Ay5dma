package com.example.agh.grad;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by agh on 11/04/17.
 */
public class SignUp extends AppCompatActivity{

    @BindView(R.id.userNameRegister)
  AppCompatEditText userNameRegister;

    @BindView(R.id.passwordRegister)
 AppCompatEditText passwordRegister;
    @BindView(R.id.emailRegister)
 AppCompatEditText emailRegister;

    @BindView(R.id.jobTitleRegister)
AppCompatEditText jobTitleRegister;
    @BindView(R.id.signup)
   AppCompatButton signup;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(userNameRegister.getText().toString(),passwordRegister.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(SignUp.this, "تم تسجيلك بنجاح ^_^ ", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


////////////// update coordinates////////////////////////
                            Intent myIntent=   new Intent(SignUp.this, UserDetail.class);
                            Bundle extras = myIntent.getExtras();
                            extras.putString("email", user.getEmail());
                            extras.putString("uid", user.getUid());


                            startActivity(myIntent);
                            finish();
                        }
                        else {
                            Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("error is ", task.getException().getMessage());

                        }


                    }
                });

            }
        });


    }
}
