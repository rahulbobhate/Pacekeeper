package com.example.parikshitt23.speedcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.NumberPicker;
import android.widget.TextView;

public class DemoActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        NumberPicker np = (NumberPicker)findViewById(R.id.numberPicker1);
        np.setMinValue(1);// restricted number to minimum value i.e 1
        np.setMaxValue(20);// restricked number to maximum value i.e. 31
        np.setWrapSelectorWheel(true);
        np.setValue(10);

        final TextView demoSpeed = (TextView)this.findViewById(R.id.speedDemo);
        demoSpeed.setText("Speed : 10 m/s");
        final float[] pace = {(float) (10 / 0.44704)};
        pace[0] = (float) 60/pace[0];
        final TextView demoPace = (TextView)this.findViewById(R.id.paceDemo);
        demoPace.setText("Pace is " + pace[0] + " minutes per mile");

        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                // TODO Auto-generated method stub

                demoSpeed.setText("Speed : " + newVal + " m/s");
                pace[0] = (float) (newVal / 0.44704);
                pace[0] = (float) 60/pace[0];
                demoPace.setText("Pace is " + pace[0] + " minutes per mile");


            }
        });

        Log.d("NumberPicker", "NumberPicker");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_demo, menu);
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
