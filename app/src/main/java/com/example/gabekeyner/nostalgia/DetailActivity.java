package com.example.gabekeyner.nostalgia;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    Animation fade_in;
    TextView titleTxt;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fade_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_detail);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add new comment ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        titleTxt = (TextView)findViewById(R.id.detailTitle);
        imageView = (ImageView)findViewById(R.id.detialView);
        fab.startAnimation(fade_in);
        titleTxt.startAnimation(fade_in);


        //Receive Data
        Intent intent = this.getIntent();
        String title = intent.getExtras().getString("title");
        String imageUrl = intent.getExtras().getString("imageUrl");

        //Bind Data

        titleTxt.setText(title);
        PicassoClient.downloadImage(this,imageUrl,imageView);

    }

}
