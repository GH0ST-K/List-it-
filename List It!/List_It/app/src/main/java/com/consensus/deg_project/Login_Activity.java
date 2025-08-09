package com.consensus.deg_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_Activity extends AppCompatActivity {
    Button bck_btn,login;
    EditText email,pass;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        bck_btn = findViewById(R.id.back_button4);
        bck_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login_Activity.this,ActualMainActivity.class));
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.login_email);
        pass = findViewById(R.id.login_password);
        login = findViewById(R.id.login_btn);
        FirebaseAuth.AuthStateListener mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = firebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    startActivity(new Intent(Login_Activity.this, DashboardActivity.class));
                }
            }
        };
        ;
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailid = email.getText().toString(),password=pass.getText().toString();
                if(!(emailid.isEmpty() && password.isEmpty())){
                    firebaseAuth.signInWithEmailAndPassword(emailid,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            startActivity(new Intent(Login_Activity.this,DashboardActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                                Toast.makeText(Login_Activity.this,"The Email or Password Does not Exist",Toast.LENGTH_LONG).show();
                            }
                    });
                }
            }
        });
    }
}