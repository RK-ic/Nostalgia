package com.example.gabekeyner.nostalgia;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class CameraActivity extends AppCompatActivity {

    public static final String TAG = CameraActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        ImageView imageView = (ImageView)findViewById(R.id.cameraImageView);

        Intent intent = getIntent();
        Uri imageUri = intent.getData();
        Picasso.with(this).load(imageUri).into(imageView);


    }
}



