package com.example.sensorproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorEventListener2;
import android.os.Bundle;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    SensorManager SensorMng;
    Sensor sensor;
    TextView text_value,text_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorMng = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor = SensorMng.getDefaultSensor(Sensor.TYPE_LIGHT);

        text_value =(TextView)findViewById(R.id.text_value);
        text_list = (TextView)findViewById(R.id.SensorList);

        List<Sensor> sensorList = SensorMng.getSensorList(Sensor.TYPE_ALL);
        StringBuilder strBuilder = new StringBuilder();

        for(Sensor s: sensorList){
            strBuilder.append(s.getName()+ "\n" );
        }

        //For ile tüm sensorListelerinde dönüldükten sonra; bu listelerdekini text_liste bas.

        //setVisibilty görünür yapması.
        text_list.setVisibility(View.VISIBLE);
        text_list.setText(strBuilder);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SensorMng.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT){
            text_value.setText(" Light Value :  "+ event.values[0]);
            if (event.values[0] < 5000){
                text_list.setTextColor(Color.YELLOW);
            }
            if (event.values[0] > 5000 && event.values[0] < 10000){
                text_list.setTextColor(Color.BLUE);
            }
            if (event.values[0] > 10000 && event.values[0] < 30000) {
                text_list.setTextColor(Color.GREEN);
            }
            if (event.values[0] > 30000){
                text_list.setTextColor(Color.RED);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SensorMng.unregisterListener(this);
    }

}
