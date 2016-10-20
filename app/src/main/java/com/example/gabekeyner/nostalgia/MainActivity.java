package com.example.gabekeyner.nostalgia;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.Toast;

import com.example.gabekeyner.nostalgia.DatabaseActivitys.Post;
import com.facebook.CallbackManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public RecyclerView recyclerView;
    CallbackManager callbackManager;

    public static final String TAG = "Nostalgia";
    private DatabaseReference mDatabase;



    FloatingActionButton fab, fabPhoto, fabVideo, floatingActionButton1, floatingActionButton2, floatingActionButton3;
    Animation hide_fab, show_fab, show_fab2, show_fab3, rotate_anticlockwise, rotate_clockwise, stayhidden_fab;
    boolean isOpen = true;

    //Handles the the array for the database
    Post[] postArray;


    public static final int REQUEST_TAKE_PHOTO = 0;
    public static final int REQUEST_TAKE_VIDEO = 1;
    public static final int REQUEST_PICK_PHOTO = 2;
    public static final int REQUEST_PICK_VIDEO = 3;

    public static final int MEDIA_TYPE_IMAGE = 4;
    public static final int MEDIA_TYPE_VIDEO = 5;

    private Uri mMediaUri;

    private final String image_names[] = {
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
            "https://lh3.googleusercontent.com/jRAbvlcfEQ9n4-v4gHy1PXaqyLb4YRcwUkGNK2EAXqw-AmRKD9TUKwTv_clO22qt5qnZFRl49jeQUK4TgiWIs4YsUWGEM-Kea0TxIML5OZdpWFYtGDVTcmILSM3Db_4OyC6M8tNriXEY_LMfZHwNKS_GkYKE-ZQzxiemIbH4L53bbjEVz9kwgt_qZb9fJ7h4S13f_VQZjoMjTleeBbNzP0dqtkDomkX3KL-FyKA8-Pki1Nib9fcpK_uY1Mby9DW6H0v4sZ8mGhmfdBuLGttyJD_tg81LSe-DLsYpoJPbqbwHBXDexGNo9Zo2939UulZ6Z1DihDcfhVWRxVjS6TDBo0mglkQoQBfocFyvE-hOvhyMEhzbKfc_DN8_h3Xaj_zp19qNLyP2TmY7A6U-Y3zoUCcdkXYCled-ary6dgMpmB7lry7viS6B7sko77VoL9lOUOnfTbo6--zLNZclkDqmRA6LCPhXazicHMaGOlst_4oGdhaagHwWvA0wwuAGKFQkevbNncQIp6OhnMZYtZPUtniiWXeOkgI4l1a1vf8mBKifhp3CVaJmgdrQh7GLSxRdM2jK1IAp-3lSqwBGwjk7qXAXudAgqUZAz6lA_ZcZnJ5WgP-Llg=w1279-h960-no",
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO || requestCode == REQUEST_PICK_PHOTO) {
                if (data != null) {
                    mMediaUri = data.getData();
                }
                Intent intent = new Intent(this, CameraActivity.class);
                intent.setData(mMediaUri);
                startActivity(intent);
            }
            else if (requestCode == REQUEST_TAKE_VIDEO) {
                Intent intent = new Intent (Intent.ACTION_VIEW, mMediaUri);
                intent.setDataAndType(mMediaUri, "video/*");
                startActivity(intent);
            }
            else if (resultCode == REQUEST_PICK_VIDEO) {
                if (data != null) {
                    Log.i(TAG, "Video content URI: " + data.getData());
                    Toast.makeText(this, "Video content URI: " + data.getData(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        else if (resultCode != RESULT_CANCELED) {
            Toast.makeText(this, "Sorry, there was an error!", Toast.LENGTH_LONG).show();
        }

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

                }else {
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
                startActivity(intent);

                mMediaUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                if (mMediaUri == null) {
                    Toast.makeText(MainActivity.this, "There was a problem accessing your device's external storage.",
                            Toast.LENGTH_SHORT).show();
                }
                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);
                startActivityForResult(takePhotoIntent, REQUEST_TAKE_PHOTO);

            }
        });
        fabPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhotoIntent = new Intent(Intent.ACTION_GET_CONTENT);
                pickPhotoIntent.setType("image/*");
                startActivityForResult(pickPhotoIntent, REQUEST_PICK_PHOTO);
            }
        });
        fabVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickVideoIntent = new Intent(Intent.ACTION_GET_CONTENT);
                pickVideoIntent.setType("video/*");
                startActivityForResult(pickVideoIntent, REQUEST_PICK_VIDEO);
            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMediaUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
                if (mMediaUri == null) {
                    Toast.makeText(MainActivity.this, "There was a problem accessing your device's external storage.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent takeVideoIntent = new Intent (MediaStore.ACTION_VIDEO_CAPTURE);
                    takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);
                    takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 15);
                    startActivityForResult(takeVideoIntent, REQUEST_TAKE_VIDEO);
                }
            }
        });
    }



    private Uri getOutputMediaFileUri(int mediaType) {
        //check for external storage
        if (isExternalStorageAvailable()) {
            // get the URI
            File mediaStorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            String fileName = "";
            String fileType = "";
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            if (mediaType == MEDIA_TYPE_IMAGE) {
                fileName = "IMG_" + timeStamp;
                fileType = ".jpg";
            } else if (mediaType == MEDIA_TYPE_VIDEO) {
                fileName = "VID_" + timeStamp;
                fileType = ".mp4";
            } else {
                return null;
            }
            File mediaFile;
            try {
                mediaFile = File.createTempFile(fileName, fileType, mediaStorageDir);
                Log.i(TAG, "File: " + Uri.fromFile(mediaFile));

                return Uri.fromFile(mediaFile);
            } catch (IOException e) {
                Log.e(TAG, "Error creating file: " + mediaStorageDir.getAbsolutePath() + fileName + fileType);
            }
        }
            // something went wrong
        return null;
    }

    private boolean isExternalStorageAvailable(){
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        else {
            return false;
        }
    }

    private void clickFab(){
        fab.callOnClick();
    }

    //VIEWS
    private void initViews() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

//        PostListAdapter adapter = new PostListAdapter(postArray,getApplicationContext());
//        recyclerView.setAdapter(adapter);
//
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
