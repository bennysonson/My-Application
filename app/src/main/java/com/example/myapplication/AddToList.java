package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddToList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_list);

        final EditText entry = findViewById(R.id.entryText);

        final EditText entry2 = findViewById(R.id.entryText2);

        Button addButton = findViewById(R.id.addButton);

        Button addButton2 = findViewById(R.id.addButton2);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("entry", entry.getText().toString());
                setResult(RESULT_OK, i);
                finish();
            }
        });

        addButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent();
                i2.putExtra("entry", entry.getText().toString());
                i2.putExtra("position", entry2.getText().toString());
                setResult(RESULT_OK, i2);
                finish();
            }
        });
    }


}
