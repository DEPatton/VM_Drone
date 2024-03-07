package com.example.vm_drone;

import static android.content.ContentValues.TAG;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;








public class GPS extends Fragment implements LongitudeCallback, LatitudeCallback, OnMapReadyCallback {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference mapDoc;
    private int Latitude;
    private int Longitude;

    private final Activity activity = getActivity();
    private GoogleMap _map;

    FrameLayout map;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Remove the super.onCreate(savedInstanceState) from onCreateView;

        //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        View view = inflater.inflate(R.layout.gps_layout, container, false);
        this.map = view.findViewById(R.id.map);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Get the SupportMapFragment and request notification when the map is ready to be used.

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {

        //Todo map feature is done just make it dynamic


        //mapView = view.findViewById(R.id.mapView);



        mapDoc = db.collection("GPS").document("GPS-data");
        SetFireBaseLatitude(50);
        SetFirebaseLongitude(30);



        addMarker(50,40);



        //SetLongitude((String) mapData.get("longitude"));
        //SetLatitude((String) mapData.get("latitude"));

        //gpsLoad.findViewById(R.id.GpsLoad);
        //GetFireBaseLongitude(longitude -> SetLongitude(longitude));
        //GetFirebaseLatitude(latitude -> SetLatitude(latitude));

    }

    public void addMarker(int longitude, int latitude)
    {
        /*
        LatLng DronePos = new LatLng(latitude, longitude);
        this._map.addMarker(new MarkerOptions()
                .position(DronePos)
                .title("Drone"));
         */
    }


    @Override
    public void onStart() {

        super.onStart();
    }



    //updates longitude in firebase value based on the value given
    public void SetFirebaseLongitude(int longitude)
    {
        SetLongitude(longitude);
        GetMapDoc().update("longitude",longitude)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully updated!"))
                .addOnFailureListener(e -> Log.w(TAG, "Error updating document", e));
    }

    //updates latitude in firebase value based on the value given
    public void SetFireBaseLatitude(Integer latitude)
    {
        SetLatitude(latitude);
        GetMapDoc().update("latitude",latitude)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully updated!"))
                .addOnFailureListener(e -> Log.w(TAG, "Error updating document", e));
    }
    public void SetLatitude(int latitude)
    {
        this.Latitude = latitude;
    }
    public void SetLongitude(int longitude)
    {
        this.Longitude = longitude;
    }
    public int GetLatitude()
    {
        return Latitude;
    }

    public int GetLongitude()
    {
        return Longitude;
    }

    public DocumentReference GetMapDoc()
    {
        return mapDoc;
    }

    public void GetFireBaseLongitude(final LongitudeCallback callback)
    {
        GetMapDoc().get().addOnSuccessListener(documentSnapshot -> {
            // Retrieve the longitude from the documentSnapshot
            Integer longitude = 0;
            if (documentSnapshot.exists()) {
                longitude = (Integer) documentSnapshot.get("longitude");
                Log.d(TAG, "DocumentSnapshot data: " + documentSnapshot.getData());
            } else {
                Log.d(TAG, "No such document");
            }
            // Call the callback method with the retrieved longitude
            callback.onLongCallback(longitude);
        });
    }

    public void GetFirebaseLatitude(final LatitudeCallback callback)
    {
        GetMapDoc().get().addOnSuccessListener(documentSnapshot ->
        {
            // Retrieve the longitude from the documentSnapshot
            int latitude = 0;
            if (documentSnapshot.exists()) {
                latitude = (Integer) documentSnapshot.get("longitude");
                Log.d(TAG, "DocumentSnapshot data: " + documentSnapshot.getData());
            } else {
                Log.d(TAG, "No such document");
            }
            // Call the callback method with the retrieved longitude
            callback.onLatCallback(latitude);
        });
    }
    @Override
    public void onLongCallback(int longitude)
    {
        this.Longitude = longitude;
    }
    @Override
    public void onLatCallback(int longitude)
    {
        this.Longitude = longitude;
    }

    public GoogleMap get_map() {
        return _map;
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap)
    {
        this._map = googleMap;
        LatLng florida = new LatLng(28.578216, -81.308165);
        this._map.addMarker(new MarkerOptions()
                .position(florida)
                .title("Marker in Sydney"));
        this._map.moveCamera(CameraUpdateFactory.newLatLng(florida));
    }
}

