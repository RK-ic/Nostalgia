package com.example.gabekeyner.nostalgia;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public RecyclerView recyclerView;
    FloatingActionButton fab, floatingActionButton1, floatingActionButton2, floatingActionButton3;
    Animation hide_fab, show_fab, show_fab2, show_fab3, rotate_anticlockwise, rotate_clockwise;
    boolean isOpen = true;

    private final String image_names[] = {
            "Donut",
            "Eclair",
            "Froyo",
            "Gingerbread",
            "Honeycomb",
            "Ice Cream Sandwich",
            "Jelly Bean",
            "KitKat",
            "Lollipop",
            "Lollipop",
            "Lollipop",
            "Lollipop",
            "Lollipop",
            "Lollipop",
            "Lollipop",
            "Marshmallow"
    };

    private final String image_urls[] = {
            "http://ia.media-imdb.com/images/M/MV5BMTUzNDY0NjY4Nl5BMl5BanBnXkFtZTgwNjY4MTQ0NzE@._V1_.jpg",
            "http://ia.media-imdb.com/images/M/MV5BMTUzNDY0NjY4Nl5BMl5BanBnXkFtZTgwNjY4MTQ0NzE@._V1_.jpg",
            "http://ia.media-imdb.com/images/M/MV5BMTUzNDY0NjY4Nl5BMl5BanBnXkFtZTgwNjY4MTQ0NzE@._V1_.jpg",
            "http://ia.media-imdb.com/images/M/MV5BMTUzNDY0NjY4Nl5BMl5BanBnXkFtZTgwNjY4MTQ0NzE@._V1_.jpg",
            "http://ia.media-imdb.com/images/M/MV5BMTUzNDY0NjY4Nl5BMl5BanBnXkFtZTgwNjY4MTQ0NzE@._V1_.jpg",
            "http://ia.media-imdb.com/images/M/MV5BMTUzNDY0NjY4Nl5BMl5BanBnXkFtZTgwNjY4MTQ0NzE@._V1_.jpg",
            "http://ia.media-imdb.com/images/M/MV5BMTUzNDY0NjY4Nl5BMl5BanBnXkFtZTgwNjY4MTQ0NzE@._V1_.jpg",
            "http://ia.media-imdb.com/images/M/MV5BMTUzNDY0NjY4Nl5BMl5BanBnXkFtZTgwNjY4MTQ0NzE@._V1_.jpg",
            "http://ia.media-imdb.com/images/M/MV5BMTUzNDY0NjY4Nl5BMl5BanBnXkFtZTgwNjY4MTQ0NzE@._V1_.jpg",
            "http://ia.media-imdb.com/images/M/MV5BMTUzNDY0NjY4Nl5BMl5BanBnXkFtZTgwNjY4MTQ0NzE@._V1_.jpg",
            "http://ia.media-imdb.com/images/M/MV5BMTUzNDY0NjY4Nl5BMl5BanBnXkFtZTgwNjY4MTQ0NzE@._V1_.jpg",
            "http://ia.media-imdb.com/images/M/MV5BMTUzNDY0NjY4Nl5BMl5BanBnXkFtZTgwNjY4MTQ0NzE@._V1_.jpg",
            "http://ia.media-imdb.com/images/M/MV5BMTUzNDY0NjY4Nl5BMl5BanBnXkFtZTgwNjY4MTQ0NzE@._V1_.jpg",
            "http://ia.media-imdb.com/images/M/MV5BMTUzNDY0NjY4Nl5BMl5BanBnXkFtZTgwNjY4MTQ0NzE@._V1_.jpg",
            "http://ia.media-imdb.com/images/M/MV5BMTUzNDY0NjY4Nl5BMl5BanBnXkFtZTgwNjY4MTQ0NzE@._V1_.jpg",
            "http://ia.media-imdb.com/images/M/MV5BMTUzNDY0NjY4Nl5BMl5BanBnXkFtZTgwNjY4MTQ0NzE@._V1_.jpg"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("MainActivity.onCreate: " + FirebaseInstanceId.getInstance().getToken());

        initViews();
        fabAnimations();
        fabClickable();

        fab.postDelayed(new Runnable() {
            @Override
            public void run() {
                clickFab();
            }
        }, 2000);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


}

    private void fabAnimations() {
        //ANIMATION LAYOUTS
        hide_fab = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_hide);
        show_fab = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_show);
        show_fab2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_show2);
        show_fab3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_show3);
        rotate_anticlockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);
        rotate_clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);

        //MY FLOATING ACTION BUTTONS
        fab = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.floatingActionButton1);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.floatingActionButton3);

    }

    private void fabClickable() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOpen) {
                    fab.startAnimation(rotate_anticlockwise);


                    floatingActionButton1.startAnimation(hide_fab);
                    floatingActionButton2.startAnimation(hide_fab);
                    floatingActionButton3.startAnimation(hide_fab);

                    floatingActionButton1.setClickable(false);
                    floatingActionButton2.setClickable(false);
                    floatingActionButton3.setClickable(false);


                    isOpen = false;

                } else {
                    fab.startAnimation(rotate_clockwise);

                    floatingActionButton1.startAnimation(show_fab2);
                    floatingActionButton2.startAnimation(show_fab);
                    floatingActionButton3.startAnimation(show_fab3);

                    floatingActionButton1.setClickable(true);
                    floatingActionButton2.setClickable(true);
                    floatingActionButton3.setClickable(true);

                    isOpen = true;
                }
            }
        });
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CameraActivity.class);
                startActivity(intent);
            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SelectImageActivity.class);
                startActivity(intent);
            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void clickFab(){
        fab.callOnClick();
    }

    //VIEWS
    private void initViews(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<ImageHelper> imageHelpers = prepareData();
        Adapter mAdapter = new Adapter(getApplicationContext(), imageHelpers);
        recyclerView.setAdapter(mAdapter);
    }
    private ArrayList<ImageHelper> prepareData() {
        ArrayList<ImageHelper> imageHelpers = new ArrayList<>();
        for (int i = 0; i < image_names.length; i++) {
            ImageHelper imageHelper = new ImageHelper();
            imageHelper.setImageHelper_name(image_names[i]);
            imageHelper.setImageHelper_url(image_urls[i]);
            imageHelpers.add(imageHelper);

        }
        return imageHelpers;
    }

    @Override
    public void onBackPressed() {
        // Handles the Navigation Drawer Opening / Closing
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //LAYOUTS & ORIENTATIONS
        switch (id) {
            case R.id.linearViewVertical:
                LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(this);
                mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(mLinearLayoutManagerVertical);
                break;

            case R.id.staggeredViewVertical:
                StaggeredGridLayoutManager mStaggeredVerticalLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(mStaggeredVerticalLayoutManager);
                break;
//            noinspection SimplifiableIfStatement
//            if (id == R.id.action_settings) {
//                return true;
//            }
        }
            return super.onOptionsItemSelected(item);
        }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            // Handle the pulling from gallery action

        } else if (id == R.id.nav_create_group) {
            // Handle the New Grouup action  action
        } else if (id == R.id.nav_first_label) {
            // Handle the added Group Label action
        } else if (id == R.id.nav_share) {
            // Handle the share action
      }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
