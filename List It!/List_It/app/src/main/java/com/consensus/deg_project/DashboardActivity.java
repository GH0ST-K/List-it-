package com.consensus.deg_project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.util.Calendar;

public class DashboardActivity extends AppCompatActivity {
TextView name;
String personName,personGivenName,personFamilyName,personEmail,personId,namee;
Uri personPhoto;
Button month,sign_out;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(DashboardActivity.this);
        if (acct != null) {
            personName = acct.getDisplayName();
            personGivenName = acct.getGivenName();
            personFamilyName = acct.getFamilyName();
            personEmail = acct.getEmail();
            personId = acct.getId();
            personPhoto = acct.getPhotoUrl();
            name = findViewById(R.id.display_name);
            sign_out = findViewById(R.id.sign_out);
            Calendar calendar = Calendar.getInstance();


            if(personGivenName!=null) {
                name.setText(personGivenName);
            }
            else
            {
                name.setText("There");
            }
            month = findViewById(R.id.curr_month);
            String currentDate = DateFormat.getDateInstance(DateFormat.MONTH_FIELD).format(calendar.getTime());
            currentDate = currentDate.replaceAll("[0-9]","");
            currentDate= currentDate.replaceAll("-","");
            month.setText(currentDate);
            month.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DashboardActivity.this,current_month.class);
                    intent.putExtra("month",month.getText().toString());
                    startActivity(intent);
                }
            });
            sign_out.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(DashboardActivity.this,ActualMainActivity.class));
                    finish();
                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}