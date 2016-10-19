package com.example.gabekeyner.nostalgia;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by GabeKeyner on 10/19/2016.
 */

public class PicassoClient {

    public static void downloadImage (Context context, String imageUrl, ImageView mImageView){

        if (imageUrl != null && imageUrl.length()>0){
            Picasso.with(context).load(imageUrl).placeholder(R.drawable.camera).into(mImageView);

        }else{
            Picasso.with(context).load(R.drawable.camera).into(mImageView);
        }
    }
}
