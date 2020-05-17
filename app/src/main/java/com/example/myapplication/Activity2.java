package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Converts meters to feet & inches. (ie 2 meters is 6 feet 6.74 inches)
 */
public class Activity2 extends AppCompatActivity {

    private static final double CENT_IN_INCHES = 2.54;
    private static final int CENT_IN_METER = 100;
    private static final int INCHES_IN_FEET = 12;
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
                double inches = CENT_IN_METER * Double.valueOf(editCent.getText().toString()) / CENT_IN_INCHES;
                int feet = (int) inches / INCHES_IN_FEET;
                inches = inches % INCHES_IN_FEET;
                String s = String.valueOf(feet) + " feet " + df.format(inches) + " inches";
                editFeet.setText(s);
            }
        });
    }
}
