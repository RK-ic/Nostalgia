package com.example.gabekeyner.nostalgia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class CameraActivity extends AppCompatActivity {

    //Add progress bar   private ProgressBar mProgressBar;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int PICK_IMAGE_REQUEST = 1;
    private static final String TAG = CameraActivity.class.getName();
    private ImageView mImageView;
    private Button selectBtn;
    private Button takeBtn;
    String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        //casting the ImageView
        mImageView = (ImageView) findViewById(R.id.mImageView);

        mImageView.setImageResource(R.drawable.placeholder);



        //mProgressBar = (ProgressBar)findViewById(R.id.progress);
        // mProgressBar.setMax(100);
        selectBtn = (Button) findViewById(R.id.select_image);
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grabFromGallery();
            }
        });
        takeBtn = (Button)findViewById(R.id.take_image);
        takeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

    }

    public File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = "JPEG_" + "_";
        File storageDir =  getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();


        return image;
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    public void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(CameraActivity.this, "No SD card on this device", Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);


            }
        }
    }
    private void setPic() {
        ImageView mImageView = (ImageView)findViewById(R.id.mImageView);

        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

        mImageView.setImageBitmap(bitmap);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            setPic();

        }
    }

    //Adds the photo to a Gallery

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }


  //select from gallery
    private void grabFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //TODO: Fill in the parameter types
    private class ImageProcessingAsyncTask extends AsyncTask<Uri, Integer, Bitmap> {
        @Override
        protected Bitmap doInBackground(Uri... params) {
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(params[0]));
                return setPic(bitmap);
            } catch (FileNotFoundException e) {
                Log.d(TAG, "Image uri is not received or recognized");
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //TODO: Update the progress bar
            //  mProgressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            //TODO: Complete this method
            mImageView.setImageBitmap(bitmap);
            // mProgressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //TODO: Complete this method
            // mProgressBar.setProgress(0);
            // mProgressBar.setVisibility(View.VISIBLE);
        }
        private Bitmap setPic(Bitmap bitmap) {
            // Get the dimensions of the View

            Bitmap sizedBitmap = bitmap.copy(bitmap.getConfig(),true);

            int targetW = mImageView.getWidth();
            int targetH = mImageView.getHeight();

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
            this.publishProgress(Math.round(scaleFactor));

            return sizedBitmap;
        }

    }
}



//private class ImageProcessingAsyncTask extends AsyncTask<Uri, Integer, Bitmap> {
//
//    @Override
//    protected Bitmap doInBackground(Uri... params) {
//        try {
//            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(params[0]));
//            return setPic(bitmap);
//        } catch (FileNotFoundException e) {
//            Log.d(TAG, "Image uri is not received or recognized");
//        }
//
//        return null;
//    }
//
//    @Override
//    protected void onProgressUpdate(Integer... values) {
//        super.onProgressUpdate(values);
//        // Updates the progress bar
//        //mProgressBar.setProgress(values[0]);
//    }
//
//    @Override
//    protected void onPostExecute(Bitmap bitmap) {
//        super.onPostExecute(bitmap);
//        mImageView.setImageBitmap(bitmap);
//        //mProgressBar.setVisibility(View.INVISIBLE);
//    }
//
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//    }
//
//TODO GOT THIS FROM ANDROID DEVELOPERS
//TODO THE CURRENT AYSNC TASK BEING USED IS FROM GRAEME LAST ADD PHOTO IN
//    //Original
//    //Decode a Scaled Image
//
//    private void setPic() {
//        // Get the dimensions of the View
//        int targetW = mImageView.getWeight();
//        int targetH = mImageView.getHeight();
//
//        // Get the dimensions of the bitmap
//        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//        bmOptions.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
//        int photoW = bmOptions.outWidth;
//        int photoH = bmOptions.outHeight;
//
//        // Determine how much to scale down the image
//        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
//
//        // Decode the image file into a Bitmap sized to fill the View
//        bmOptions.inJustDecodeBounds = false;
//        bmOptions.inSampleSize = scaleFactor;
//        bmOptions.inPurgeable = true;
//
//        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
//        mImageView.setImageBitmap(bitmap);
//    }

