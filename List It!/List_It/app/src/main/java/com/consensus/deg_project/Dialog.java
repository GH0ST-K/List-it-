package com.consensus.deg_project;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Dialog extends AppCompatDialogFragment {
    private TextView fruit;
    private EditText quantity,price;
    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);
        quantity = view.findViewById(R.id.editQuantity);
        price = view.findViewById(R.id.editPrice);
        fruit = view.findViewById(R.id.fruit_name);
        SharedPreferences mySharedPreferences = getActivity().getSharedPreferences("MYPREFERENCENAME", Context.MODE_PRIVATE);
        String username = mySharedPreferences.getString("result", "");
        fruit.setText(username);
        builder.setView(view).setTitle("Insert").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                current_month.resultTextView2.setText("NA" + "," + fruit.getText() + "," + "FOOD" + "," + quantity.getText() + "," + price.getText());
            }
        });
        return builder.create();
    }

}
