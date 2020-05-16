package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class ListMaker extends AppCompatActivity {

    private ArrayList<ListEntry> list;
    private String title;
    private TextView titleText;
    private TextView entryText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_maker);
        list = new ArrayList<>();
        title = "My List";
        titleText = findViewById(R.id.titleText);
        entryText = findViewById(R.id.entryText);
        titleText.setText(title);
  //      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    //            != PackageManager.PERMISSION_GRANTED) {
     //       requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
     //   }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.listmaker_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addOption:
                Toast.makeText(this, "Add an item to the list", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, AddToList.class);
                startActivityForResult(i, 1);
                return true;
            case R.id.changeTitleOption:
                Intent i2 = new Intent(this, ChangeTitle.class);
                startActivityForResult(i2, 2);
                return true;
            case R.id.loadOption:
                Intent i3 = new Intent(this, LoadList.class);
                startActivityForResult(i3, 3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent dataIntent) {
        super.onActivityResult(requestCode, resultCode, dataIntent);
        switch (requestCode) {
            //Add entry to list
            case 1:
                if (resultCode == RESULT_OK) {
                    ListEntry e = new ListEntry(list.size() + 1, dataIntent.getStringExtra("entry"));
                    list.add(e);
                    entryText.setText(sbToString());
                }
                break;
            //Change title
            case 2:
                if (resultCode == RESULT_OK) {
                    title = dataIntent.getStringExtra("title");
                    titleText.setText(title);
                }
                break;
            case 42:
                if (resultCode == RESULT_OK) {
                    if (dataIntent != null) {
                        Uri uri = dataIntent.getData();
                        assert uri != null;
                        String path = uri.getPath();
                        assert path != null;
                        path = path.substring(path.indexOf(":") + 1);
                        Toast.makeText(this, "" + path, Toast.LENGTH_SHORT).show();
                        readText(path);
                    }
                }
        }
    }

    public StringBuilder sbToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).toString());
        }
        return sb;
    }

    private void readText(String input) {
        File file = new File(input);
        list = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            int i = 0;
            Scanner scan = null;
            while ((line = br.readLine()) != null) {
                scan = new Scanner(line);
                scan.useDelimiter("\\. {2}");
                int rank = scan.nextInt();
                String entry = scan.next();
                ListEntry e = new ListEntry(rank, entry);
                list.add(e);
                i++;
            }
            if (scan != null) {
                scan.close();
            }
            entryText.setText(sbToString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void performFileSearch(MenuItem item) {
        Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("text/*");
        startActivityForResult(i, 42);
    }


    public void load(MenuItem item) {
        FileInputStream fis = null;
        list = new ArrayList<>();
        try {
            fis = openFileInput(title);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            int i = 0;
            Scanner scan = null;
            while ((text = br.readLine()) != null) {
                scan = new Scanner(text);
                scan.useDelimiter("\\. {2}");
                int rank = scan.nextInt();
                String entry = scan.next();
                ListEntry e = new ListEntry(rank, entry);
                list.add(e);
                i++;
            }
            if (scan != null) {
                scan.close();
            }
            entryText.setText(sbToString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean isExternalStorageWritable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Toast.makeText(this, "Storage is writable", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return false;
        }
    }

    public void writeFile(MenuItem item) {
        if (isExternalStorageWritable() && checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            File textFile = new File(Environment.getExternalStorageDirectory(), title + ".txt");
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(textFile);
                fos.write(sbToString().toString().getBytes());
                fos.close();
                Toast.makeText(this, "File Saved", Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Cannot Write to External Storage", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPermission(String permission) {
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }

    private static class ListEntry {

        private String entry;
        private int rank;
        private ListEntry(int rank, String entry) {
            this.entry = entry;
            this.rank = rank;
        }

        @Override
        public String toString() {
            return rank + ". " + entry + "\n";
        }
    }

    public void save(MenuItem item) {
        String text = entryText.getText().toString();
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(title, MODE_PRIVATE);
            fos.write(text.getBytes());
            Toast.makeText(this, "Saved to" + getFilesDir() + "/" + title, Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
