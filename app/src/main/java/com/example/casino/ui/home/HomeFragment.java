package com.example.casino.ui.home;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.casino.R;

import java.util.Random;

public class HomeFragment extends Fragment implements SensorEventListener {
    private ImageView image1;
    private ImageView image2;
    private SensorManager sensorManager;
    private Sensor accel;
    private float[] rotationMatrix; //матрица поворота

    private float[] accelerometer;  //данные с акселерометра
    private float[] orientation;
    private float[] geomagnetism;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        image1=root.findViewById(R.id.bones1);
        image2=root.findViewById(R.id.bones2);
        sensorManager=(SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        accel=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_UI );
        rotationMatrix = new float[16];
        accelerometer = new float[3];
        orientation = new float[3];
        return root;
    }

    private void loadSensorData(SensorEvent event) {
        final int type = event.sensor.getType(); //определяем тип датчика
        if (type == Sensor.TYPE_ACCELEROMETER) { //если акселерометр
            accelerometer = event.values.clone();
        }
        if (type == Sensor.TYPE_MAGNETIC_FIELD) { //если геомагнитный датчик
            geomagnetism = event.values.clone();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        loadSensorData(event); // получаем данные с датчика
        SensorManager.getRotationMatrix(rotationMatrix, null, accelerometer,geomagnetism); //получаем матрицу поворота
        SensorManager.getOrientation(rotationMatrix, orientation);
        if(accelerometer[0]>=10) {
            Random random = new Random();
            switch (random.nextInt(3)) {
                case 0:
                    image1.setImageResource(R.drawable.five);
                    break;
                case 1:
                    image1.setImageResource(R.drawable.six);
                    break;
                case 2:
                    image1.setImageResource(R.drawable.four);
                    break;
            }
            switch (random.nextInt(3)) {
                case 0:
                    image2.setImageResource(R.drawable.five);
                    break;
                case 1:
                    image2.setImageResource(R.drawable.six);
                    break;
                case 2:
                    image2.setImageResource(R.drawable.four);
                    break;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}