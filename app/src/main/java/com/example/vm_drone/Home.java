package com.example.vm_drone;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import java.util.Set;
import java.util.UUID;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.zerokol.views.joystickView.JoystickView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Home extends Fragment
{
    private static final String TAG = "Greedy";
    private static final int REQUEST_BLUETOOTH_PERMISSIONS = 1; // You can use any unique integer value
    private static final int REQUEST_ENABLE_BT = 1;
    //We will use a Handler to get the BT Connection states
    public static Handler handler;

    private final static int ERROR_READ = 0; // used in bluetooth handler to identify message update
    BluetoothDevice arduinoBTModule = null;
    String holdHumidityValue;
    Activity activity;
    UUID arduinoUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //We declare a default UUID to create the global variable

    private JoystickView leftJoystick, rightJoystick;
    Settings settings = new Settings();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        activity = getActivity();
        //activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        View view = inflater.inflate(R.layout.main_menu_layout, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {

        AppCompatImageButton bluetooth_button = view.findViewById(R.id.bluetooth_connect);
        TextView height_value = view.findViewById(R.id.Height_value);
        leftJoystick = view.findViewById(R.id.Left_Stick);
        rightJoystick = view.findViewById(R.id.Right_Stick);
        if(settings.GetJoyState() != false)
        {
            long interval = 0;
            rightJoystick.setOnJoystickMoveListener(new JoystickView.OnJoystickMoveListener() {
                @Override
                public void onValueChanged(int angle, int power, int direction) {

                }
            },interval);

            leftJoystick.setOnJoystickMoveListener(new JoystickView.OnJoystickMoveListener() {
                @Override
                public void onValueChanged(int angle, int power, int direction) {

                }
            },interval);

        }




        BluetoothManager bluetoothManager = activity.getSystemService(BluetoothManager.class);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        //Display all the linked BT Devices
        bluetooth_button.setOnClickListener(view1 ->
        {
            //Check if the phone supports BT
            if (bluetoothAdapter == null)
            {
                // Device doesn't support Bluetooth
                Log.d(TAG, "Device doesn't support Bluetooth");
            }
            else
            {
                Log.d(TAG, "Device does support Bluetooth");
                //Check BT enabled. If disabled, we ask the user to enable BT

                //TODO: Fix the bug within this code {
                Intent enableBtIntent = null;
                if (!bluetoothAdapter.isEnabled())
                {
                    Log.d(TAG, "Bluetooth is disabled");
                    Context context = activity.getApplicationContext();
                    enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

                    //if user does not have the bluetooth permissions activated
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED)
                    {
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED ||
                                ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED ||
                                ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED)
                        {
                            // Request Bluetooth permissions
                            ActivityCompat.requestPermissions(activity, new String[]
                                    {
                                            Manifest.permission.BLUETOOTH,
                                            Manifest.permission.BLUETOOTH_ADMIN,
                                            Manifest.permission.BLUETOOTH_CONNECT
                                    }, REQUEST_BLUETOOTH_PERMISSIONS);
                        }
                        else
                        {
                            // Users should have the permissions
                            Log.d(TAG, "We have BT Permissions");
                            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                            Log.d(TAG, "Bluetooth is enabled now");
                        }

                    }
                    else
                    {
                        Log.d(TAG, "We have BT Permissions");
                        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                        Log.d(TAG, "Bluetooth is enabled now");
                    }

                }
                else
                {
                    Log.d(TAG, "Bluetooth is enabled");
                }
                // Todo }
                String btDevicesString = "";
                Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

                if (pairedDevices.size() > 0) {
                    // There are paired devices. Get the name and address of each paired device.
                    for (BluetoothDevice device : pairedDevices) {
                        String deviceName = device.getName();
                        String deviceHardwareAddress = device.getAddress(); // MAC address
                        Log.d(TAG, "deviceName:" + deviceName);
                        Log.d(TAG, "deviceHardwareAddress:" + deviceHardwareAddress);
                        //We append all devices to a String that we will display in the UI
                        btDevicesString = btDevicesString + deviceName + " || " + deviceHardwareAddress + "\n";
                        //If we find the HC 06 device (the Arduino BT module) exchanged for the raspberry pi for now
                        //We assign the device value to the Global variable BluetoothDevice
                        //We enable the button "Connect to HC 06 device"
                        if (deviceName.equals("HC-06")) {
                            Log.d(TAG, "HC-06 found");
                            arduinoUUID = device.getUuids()[0].getUuid();
                            arduinoBTModule = device;


                            //Toast message whenever the drone connects to the application
                            CharSequence text = "Drone connected";
                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(activity, text, duration).show();

                            //HC -06 Found, enabling the button to read results
                            //connectToDevice.setEnabled(true);
                        }
                        //btDevices.setText(btDevicesString);
                    }
                }
            }
            Log.d(TAG, "Button Pressed");
            RunBluetooth();
        });

    }

    // Create an Observable from RxAndroid
    //The code will be executed when an Observer subscribes to the the Observable


    final Observable<String> connectToBTObservable = Observable.create(emitter ->
    {

        Log.d(TAG, "Calling connectThread class");
        //Call the constructor of the ConnectThread class
        //Passing the Arguments: an Object that represents the BT device,
        // the UUID and then the handler to update the UI
        ConnectBluetooth connectThread = new ConnectBluetooth(arduinoBTModule, arduinoUUID, handler);
        connectThread.run();
        SystemClock.sleep(2000);

        //Check if Socket connected
        if (connectThread.getSocket().isConnected())
        {
            Log.d(TAG, "Calling ConnectedThread class");
            //The pass the Open socket as arguments to call the constructor of ConnectedThread
            ConnectedBluetooth _connectedThread = new ConnectedBluetooth(connectThread.getSocket());
            _connectedThread.run();
            if (_connectedThread.getValueRead() != null)
            {
                // If we have read a value from the Arduino
                // we call the onNext() function
                //This value will be observed by the observer
                emitter.onNext(_connectedThread.getValueRead());
                //realfeel = CalculateRealFeel(_connectedThread.getValueRead(),_connectedThread.getHumidityRead());
            }
            //We just want to stream 1 value, so we close the BT stream
            _connectedThread.cancel();
        }
        //SystemClock.sleep(5000); // simulate delay
        //Then we close the socket connection
        connectThread.cancel();
        //We could Override the onComplete function
        emitter.onComplete();

    });

    @SuppressLint("CheckResult")
    public void RunBluetooth()
    {
        //Todo Add functionality to receive things from bluetooth and to send things using bluetooth as well
        //edit.setText("");
        if (arduinoBTModule != null)
        {
            //We subscribe to the observable until the onComplete() is called
            //We also define control the thread management with
            // subscribeOn:  the thread in which you want to execute the action
            // observeOn: the thread in which you want to get the response
            connectToBTObservable.
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribeOn(Schedulers.io()).
                    subscribe(valueRead -> {
                        //valueRead returned by the onNext() from the Observable
                        //edit.setText(valueRead);
                        //Humidity.setText(holdHumidityValue);
                        //feelslike.setText(String.valueOf(realfeel));

                        //We just scratched the surface with RxAndroid
                    });

        }
    }
}

