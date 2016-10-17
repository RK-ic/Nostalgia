package com.example.gabekeyner.nostalgia;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.facebook.FacebookSdk;
import com.squareup.picasso.Picasso;


public class CameraActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_camera);
//
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        if (auth.getCurrentUser() != null) {
//            // already signed in
//        } else {
//            // not signed in
//        }
//
//        //Builds the sign with Smart Lock feature to remember user credentials
//        startActivityForResult(
//                AuthUI.getInstance()
//                        .createSignInIntentBuilder()
//                        .setIsSmartLockEnabled(false)
//                        .setProviders(
//                                AuthUI.FACEBOOK_PROVIDER)
////                        .setTheme(R.style.) will use for color customization
//                        .build(),
//                RC_SIGN_IN);

        ImageView imageView = (ImageView) findViewById(R.id.cameraImageView);

        Intent intent = getIntent();
        Uri imageUri = intent.getData();
        Picasso.with(this).load(imageUri).into(imageView);
    }
}







