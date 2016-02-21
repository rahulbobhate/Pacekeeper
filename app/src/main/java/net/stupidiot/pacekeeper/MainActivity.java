package net.stupidiot.pacekeeper;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.net.rtp.AudioStream;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int SELECT_FILE_REQUEST_CODE = 1;

    private Button playButton;
    private Button increaseRate;
    private Button decreaseRate;
    private Button selectFileButton;
    private AssetManager assetManager;

    private SoundPool soundPool;

    int media = 0;
    float rate = 0.1f;
    boolean loaded = false;
    float volume = 20f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assetManager = getAssets();
        playButton = (Button) findViewById(R.id.play_button);
        increaseRate = (Button) findViewById(R.id.increase_rate_button);
        decreaseRate = (Button) findViewById(R.id.decrease_rate_button);
        selectFileButton = (Button) findViewById(R.id.select_file_button);

        //soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setFlags(AudioAttributes.FLAG_AUDIBILITY_ENFORCED)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();
        soundPool = new SoundPool.Builder().setAudioAttributes(attributes).setMaxStreams(10).build();

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener()
        {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                loaded = true;
                Toast.makeText(getApplicationContext(), "Song loaded", Toast.LENGTH_LONG).show();
            }
        });

        selectFileButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent selectFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
                selectFileIntent.setType("audio/*");
                startActivityForResult(selectFileIntent, SELECT_FILE_REQUEST_CODE);
            }
        });

        playButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                float actualVolume = (float) audioManager
                        .getStreamVolume(AudioManager.STREAM_MUSIC);
                float maxVolume = (float) audioManager
                        .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                volume = actualVolume / maxVolume;

                if (loaded && playButton.getText().toString().equals("Play"))
                {
                    soundPool.play(media, volume, volume, 1, 0, rate);
                    playButton.setText("Pause");
                }
                else if(playButton.getText().toString().equals("Pause"))
                {
                    soundPool.pause(media);
                    playButton.setText("Resume");
                }
                else if(playButton.getText().toString().equals("Resume"))
                {
                    soundPool.resume(media);
                    playButton.setText("Pause");
                }

            }
        });

        increaseRate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                rate += 0.1;
                soundPool.pause(media);
                soundPool.setRate(media, rate);
                soundPool.resume(media);
            }
        });

        decreaseRate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                rate -= 0.1;
                soundPool.pause(media);
                soundPool.setRate(media, rate);
                soundPool.resume(media);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);

        if(resultCode != RESULT_OK)
        {
            Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_LONG).show();
 //           getApplicationContext().grantUriPermission("net.stupidiot.pacekeeper", intent.getData(), Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        else
        {
            if(requestCode == SELECT_FILE_REQUEST_CODE)
            {
                selectFile(intent);
            }
        }
    }

    private void selectFile(Intent intent)
    {
        Log.d(TAG, "User picked file: " + intent.getData().getPath());
        loaded = false;
        try
        {
 //           media = soundPool.load(assetManager.openFd("Mozart_Nachtmusik.ogg"), 100);
            media = soundPool.load(_getRealPathFromURI(intent.getData()), 100);
            playButton.setText("Play");
            rate = 1f;
        }
        catch (Exception e)
        {
            Log.d(TAG, e.getMessage());
        }
    }

    private String _getRealPathFromURI(Uri contentUri)
    {
        Context context = getApplicationContext();
        String[] proj = { MediaStore.Audio.Media.DATA };
        CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        Log.d(TAG, cursor.getString(column_index));
        return cursor.getString(column_index);
    }

}