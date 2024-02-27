package com.example.vm_drone;

import android.bluetooth.BluetoothDevice;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ItemViewModel extends ViewModel {
    private final MutableLiveData<BluetoothDevice> selectedItem = new MutableLiveData<BluetoothDevice>();

    public void selectItem(BluetoothDevice bt)
    {
        selectedItem.setValue(bt);
    }

    public LiveData<BluetoothDevice> getSelectedItem() {
        return selectedItem;
    }
}
