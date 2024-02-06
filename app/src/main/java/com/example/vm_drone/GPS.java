package com.example.vm_drone;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mapbox.maps.MapView;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


public class GPS extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference mapDoc;
    private String Latitude;
    private String Longitude;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Remove the super.onCreate(savedInstanceState) from onCreateView
        // It's not necessary and could cause issues
        // super.onCreate(savedInstanceState);
        //MapView mapView = view.findViewById(R.id.mapView);

        View view = inflater.inflate(R.layout.gps_layout, container, false);

        // Example of finding a view within the inflated layout
        // Note: Uncomment the line below if you need to find a view by ID
        // TextView helloWorldTextView = view.findViewById(R.id.HelloWorld);

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {


                mapDoc = db.collection("GPS").document("GPS-data");
        //mapData.put("longitude", "137-67");
        //mapData.put("latitude", "678-09");

        //SetLongitude((String) mapData.get("longitude"));
        //SetLatitude((String) mapData.get("latitude"));
        GetFireBaseLongitude();
        GetFirebaseLatitude();

        String longitude = GetLongitude();

    }


    //updates longitude in firebase value based on the value given
    public void SetFirebaseLongitude(String longitude)
    {
        GetMapDoc().update("longitude",longitude)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully updated!"))
                .addOnFailureListener(e -> Log.w(TAG, "Error updating document", e));
    }

    //updates latitude in firebase value based on the value given
    public void SetFireBaseLatitude(String latitude)
    {
        GetMapDoc().update("latitude",latitude)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully updated!"))
                .addOnFailureListener(e -> Log.w(TAG, "Error updating document", e));
    }
    public void SetLatitude(String latitude)
    {
        Latitude = latitude;
    }
    public void SetLongitude(String longitude)
    {
        Longitude = longitude;
    }
    public String GetLatitude()
    {
        return Latitude;
    }

    public String GetLongitude()
    {
        return Longitude;
    }

    private DocumentReference GetMapDoc()
    {
        return mapDoc;
    }


    public void GetFireBaseLongitude()
    {
        GetMapDoc().get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>(){
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {
                    if (documentSnapshot.exists())
                    {
                        //SetLongitude((String) documentSnapshot.get("longitude"));
                        Log.d(TAG, "DocumentSnapshot data: " + documentSnapshot.getData());
                    }
                    else
                    {
                        Log.d(TAG, "No such document");
                    }
                }
        });
    }




    public void GetFirebaseLatitude()
    {
        GetMapDoc().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    SetLatitude((String) doc.get("latitude"));
                    if (doc.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + doc.getData());
                    }
                    else
                    {
                        Log.d(TAG, "No such document");
                    }
                }
                else
                {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }


}
