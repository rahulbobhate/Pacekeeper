package com.example.parikshitt23.speedcalculator;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class MusicActivity extends AppCompatActivity {

    ListView songsList;
    String[] items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        songsList = (ListView)findViewById(R.id.listView);
        ArrayList<File> mySongs = findSongs(Environment.getExternalStorageDirectory());
        items = new String[mySongs.size()];
        for(int i =0 ; i < mySongs.size(); i++){
            items[i] = mySongs.get(i).getName().toString().replace(".mp3","");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,items);
        songsList.setAdapter(adapter);
    }
    public ArrayList<File> findSongs(File root){
        ArrayList<File> a1 = new ArrayList<>();
        File[] files = root.listFiles();
        for(File singleFile : files){
            if(singleFile.isDirectory()){
                a1.addAll(findSongs(singleFile));
            }else{
                if(singleFile.getName().endsWith(".mp3")){
                    a1.add(singleFile);
                }
            }
        }

        return a1;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_music, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
