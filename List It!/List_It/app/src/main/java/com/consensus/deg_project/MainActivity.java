package com.consensus.deg_project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mAuth.getCurrentUser() != null) {
                    startActivity(new Intent(MainActivity.this,DashboardActivity.class));
                    finish();
                }
                else{
                    startActivity(new Intent(MainActivity.this,ActualMainActivity.class));
                    finish();
                }
            }
        },3000);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
