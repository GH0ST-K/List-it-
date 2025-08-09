package com.consensus.deg_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registeration_password extends AppCompatActivity {
Button btn,bck_btn;
ImageButton visibile;
EditText password;
TextView valid_check;
String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration_password);
        btn = findViewById(R.id.password_next);
        password = findViewById(R.id.input_password);
        email = getIntent().getStringExtra("email");
        bck_btn = findViewById(R.id.back_button2);
        bck_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registeration_password.this,RegisterationActivity.class));
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View view) {
                String pass = password.getText().toString();
                if (pass.length() > 8 && isValidPassword(pass)) {
                    Intent intent = new Intent(Registeration_password.this, Registeration_dob.class);
                    intent.putExtra("password", pass);
                    intent.putExtra("email", email);
                    if (!pass.isEmpty())
                        startActivity(intent);
                    else
                        Toast.makeText(Registeration_password.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Registeration_password.this, "Password Must Contain at least a number , a symbol and should be of length 8", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

}
