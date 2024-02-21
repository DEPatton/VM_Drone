package com.example.vm_drone;

import static android.content.ContentValues.TAG;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.maps.MapView;
import com.mapbox.maps.MapboxMap;
import com.mapbox.maps.MapboxStyleManager;
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource;
import com.mapbox.maps.plugin.Plugin;
import com.mapbox.maps.plugin.annotation.Annotation;
import com.mapbox.maps.plugin.annotation.AnnotationConfig;
import com.mapbox.maps.plugin.annotation.AnnotationManager;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import static com.mapbox.maps.plugin.locationcomponent.LocationComponentUtils.getLocationComponent;

import com.mapbox.maps.plugin.annotation.AnnotationType;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.delegates.MapDelegateProvider;



/*
import com.mapbox.mapboxsdk.annotations.BaseMarkerOptions;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.plugins.annotation.Symbol;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.maps.MapView;
import com.mapbox.maps.MapboxMap;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;

*/

//import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;




public class GPS extends Fragment implements LongitudeCallback, LatitudeCallback {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference mapDoc;
    private String Latitude;
    private String Longitude;

    private final Activity activity = getActivity();

    MapView mapView;
    //private SymbolManager symbolManager;
    private MapboxMap mapboxMap;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Remove the super.onCreate(savedInstanceState) from onCreateView
        // It's not necessary and could cause issues
        // super.onCreate(savedInstanceState);

        //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        View view = inflater.inflate(R.layout.gps_layout, container, false);

        // Example of finding a view within the inflated layout
        // Note: Uncomment the line below if you need to find a view by ID
        // TextView helloWorldTextView = view.findViewById(R.id.HelloWorld);

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {

        //Todo Should have a map point feature implemented and firebase data shows the map point


        mapView = view.findViewById(R.id.mapView);




        mapDoc = db.collection("GPS").document("GPS-data");
        SetFireBaseLatitude("50 N");
        SetFirebaseLongitude("30 W");


        addMarker(50,40);



        //SetLongitude((String) mapData.get("longitude"));
        //SetLatitude((String) mapData.get("latitude"));

        //gpsLoad.findViewById(R.id.GpsLoad);
        //GetFireBaseLongitude(longitude -> SetLongitude(longitude));
        //GetFirebaseLatitude(latitude -> SetLatitude(latitude));

    }

    public void addMarker(int longitude, int latitude)
    {


    }


    @Override
    public void onStart() {

        super.onStart();
    }



    //updates longitude in firebase value based on the value given
    public void SetFirebaseLongitude(String longitude)
    {
        SetLongitude(longitude);
        GetMapDoc().update("longitude",longitude)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully updated!"))
                .addOnFailureListener(e -> Log.w(TAG, "Error updating document", e));
    }

    //updates latitude in firebase value based on the value given
    public void SetFireBaseLatitude(String latitude)
    {
        SetLatitude(latitude);
        GetMapDoc().update("latitude",latitude)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully updated!"))
                .addOnFailureListener(e -> Log.w(TAG, "Error updating document", e));
    }
    public void SetLatitude(String latitude)
    {
        this.Latitude = latitude;
    }
    public void SetLongitude(String longitude)
    {
        this.Longitude = longitude;
    }
    public String GetLatitude()
    {
        return Latitude;
    }

    public String GetLongitude()
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
            String longitude = null;
            if (documentSnapshot.exists()) {
                longitude = (String) documentSnapshot.get("longitude");
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
        GetMapDoc().get().addOnSuccessListener(documentSnapshot -> {
            // Retrieve the longitude from the documentSnapshot
            String latitude = null;
            if (documentSnapshot.exists()) {
                latitude = (String) documentSnapshot.get("longitude");
                Log.d(TAG, "DocumentSnapshot data: " + documentSnapshot.getData());
            } else {
                Log.d(TAG, "No such document");
            }
            // Call the callback method with the retrieved longitude
            callback.onLatCallback(latitude);
        });
    }
    @Override
    public void onLongCallback(String longitude)
    {
        this.Longitude = longitude;
    }
    @Override
    public void onLatCallback(String longitude)
    {
        this.Longitude = longitude;
    }

}

