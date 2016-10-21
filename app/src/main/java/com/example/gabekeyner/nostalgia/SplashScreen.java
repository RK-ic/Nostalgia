package com.example.gabekeyner.nostalgia;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class SplashScreen extends AppCompatActivity {
    Animation fade_in, upfade;
    ImageView imageView;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        fade_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        upfade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.upfade);
        imageView = (ImageView) findViewById(R.id.imageSplash1);
        textView = (TextView) findViewById(R.id.textViewSplash);

        textView.setVisibility(View.INVISIBLE);

        Picasso.with(this)
                .load("http://iphonewalls.net/wp-content/uploads/2014/08/Colorful%20Rain%20Bokeh%20Lights%20iPhone%205%20Wallpaper.jpg")
                .fit()
                .into(imageView);
        imageView.startAnimation(fade_in);



        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.startAnimation(upfade);
            }
        },2000);



        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            }
        }, 4000);

    }

}
