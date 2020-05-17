package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
        }
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
            case R.id.removeOption:
                Toast.makeText(this, "Remove an item from the list", Toast.LENGTH_SHORT).show();
                Intent i3 = new Intent(this, RemoveFromList.class);
                startActivityForResult(i3, 3);
                return true;
            case R.id.changeTitleOption:
                Intent i2 = new Intent(this, ChangeTitle.class);
                startActivityForResult(i2, 2);
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
                    try {
                        int position = Integer.valueOf(dataIntent.getStringExtra("position"));
                        ListEntry entry = new ListEntry(position, dataIntent.getStringExtra("entry"));
                        list.add(position - 1, entry);
                        for (int i = position; i < list.size(); i++) {
                            list.get(i).setRank(i + 1);
                        }
                    } catch (Exception e) {
                        ListEntry entry = new ListEntry(list.size() + 1, dataIntent.getStringExtra("entry"));
                        list.add(entry);
                    }
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
            //Load selected file
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
                break;
            case 3:
                if (resultCode == RESULT_OK) {
                    int removeIndex = Integer.valueOf(dataIntent.getStringExtra("removeChoice")) - 1;
                    list.remove(removeIndex);
                    for (int i = removeIndex; i < list.size(); i++) {
                        list.get(i).setRank(i + 1);
                    }
                    entryText.setText(sbToString());
                }
                break;
        }
    }

    private void readText(String input) {
        list = new ArrayList<>();
        title = input.substring(0, input.lastIndexOf('.'));
        titleText.setText(title);
        try {
            File sdcard = Environment.getExternalStorageDirectory();
            File file = new File(sdcard, input);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            Scanner scan = null;
            while ((line = br.readLine()) != null) {
                scan = new Scanner(line);
                scan.useDelimiter("\\. ");
                int rank = scan.nextInt();
                String entry = scan.next();
                ListEntry e = new ListEntry(rank, entry);
                list.add(e);
            }
            if (scan != null) {
                scan.close();
            }
            entryText.setText(sbToString());
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "FileNotFoundException", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(this, "IOException", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void performFileSearch(MenuItem item) {
        Intent i = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        }
        assert i != null;
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("text/*");
        startActivityForResult(i, 42);
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
        if (isExternalStorageWritable() && checkPermission()) {
            File textFile = new File(Environment.getExternalStorageDirectory().getPath(), title + ".txt");
            FileOutputStream fos;
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

    private boolean checkPermission() {
        int check = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return (check == PackageManager.PERMISSION_GRANTED);
    }

    public StringBuilder sbToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).toString());
        }
        return sb;
    }

    private static class ListEntry {

        private String entry;
        private int rank;
        private ListEntry(int rank, String entry) {
            this.entry = entry;
            this.rank = rank;
        }

        void setRank(int i) {
            this.rank = i;
        }

        @Override
        public String toString() {
            return rank + ". " + entry + "\n";
        }
    }

//    public void save(MenuItem item) {
//        String text = entryText.getText().toString();
//        FileOutputStream fos = null;
//        try {
//            fos = openFileOutput(title, MODE_PRIVATE);
//            fos.write(text.getBytes());
//            Toast.makeText(this, "Saved to" + getFilesDir() + "/" + title, Toast.LENGTH_LONG).show();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (fos != null) {
//                try {
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    /*
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
     */
}
