package com.example.gabekeyner.nostalgia;

import android.util.Log;

import com.example.gabekeyner.nostalgia.DatabaseActivitys.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by GabeKeyner on 10/17/2016.
 */

public class FireBaseReadWrite {

    private static final String TAG = "FirebaseReadWrite";
    // Firebase instance variables
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference postRef = mRootRef.child("post");


    public FireBaseReadWrite writeFirebase(String title,String imageURL){
        Post post = new Post(title,imageURL);

        mRootRef.child("posts").setValue(post);
        return null;
    }

    public void readFirebase(){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Post post = dataSnapshot.getValue(Post.class);
                Log.w(TAG, String.valueOf(post));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
//    mPostReference.addValueEventListener(postListener);
    }



}
