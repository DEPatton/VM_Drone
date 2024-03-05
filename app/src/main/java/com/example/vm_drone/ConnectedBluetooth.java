package com.example.vm_drone;

import static com.example.vm_drone.ConnectBluetooth.handler;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Message;
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
    private byte[] buffer;

    private interface MessageConstants {
        int MESSAGE_READ = 0;
        int MESSAGE_WRITE = 1;
        int MESSAGE_TOAST = 2;

        // ... (Add other message types here as needed.)
    }

    public ConnectedBluetooth(BluetoothSocket socket)
    {
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



    //public String getReelFeelRead() {return humidity;}

    public String read()
    {

        // buffer to store messages received
        buffer = new byte[1024];
        int bytes = 0; //bytes returned from read()

        // Keep listening to the InputStream until an exception occurs.

        while (true)
        {
            try
            {
                // read from InputStream and update the valueRead value

                bytes = _InStream.read(buffer);
                valueRead = buffer.toString();
                //valueRead = String.valueOf(bytes);
                // Send the obtained bytes to the UI activity.
                Message readMsg = handler.obtainMessage(MessageConstants.MESSAGE_READ, bytes, -1, buffer);
                readMsg.sendToTarget();
                if(valueRead != "")
                {
                    break;
                }
            }
            catch (IOException e)
            {
                Log.d(TAG, "Input stream was disconnected", e);
                break;
            }
        }
        return valueRead;
    }
    public void write(byte[] string)
    {
        try
        {
            _OutStream.write(string);

            //Give the message to the fragment that will be using the write functionality

            Message writtenMsg = handler.obtainMessage(MessageConstants.MESSAGE_WRITE,-1,-1,string);
            writtenMsg.sendToTarget();
        }
        catch (IOException e)
        {
            Log.e(TAG,"Message could not be sent");

            //failure message back to the activity calling the method
            Message writeErrorMsg =
                    handler.obtainMessage(MessageConstants.MESSAGE_TOAST);
            Bundle bundle = new Bundle();
            bundle.putString("toast",
                    "Couldn't send data to the other device");
            writeErrorMsg.setData(bundle);
            handler.sendMessage(writeErrorMsg);

        }
    }

    // Call the method from the main Activity to shut down the connection.

    public void cancel()
    {
        try
        {
            _Socket.close();
        }
        catch (IOException e)
        {
            Log.e(TAG, "Could not close the connect socket", e);
        }
    }

}
