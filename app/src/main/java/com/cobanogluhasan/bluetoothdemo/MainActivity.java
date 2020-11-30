package com.cobanogluhasan.bluetoothdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    BluetoothAdapter BA;
    Button turnOffButton;
    Button findDevices;
    Button viewPairedDevice;
    String message;
    ListView myListView;
    ArrayList arrayList;

    public void displayToast(String message) {

        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         myListView = findViewById(R.id.myListView);

        turnOffButton = findViewById(R.id.turnOff);
        findDevices = findViewById(R.id.findDevices);
        viewPairedDevice = findViewById(R.id.viewPairedDevice);

         arrayList = new ArrayList();


        BA = BluetoothAdapter.getDefaultAdapter();




        if(BA.isEnabled()) {
            message = "Bluetooth is turned on dude";
           displayToast(message);

        } else {
            Intent bluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(bluetoothIntent);
            if(BA.isEnabled()) {
                message = "Bluetooth is turned on dude";
                displayToast(message);
            }
        }





        turnOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BA.disable();
                        message = "Bluetooth is off";
                        displayToast(message);
            }
        });


        findDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                startActivity(intent);

                String device = BluetoothDevice.EXTRA_NAME;

                String  device1 = BluetoothDevice.EXTRA_DEVICE;

                Log.i(TAG, "onClick: device" +  device1 + device );
            }
        });



        viewPairedDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<BluetoothDevice> pairedDevices = BA.getBondedDevices();
                for (BluetoothDevice bluetoothDevice: pairedDevices) {

                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.select_dialog_item, arrayList);
                    myListView.setAdapter(adapter);

                    arrayList.add(bluetoothDevice.getName());
                    arrayList.add(bluetoothDevice.getAddress());
                    arrayList.add(bluetoothDevice.getUuids());
                }

            }
        });




    }
}