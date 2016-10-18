package com.example.gabekeyner.nostalgia;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.facebook.FacebookSdk;
import com.squareup.picasso.Picasso;


public class CameraActivity extends AppCompatActivity {

    private EditText mTitle;
    private Button postBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_camera);

        //make a button called[post] set a click listeneer 174-176 inside there.
        postBtn = (Button)findViewById(R.id.post_button);
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mTitle = (EditText)findViewById(R.id.editText);
                FireBaseReadWrite fb = new FireBaseReadWrite().writeFirebase(mTitle.getText().toString(),"http://svp.org.uk/sites/default/files/images/friends.jpg");

            }
        });



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







