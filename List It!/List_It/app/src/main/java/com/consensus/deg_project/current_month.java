package com.consensus.deg_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;
import org.tensorflow.lite.examples.classification.ClassifierActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;
public class current_month extends AppCompatActivity {

TextView title;
public ListView barcode_detail;
FloatingActionButton first_button,action,action2;
String sdata;
JSONObject perData = new JSONObject();
DatabaseReference databaseReference;
DatabaseReference databaseReferencecat;
FirebaseAuth firebaseAuth;


    String[] ans;
    public Boolean clicked = false;
    public static TextView resultTextView;
    public static TextView resultTextView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_month);
        final Animation from_Bottom = AnimationUtils.loadAnimation(this.getApplicationContext(),R.anim.from_bottom_anim);
        final Animation rotate_open = AnimationUtils.loadAnimation(this.getApplicationContext(),R.anim.rotate_open_anim);
        final Animation rotate_close = AnimationUtils.loadAnimation(this.getApplicationContext(),R.anim.rotate_close_anim);
        final Animation to_Bottom = AnimationUtils.loadAnimation(this.getApplicationContext(),R.anim.to_bottom_anim);
        title = findViewById(R.id.title_tv);
        barcode_detail =(ListView) findViewById(R.id.barcode_detail);
        title.setText(getIntent().getStringExtra("month"));
        resultTextView = findViewById(R.id.resultView);
        resultTextView2 = findViewById(R.id.resultView2);
        action = findViewById(R.id.bar_code_scan);
        first_button = findViewById(R.id.scanButton);
        action2 = findViewById(R.id.fruit_scan);
        firebaseAuth = firebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        //databaseReference2 = FirebaseDatabase.getInstance().getReference("Data");
        databaseReferencecat = FirebaseDatabase.getInstance().getReference("Users");
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(current_month.this,ScancodeActivity.class));
            }
        });
        action2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(current_month.this, ClassifierActivity.class));
            }
        });
        first_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddButtonPressed();
            }

            private void onAddButtonPressed() {
                setVisibility(clicked);
                setAnimation(clicked);
                if (!clicked) clicked = true;
                else clicked = false;
            }

            private void setVisibility(Boolean clicked) {
                if(!clicked){
                    action.setVisibility(View.VISIBLE);
                    action2.setVisibility(View.VISIBLE);
                }
                else{
                    action.setVisibility(View.INVISIBLE);
                    action2.setVisibility(View.INVISIBLE);
                }

            }

            private void setAnimation(Boolean clicked){
                if(!clicked){
                    action.setAnimation(from_Bottom);
                    action2.setAnimation(from_Bottom);
                    first_button.setAnimation(rotate_open);
                }
                else{
                    action.setAnimation(to_Bottom);
                    action2.setAnimation(to_Bottom);
                    first_button.setAnimation(rotate_close);
                }
            }
        });
        resultTextView2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                openDialog();
                sdata = resultTextView2.getText().toString();
                new SendRequest().execute();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        resultTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sdata = resultTextView.getText().toString();
                new SendRequest().execute();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        final ArrayList<String> dat = new ArrayList<>();
        final FirebaseUser users = firebaseAuth.getCurrentUser();
        String result = users.getEmail().replace(".","");
        final ArrayAdapter arrayAdapter = new ArrayAdapter(current_month.this, android.R.layout.simple_list_item_1,dat);
        barcode_detail.setAdapter(arrayAdapter);
        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(result).child(title.getText().toString()).child("Product");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dat.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    dat.add(snapshot.child("productname").getValue().toString());
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void openDialog() {
        Dialog dialog = new Dialog();
        dialog.show(getSupportFragmentManager(),"Dialog");
    }

    @SuppressLint("StaticFieldLeak")
    public class SendRequest extends AsyncTask<String, Void, String> {


        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try{

                //Enter script URL Here
                URL url = new URL("https://script.google.com/macros/s/AKfycbwqcykYsUeDtCNe8BeSmryO2gDvYppBMmhjRZ3GSGE2qwu7itFx/exec");

                JSONObject postDataParams = new JSONObject();

                //int i;
                //for(i=1;i<=70;i++)


                //    String usn = Integer.toString(i);

                //Passing scanned code as parameter

                postDataParams.put("sdata",sdata);
                Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, StandardCharsets.UTF_8));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer();
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return "false : " + responseCode;
                }
            }
            catch(Exception e){
                return "Exception: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {

            ans = result.split(",");
            final FirebaseUser users = firebaseAuth.getCurrentUser();
            if(users!=null) {
                String finaluser = users.getEmail();
                assert finaluser != null;
                String resultemail = finaluser.replace(".", "");
                product Product = new product(ans[0], ans[1], ans[2], ans[3], ans[4]);
                databaseReference.child(resultemail).child((String) title.getText()).child("Product").child(ans[0]).setValue(Product);
                databaseReferencecat.child(resultemail).child((String) title.getText()).child("ProductByCategory").child(ans[2]).child(ans[0]).setValue(Product);
            }
        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

}