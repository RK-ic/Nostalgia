package com.example.gabekeyner.nostalgia;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {


    TextView titleTxt;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        //Receive Data
        Intent intent = this.getIntent();
        String title = intent.getExtras().getString("title");
        String imageUrl = intent.getExtras().getString("imageUrl");

        //Bind Data

        titleTxt.setText(title);
        PicassoClient.downloadImage(this,imageUrl,imageView);

    }

}
