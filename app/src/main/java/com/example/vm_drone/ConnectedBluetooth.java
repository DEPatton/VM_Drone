package com.example.vm_drone;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ConnectedBluetooth extends Thread {
    private static final String TAG = "Greedy";
    private final BluetoothSocket _Socket;
    private final InputStream _InStream;
    private final OutputStream _OutStream;
    private String valueRead;

    private int timeOut = 0;

    private String humidity;

    public ConnectedBluetooth(BluetoothSocket socket) {
        _Socket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // Get the input and output streams; using temp objects because member streams are final.

        try {
            tmpIn = socket.getInputStream();
        } catch (IOException e) {
            Log.e(TAG, "Error occurred when creating input stream", e);
        }
        try {
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
            Log.e(TAG, "Error occurred when creating output stream", e);
        }

        _InStream = tmpIn;
        _OutStream = tmpOut;
    }

    public String getValueRead() {
        return valueRead;
    }

    public String getHumidityRead() {return humidity;}

    public void setCounter(int _counter)
    {
        timeOut = _counter;
    }
    public int getCounter()
    {
        return timeOut;
    }

    //public String getReelFeelRead() {return humidity;}

    public void run() {
        //for tmp-36 one value for buffer
        //for dht-11 two values for buffer to get humidity and temperature
        byte[] buffer = new byte[2];
        int bytes = 0; //bytes returned from read()
        int Readings = 0; // to control the number of reading from the Arduino

        // Keep listening to the InputStream until an exception occurs.
        // We just want to get 1 temperature reading from the Arduino
        int readSecondValue;

        try {
            if (_InStream.available() > 0) {
                while(bytes != 2) {
                    buffer[bytes] = (byte) _InStream.read();
                    bytes++;
                }
                Readings = buffer[0];
                //100 is the offset I reduced it to in arduino then we want to
                readSecondValue = buffer[1];
                //Log.e(TAG, readMessage);
                // Value to be read by the Observer streamed by the Observable

                valueRead = String.valueOf(Readings);

                humidity = readSecondValue + "%";
            } else {
                // Handle the case where no data is available or implement a timeout mechanism.
                while(_InStream.available() > 0)
                {
                    timeOut++;
                }
            }

        } catch (IOException e) {
            Log.d(TAG, "Input stream was disconnected", e);
        }
    }

    // Call the method from the main Activity to shut down the connection.

    public void cancel() {
        try {
            _Socket.close();
        } catch (IOException e) {
            Log.e(TAG, "Could not close the connect socket", e);
        }
    }
}
