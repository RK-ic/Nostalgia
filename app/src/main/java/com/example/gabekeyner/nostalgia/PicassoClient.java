package com.example.gabekeyner.nostalgia;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PicassoClient {

    public static void downloadImage (Context context, String imageUrl, ImageView mImageView){

        if (imageUrl != null && imageUrl.length()>0){
            Picasso.with(context).load(imageUrl).resize(1500,950).placeholder(R.drawable.camera).into(mImageView);

        }else{
            Picasso.with(context).load(R.drawable.camera).into(mImageView);
        }
    }
}
