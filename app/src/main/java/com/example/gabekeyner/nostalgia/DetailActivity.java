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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class DetailActivity extends AppCompatActivity {

   private ArrayAdapter<String>adapter;
    private EditText typeComment;
    private ArrayList<String> itemList;

    Animation fade_in;
    TextView titleTxt;

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO adding comment section (using arraylist)

        String[] items={"Apple","Banana","Clementine"};
        itemList=new ArrayList<String>(Arrays.asList(items));
       adapter = new ArrayAdapter<>(
               this,
               R.layout.comment_item,
               R.id.comment_txt_item,itemList);

        ListView LV = (ListView) findViewById(R.id.postedComments);
        LV.setAdapter(adapter);
        typeComment = (EditText) findViewById(R.id.comment);


        fade_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_detail);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Comment Posted", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                String newItem = typeComment.getText().toString();
                // Add new comment to ArrayList TODO need to add comment to database
                itemList.add(newItem);
                adapter.notifyDataSetChanged();

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
