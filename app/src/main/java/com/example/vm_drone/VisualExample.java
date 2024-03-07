package com.example.vm_drone;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class VisualExample extends AppCompatActivity
{
    //private StorageReference storageRef;

    private StorageReference firstStorageRef;
    private StorageReference secondStorageRef;
    private StorageReference thirdStorageRef;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.visual_examples_layout);


        firstStorageRef = FirebaseStorage.getInstance().getReference().child("Example_Images/IMG_3632.jpeg");

        secondStorageRef = FirebaseStorage.getInstance().getReference().child("Example_Images/IMG_3633.jpeg");

        thirdStorageRef = FirebaseStorage.getInstance().getReference().child("Example_Images/IMG_3635.jpeg");


        //storage = FirebaseStorage.getInstance().getReference("Example_Images").getStorage();


        //StorageReference imageRef = storageRef.child("Example_Images/IMG_3632.jpeg");

        ImageView firstImageView = findViewById(R.id.First_Picture);
        ImageView secondImageView = findViewById(R.id.Second_Picture);
        ImageView thirdImageView = findViewById(R.id.Third_Picture);

        ImageButton imageButton = findViewById(R.id.visual_exam_back_button);

        imageButton.setOnClickListener(view -> finish());


        // Todo clean up this code but for now it works
        downloadAndDisplayImage(firstStorageRef,firstImageView,"IMG_3632");
        downloadAndDisplayImage(secondStorageRef,secondImageView,"IMG_3633");
        downloadAndDisplayImage(thirdStorageRef,thirdImageView,"IMG_3635");




        // Todo add pictures from firebase and display it using the image view inside of the scrollview
    }
    private void downloadAndDisplayImage(StorageReference storageRef, final ImageView imageView, String fileNamePrefix)
    {
        try {
            final File localFile = File.createTempFile(fileNamePrefix, "jpeg");
            storageRef.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
                Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                imageView.setImageBitmap(bitmap);
            }).addOnFailureListener(e -> {
                throw new RuntimeException(e);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
