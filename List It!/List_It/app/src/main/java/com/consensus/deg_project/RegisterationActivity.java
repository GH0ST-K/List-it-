package com.consensus.deg_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class RegisterationActivity extends AppCompatActivity {
Button btn,bck_btn;
EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        email = findViewById(R.id.input_email);
        btn = (Button)findViewById(R.id.email_next);
        bck_btn = findViewById(R.id.back_button);

        bck_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterationActivity.this,ActualMainActivity.class));
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean ans = validateemailaddress(email);
                String emailid = email.getText().toString();
                Intent intent = new Intent(RegisterationActivity.this,Registeration_password.class);
                intent.putExtra("email",emailid);
                if(ans)
                    startActivity(intent);
                else {
                    Toast.makeText(RegisterationActivity.this,"Invalid Email",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean validateemailaddress(EditText email){
        String emailInput = email.getText().toString();
        if(!emailInput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            Toast.makeText(RegisterationActivity.this,"Email Validated Successfully",Toast.LENGTH_LONG).show();
            return true;
        }
        else{
            return false;
        }
    }
}
