package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;

public class KGToPounds extends AppCompatActivity {

    private static DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_k_g_to_pounds);

        final EditText editKG = findViewById(R.id.cent);

        final EditText editPounds = findViewById(R.id.feet);

        Button buttonConvert = findViewById(R.id.button);

        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double pounds = Double.valueOf(editKG.getText().toString()) * 2.20462;
                String p = df.format(pounds) + " pounds";
                editPounds.setText(p);
            }
        });
    }
}
