package com.example.vm_drone;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;


public class ConnectBluetooth extends Thread {
    private final BluetoothSocket _socket;
    private static final String TAG = "Greedy";
    public static Handler handler;
    private final static int ERROR_READ = 0;

    @SuppressLint("MissingPermission")
    public ConnectBluetooth(BluetoothDevice device, UUID MY_UUID, Handler _handler) {
        // Use a temporary object that is later assigned to Socket
        // because Socket is Final

        BluetoothSocket HC_mod = null;
        handler = _handler;

        try {
            // Get a BluetoothSocket to connect with the given BluetoothDevice.
            // MY_UUID is the app's UUID string, also used in the server code.

            HC_mod = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
            Log.e(TAG, "Socket's create method failed", e);
        }
        _socket = HC_mod;
    }

    @SuppressLint("MissingPermission")
    public void run() {
        try {
            // Connect to the remote device through the socket. This call
            // until it succeeds or throws an exception
            _socket.connect();
        } catch (IOException connectException) {
            // Unable to connect; close the socket and return.
            handler.obtainMessage(ERROR_READ, "Unable to connect to the BT device").sendToTarget();
            Log.e(TAG, "connectException: " + connectException);
            try {
                _socket.close();
            } catch (IOException closeException) {
                Log.e(TAG, "could not close the client socket", closeException);
            }
            return;
        }


    }

    // Makes the thread stop and closes the client

    public void cancel() {
        try {
            _socket.close();
        } catch (IOException e) {
            Log.e(TAG, "Could not close the client socket", e);
        }
    }

    public BluetoothSocket getSocket() {
        return _socket;
    }


}
