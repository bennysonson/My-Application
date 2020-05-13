package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Activity2 extends AppCompatActivity {

    private static DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        final EditText editCent = findViewById(R.id.cent);

        final EditText editFeet = findViewById(R.id.feet);

        Button buttonConvert = findViewById(R.id.button);

        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double inches = 100 * Double.valueOf(editCent.getText().toString()) / 2.54;
                int feet = (int) inches / 12;
                inches = inches % 12;
                String s = String.valueOf(feet) + " feet " + df.format(inches) + " inches";
                editFeet.setText(s);
            }
        });
    }
}
