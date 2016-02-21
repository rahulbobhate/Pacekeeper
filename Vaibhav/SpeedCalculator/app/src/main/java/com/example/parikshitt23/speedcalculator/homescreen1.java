package com.example.parikshitt23.speedcalculator;

import android.os.Bundle;
import android.app.Activity;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class homescreen1 extends Activity {

    private static SeekBar seek_Bar;
    private static TextView text_view;
    private static TextView text_view1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen1);
        seekbar();
    }

    public void seekbar(){
        seek_Bar=(SeekBar)findViewById(R.id.seekBar);
        text_view=(TextView)findViewById(R.id.textView);
        text_view1=(TextView)findViewById(R.id.textView2);
        //text_view.setText("Running pace is " + seek_Bar.getProgress() + " / " + seek_Bar.getMax());
        text_view.setText("Starting the run . . .");
        text_view1.setText("Let's get this done!");
        //text_view.setText("Running pace is " + String.valueOf(((int)seek_Bar.getMax()*1.5)- (int)seek_Bar.getProgress()) + "%");

        seek_Bar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progress_value;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progress_value = progress;

                        text_view.setText("Running at " + String.valueOf(progress+50) + " % ");
                        if(progress_value>55){
                            text_view1.setText("Slow down, you'll burn out!");
                        }
                        else if(progress_value<45){
                            text_view1.setText("Giving up already? Speed up!");
                        }
                        else
                            text_view1.setText("Keep it up, you can do it!");


                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        text_view.setText("Running at " + String.valueOf(progress_value + 50) + " % ");

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        text_view.setText("Running at " + String.valueOf(progress_value + 50) + " % ");
                        //Toast.makeText(homescreen1.this, "Seekbar on stop tracking", Toast.LENGTH_LONG).show();
                        if(progress_value>55){
                            text_view1.setText("Slow down, you'll burn out!");
                        }
                        else if(progress_value<45){
                            text_view1.setText("Giving up already? Speed up!");
                        }
                        else
                            text_view1.setText("Keep it up, you can do it!");

                    }
                }

        );

    }
}
