package com.example.gabekeyner.nostalgia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gabekeyner.nostalgia.DatabaseActivitys.Post;
import com.facebook.FacebookSdk;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CameraActivity extends AppCompatActivity implements View.OnClickListener {

    //Activity Intents for Button declarations
    public static final String ACTIVITY_INTENTION = "Activity Intention";
    public static final String TAKE_PHOTO = "Take Photo";
    public static final String GALLERY_PICKER = "Gallery Picker";
    public static final String VIDEO_SHOOTER = "Video Shooter";
    public static final String GALLERY_VIDEO_PICKER = "Gallery Video Picker";

    //Request Codes for Media Capturing/Gallery
    public static final int REQUEST_TAKE_PHOTO = 0;
    public static final int REQUEST_TAKE_VIDEO = 1;
    public static final int REQUEST_PICK_PHOTO = 2;
    public static final int REQUEST_PICK_VIDEO = 3;
    public static final int MEDIA_TYPE_IMAGE = 4;
    public static final int MEDIA_TYPE_VIDEO = 5;

    private static final String TAG = CameraActivity.class.getCanonicalName();


    private String filepath;
    private EditText mTitle;
    private FloatingActionButton postBtn;
    private ImageView mImageView;
    private Uri mMediaUri;

    private String imageURL;
    private String title;

    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference listRef = mRootRef.child("post");


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_camera);


        databaseReference = FirebaseDatabase.getInstance().getReference();


        mTitle = (EditText) findViewById(R.id.editText);
        mImageView = (ImageView) findViewById(R.id.cameraImageView);
//        mVideoView = (VideoView) findViewById(R.id.cameraVideoView);

        postBtn = (FloatingActionButton) findViewById(R.id.post_button);
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO Handles the post message to the Realtime Database[in process]
                Post message = new Post(imageURL, title);
                FireBaseReadWrite fb = new FireBaseReadWrite().writeFirebase(mTitle.getText().toString(), "");


            }
        });


//        public void onResume() {
//            super.onResume();  // Always call the superclass method first
//
//
//        }

        //check flag variable
        Bundle extras = getIntent().getExtras();
        //handles the intent passed from the Main Activity using String ACITVITY_TRANSITION
        if (extras.containsKey(ACTIVITY_INTENTION)) {
            //TAKE PHOTO
            if (extras.getString(ACTIVITY_INTENTION).equals(TAKE_PHOTO)) {
                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mMediaUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);
                startActivityForResult(takePhotoIntent, REQUEST_TAKE_PHOTO);

                //PICK FROM GALLERY
            } else if (extras.getString(ACTIVITY_INTENTION).equals(GALLERY_PICKER)) {
                Intent pickPhotoIntent = new Intent(Intent.ACTION_GET_CONTENT);
                pickPhotoIntent.setType("image/*");
                startActivityForResult(pickPhotoIntent, REQUEST_PICK_PHOTO);

                //TAKE VIDEO
            } else if (extras.getString(ACTIVITY_INTENTION).equals(VIDEO_SHOOTER)) {
                Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                mMediaUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
                takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);
                takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 15);
                startActivityForResult(takeVideoIntent, REQUEST_TAKE_VIDEO);

                //PICK FROM VIDEO GALLERY
            } else if (extras.getString(ACTIVITY_INTENTION).equals(GALLERY_VIDEO_PICKER)) {
                Intent pickVideoIntent = new Intent(Intent.ACTION_GET_CONTENT);
                pickVideoIntent.setType("video/*");
                startActivityForResult(pickVideoIntent, REQUEST_PICK_VIDEO);
            }
        }
    }


    public Uri getOutputMediaFileUri(int mediaType) {
        //check for external storage
        if (isExternalStorageAvailable()) {
            // get the URI
            File mediaStorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            String fileName = "";
            String fileType = "";
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            if (mediaType == MEDIA_TYPE_IMAGE) {
                fileName = "IMG_" + timeStamp;
                fileType = ".jpg";
            } else if (mediaType == MEDIA_TYPE_VIDEO) {
                fileName = "VID_" + timeStamp;
                fileType = ".mp4";
            } else {
                return null;
            }
            File mediaFile;
            try {
                mediaFile = File.createTempFile(fileName, fileType, mediaStorageDir);
                filepath = mediaFile.getAbsolutePath();
                Log.i(TAG, "File: " + Uri.fromFile(mediaFile));

                return Uri.fromFile(mediaFile);
            } catch (IOException e) {
                Log.e(TAG, "Error creating file: " + mediaStorageDir.getAbsolutePath() + fileName + fileType);
            }
        }
        // something went wrong
        return null;
    }

    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {

                //CREATES FILE FOR THE IMAGE
                File file = new File(filepath);
                if (file.exists()) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    mImageView.setImageBitmap(bitmap);
                }
//                    StorageReference filePath = mStorage.child("Photos").child(mMediaUri.getLastPathSegment());
//                    filePath.putFile(mMediaUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Toast.makeText(CameraActivity.this, "Memory Uploaded", Toast.LENGTH_SHORT).show();
//                        }
//                    });
            } else if (requestCode == REQUEST_PICK_PHOTO) {
                if (data != null) {
                    mMediaUri = data.getData();
                    mImageView.setImageURI(mMediaUri);
                }


            } else if (requestCode == REQUEST_TAKE_VIDEO) {

                //CREATES FILE FOR THE VIDEO
                File file = new File(filepath);
                if (file.exists()) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    mImageView.setImageBitmap(bitmap);
                }

            } else if (resultCode == REQUEST_PICK_VIDEO) {
                if (data != null) {
                    mMediaUri = data.getData();
                    mImageView.setImageURI(mMediaUri);
                }
            }
        } else if (resultCode != RESULT_CANCELED)

        {
            Toast.makeText(this, "Sorry, there was an error!", Toast.LENGTH_LONG).show();
        }

    }

    private void postInformation() {
        String imageURL = mImageView.getContext().toString();
        String title = mTitle.getText().toString().trim();

//        mMediaUri

        Post post = new Post(imageURL, title);
        databaseReference.child(post.getTitle().toString()).setValue(post);

        StorageReference filePath = storageReference.child("Photos").child(mMediaUri.getLastPathSegment());
        filePath.putFile(mMediaUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(CameraActivity.this, "Memory Uploaded", Toast.LENGTH_SHORT).show();
            }
        });
//        databaseReference.child(post.getImageURL()).setValue(post);

        Toast.makeText(this, "Upload Successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        postInformation();
    }

}







