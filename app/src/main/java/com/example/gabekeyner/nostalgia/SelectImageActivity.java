package com.example.gabekeyner.nostalgia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SelectImageActivity extends AppCompatActivity {

    private static final String TAG = SelectImageActivity.class.getName();
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView mImageView;
    private Button selectBtn;
    String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);

        //casting the ImageView
        mImageView = (ImageView) findViewById(R.id.selectImage);

        mImageView.setImageResource(R.drawable.ic_menu_camera);

        selectBtn = (Button) findViewById(R.id.select_image);
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grabFromGallery();
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

    //select from gallery
    private void grabFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == MainActivity.RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            ImageProcessingAsyncTask imageTask = new ImageProcessingAsyncTask();
            imageTask.execute(selectedImage);
        }
    }

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
            // Updates the progress bar
            //mProgressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mImageView.setImageBitmap(bitmap);
            //mProgressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

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

    private void publishProgress(int round) {
    }
}
