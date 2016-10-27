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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.gabekeyner.nostalgia.DatabaseActivitys.Post;
import com.facebook.CallbackManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public RecyclerView recyclerView;
    CallbackManager callbackManager;

    public static final String TAG = "Nostalgia";
    private DatabaseReference mDatabase;
    public ImageView mImageView;
    private StorageReference mStorage;

    FloatingActionButton fab, fabPhoto, fabVideo, floatingActionButton1, floatingActionButton2, floatingActionButton3;
    Animation hide_fab, show_fab, show_fab2, show_fab3, rotate_anticlockwise, rotate_clockwise, stayhidden_fab;
    boolean isOpen = true;

    //Handles the the array for the database
    Post[] postArray;

    private final String image_names[] = {
            "City",
            "ADI",
            "Friends",
            "Family",
            "Birthday",
            "Adventure",
            "Hanging Out",
            "Board Walk",
            "Wall-E",
            "Grid",
            "Washington",
            "City Bridge",
            "Nature Tunnel",
            "City at Dusk",
            "Eiffel Tower",
            "Nebula",
            "Beachin It",
            "Autumn",
            "The Warf",
            "River Sunset",
            "Winter",
            "So Green",
            "Metropolis",
            "Cherry Blossom",
            "Image",
            "Image",
            "Image",
            "Image",
            "Image",
            "Image",
            "Image",
            "Image",
            "Image",
            "Image",
            "Image",
            "Image",
            "Image",
            "Image",
            "Image",
            "Image",
            "Image",
            "Image",
            "Image",
            "Image",
            "Image",
            "Image",
            "Image"
    };

    private final String image_urls[] = {
            "http://www.hiltonhotels.de/assets/img/destinations/China/china-3.jpg",
            "http://wallpapersonthe.net/wallpapers/b/1920x1080/1920x1080-london_subways_light_trails_yellow_selective_coloring_city_long_exposure_metro-3752.png",
            "http://plusquotes.com/images/quotes-img/hd-wallpaper-2.jpg",
            "http://www.planwallpaper.com/static/images/Nature-Beach-Scenery-Wallpaper-HD.jpg",
            "http://www.hdbloggers.net/wp-content/uploads/2015/12/HD-Wallpaper-40.jpg",
            "http://24.media.tumblr.com/812633100cc8a167f4875cf39099e743/tumblr_mexa6oloEB1qzyjdbo1_1280.jpg",
            "http://67.media.tumblr.com/dd42b18d499fb9db2c9772c6efb34650/tumblr_mrhogsZc2S1qzyjdbo1_1280.jpg",
            "https://images.freecreatives.com/wp-content/uploads/2015/05/Vintage-Photography-46-HD-Wallpaper.jpg",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS5uSKFBAiwMgdiGvEre3qWwRCfAQOfWxPvUY8_DH0GBbGJs7zQ",
            "http://lh5.ggpht.com/_Gq1jO6iuU2U/TTtbe7YWMAI/AAAAAAAAHfE/wq8EJ6fc4Yk/s9000/Abstract%2BTunnel%2BVision%2BHD%2BWallpaper.jpg",
            "http://cdn.wallpapersafari.com/4/13/4n5qmQ.jpg",
            "http://www.rajeshagrawal.com/wp-content/uploads/2015/02/london-at-night-desktop-wallpaper-beautiful-london-city-wallpapers-with-hd-gallery2.jpg",
            "https://images5.alphacoders.com/306/306940.jpg",
            "http://i2.cdn.turner.com/cnnnext/dam/assets/150306145109-beautiful-japan-kawachi-wisteria-super-169.jpg",
            "http://netupd8.com/walls/WallPaperHD039.resized.jpg",
            "http://wallpaperspro.net/wp-content/uploads/2016/08/Beautiful-Images-free-1134x750.jpg",
            "http://freephotos.atguru.in/hdphotos/beautiful-wallpapers/beautiful-wallpapers-6.jpg",
            "http://funnyneel.com/image/files/i/01-2014/beautiful-trees-v.jpg",
            "http://cdn.thecoolist.com/wp-content/uploads/2016/05/Japanese-Cherry-beautiful-tree-960x540.jpg",
            "http://www.technocrazed.com/wp-content/uploads/2015/12/beautiful-wallpaper-download-13.jpg",
            "https://iso.500px.com/wp-content/uploads/2016/04/stock-photo-150595123-1500x1000.jpg",
            "http://wallpaperwarrior.com/beautiful-wallpapers/beautiful-wallpapers-17/",
            "http://www.technocrazed.com/wp-content/uploads/2015/12/beautiful-wallpaper-download-11.jpg",
            "https://static.pexels.com/photos/17682/pexels-photo.jpg",
            "http://1.bp.blogspot.com/_Zw41kxI2akg/TJ9vsPZ76NI/AAAAAAAACvs/pk94qBVMJrM/s1600/natura_iarna_wallpaper.jpg",
            "http://www.hiltonhotels.de/assets/img/destinations/China/china-3.jpg",
            "http://kingofwallpapers.com/city-pictures/city-pictures-001.jpg",
            "https://newevolutiondesigns.com/images/freebies/city-wallpaper-11.jpg",
            "http://kingofwallpapers.com/city-pictures/city-pictures-021.jpg",
            "http://photos.mandarinoriental.com/is/image/MandarinOriental/excelsior-exterior-home?$HomepageHeroImage$",
            "http://best-wallpaper.net/wallpaper/1920x1200/1109/Beautiful-abstract-flight-line_1920x1200.jpg",
            "http://lh5.ggpht.com/_Gq1jO6iuU2U/TTtbe7YWMAI/AAAAAAAAHfE/wq8EJ6fc4Yk/s9000/Abstract%2BTunnel%2BVision%2BHD%2BWallpaper.jpg",
            "http://cdn.wallpapersafari.com/4/13/4n5qmQ.jpg",
            "http://www.rajeshagrawal.com/wp-content/uploads/2015/02/london-at-night-desktop-wallpaper-beautiful-london-city-wallpapers-with-hd-gallery2.jpg",
            "https://images5.alphacoders.com/306/306940.jpg",
            "http://i2.cdn.turner.com/cnnnext/dam/assets/150306145109-beautiful-japan-kawachi-wisteria-super-169.jpg",
            "http://netupd8.com/walls/WallPaperHD039.resized.jpg",
            "http://wallpaperspro.net/wp-content/uploads/2016/08/Beautiful-Images-free-1134x750.jpg",
            "http://freephotos.atguru.in/hdphotos/beautiful-wallpapers/beautiful-wallpapers-6.jpg",
            "http://funnyneel.com/image/files/i/01-2014/beautiful-trees-v.jpg",
            "http://cdn.thecoolist.com/wp-content/uploads/2016/05/Japanese-Cherry-beautiful-tree-960x540.jpg",
            "http://www.technocrazed.com/wp-content/uploads/2015/12/beautiful-wallpaper-download-13.jpg",
            "https://iso.500px.com/wp-content/uploads/2016/04/stock-photo-150595123-1500x1000.jpg",
            "http://wallpaperwarrior.com/beautiful-wallpapers/beautiful-wallpapers-17/",
            "http://www.technocrazed.com/wp-content/uploads/2015/12/beautiful-wallpaper-download-11.jpg",
            "https://static.pexels.com/photos/17682/pexels-photo.jpg",
            "http://1.bp.blogspot.com/_Zw41kxI2akg/TJ9vsPZ76NI/AAAAAAAACvs/pk94qBVMJrM/s1600/natura_iarna_wallpaper.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        System.out.println("MainActivity.onCreate: " + FirebaseInstanceId.getInstance().getToken());
        initViews();
        fabAnimations();
        fabClickable();
        fabPhoto.startAnimation(stayhidden_fab);
        fabVideo.startAnimation(stayhidden_fab);
        fabPhoto.setClickable(false);
        fabVideo.setClickable(false);

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


        //Handles the Read and Write to Database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


    private void fabAnimations() {
        //ANIMATION LAYOUTS
        hide_fab = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_hide);
        show_fab = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_show);
        show_fab2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_show2);
        show_fab3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_show3);
        rotate_anticlockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);
        rotate_clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        stayhidden_fab = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_stayhidden);

        //MY FLOATING ACTION BUTTONS
        fab = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.floatingActionButton1);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.floatingActionButton3);
        fabPhoto = (FloatingActionButton) findViewById(R.id.fabPhoto);
        fabVideo = (FloatingActionButton) findViewById(R.id.fabVideo);

    }

    private void fabClickable() {

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    floatingActionButton2.startAnimation(rotate_anticlockwise);

                    floatingActionButton1.startAnimation(hide_fab);
                    floatingActionButton3.startAnimation(hide_fab);

                    fabPhoto.startAnimation(show_fab2);
                    fabVideo.startAnimation(show_fab3);

                    fabPhoto.setClickable(true);
                    fabVideo.setClickable(true);
                    floatingActionButton2.setClickable(true);
                    floatingActionButton1.setClickable(false);
                    floatingActionButton3.setClickable(false);

                    isOpen = false;

                } else {
                    fabPhoto.startAnimation(hide_fab);
                    fabVideo.startAnimation(hide_fab);

                    fabPhoto.setClickable(false);
                    fabVideo.setClickable(false);

                    floatingActionButton1.startAnimation(show_fab2);
                    floatingActionButton3.startAnimation(show_fab3);

                    floatingActionButton1.setClickable(true);
                    floatingActionButton2.setClickable(true);
                    floatingActionButton3.setClickable(true);

                    isOpen = true;
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOpen) {
                    fab.startAnimation(rotate_anticlockwise);


                    floatingActionButton1.startAnimation(hide_fab);
                    floatingActionButton2.startAnimation(hide_fab);
                    floatingActionButton3.startAnimation(hide_fab);

                    fabPhoto.startAnimation(stayhidden_fab);
                    fabVideo.startAnimation(stayhidden_fab);

                    floatingActionButton1.setClickable(false);
                    floatingActionButton2.setClickable(false);
                    floatingActionButton3.setClickable(false);


                    isOpen = false;

                } else {
                    fab.startAnimation(rotate_clockwise);

                    floatingActionButton1.startAnimation(show_fab2);
                    floatingActionButton2.startAnimation(show_fab);
                    floatingActionButton3.startAnimation(show_fab3);

                    fabPhoto.startAnimation(stayhidden_fab);
                    fabVideo.startAnimation(stayhidden_fab);

                    floatingActionButton1.setClickable(true);
                    floatingActionButton2.setClickable(true);
                    floatingActionButton3.setClickable(true);

                    fabPhoto.setClickable(false);
                    fabVideo.setClickable(false);

                    isOpen = true;
                }
            }
        });


        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.ACTIVITY_INTENTION, CameraActivity.TAKE_PHOTO);
                startActivity(intent);
            }
        });
        fabPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.ACTIVITY_INTENTION, CameraActivity.GALLERY_PICKER);
                startActivity(intent);

            }
        });
        fabVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.ACTIVITY_INTENTION, CameraActivity.GALLERY_VIDEO_PICKER);
                startActivity(intent);
            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.ACTIVITY_INTENTION, CameraActivity.VIDEO_SHOOTER);
                startActivity(intent);
            }
        });
    }

    private void clickFab() {
        fab.callOnClick();
    }
    //VIEWS
    private void initViews() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
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
