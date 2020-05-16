package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(this, "Item 1 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                Toast.makeText(this, "Converter selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subItem1:
                Toast.makeText(this, "Meters to Imperial selected", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(MainActivity.this, Activity2.class);
                startActivity(myIntent);
                return false;
            case R.id.subItem2:
                Toast.makeText(this, "Kilograms to Pounds selected", Toast.LENGTH_SHORT).show();
                Intent myIntent2 = new Intent(MainActivity.this, KGToPounds.class);
                startActivity(myIntent2);
                return false;
            case R.id.item3:
                Toast.makeText(this, "Piano selected", Toast.LENGTH_SHORT).show();
                Intent myIntent3 = new Intent(MainActivity.this, PianoActivity.class);
                startActivity(myIntent3);
                return true;
            case R.id.item4:
                Toast.makeText(this, "List Maker selected", Toast.LENGTH_SHORT).show();
                Intent myIntent4 = new Intent(MainActivity.this, ListMaker.class);
                startActivity(myIntent4);
                return true;
             default:
                return super.onOptionsItemSelected(item);

        }
    }
}
