package com.example.gabekeyner.nostalgia;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.gabekeyner.nostalgia.DatabaseActivitys.Post;
import com.facebook.FacebookSdk;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class CameraActivity extends AppCompatActivity {

    private EditText mTitle;
    private Button postBtn;
    private ImageView mImageView;
    private String imageURL;
    private String title;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference listRef = mRootRef.child("post");




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_camera);


        postBtn = (Button)findViewById(R.id.post_button);
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO Handles the post message to the Realtime Database[in process]
            Post message = new Post(imageURL,title);
                mTitle = (EditText)findViewById(R.id.editText);
                mImageView = (ImageView)findViewById(R.id.cameraImageView);
                FireBaseReadWrite fb = new FireBaseReadWrite().writeFirebase(mTitle.getText().toString(),"");


            }
        });


        ImageView imageView = (ImageView) findViewById(R.id.cameraImageView);

        Intent intent = getIntent();
        Uri imageUri = intent.getData();
        Picasso.with(this).load(imageUri).into(imageView);

        //changing the images into buffered images
        //BufferedImage img = ImageIO.read(getClass().getResource("/path/to/image"));
//
//        ByteArrayOutputStream output = new ByteArrayOutputStream();
//        ImageIO.write(image, "png", output);
//        DatatypeConverter.printBase64Binary(output.toByteArray());

//        byte[] imageBytes = IOUtils.toByteArray(new URL("...")));
//        String base64 = Base64.getEncoder().encodeToString(imageBytes);
    }
}







