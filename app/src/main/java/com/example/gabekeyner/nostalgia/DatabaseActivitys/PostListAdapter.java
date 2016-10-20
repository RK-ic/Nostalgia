//package com.example.gabekeyner.nostalgia.DatabaseActivitys;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.AlphaAnimation;
//import android.view.animation.Animation;
//import android.view.animation.ScaleAnimation;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.gabekeyner.nostalgia.AnimationUtil;
//import com.example.gabekeyner.nostalgia.R;
//import com.squareup.picasso.Picasso;
//
//import java.util.Random;
//
///**
// * Created by GabeKeyner on 10/17/2016.
// */
//
//public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostListViewHolder> {
//
//
//    Post[] postArray;
//    Context context;
//    int previousPosition = 0;
//
//    //TODO NEW ADAPTER
//    public PostListAdapter(Post[]postArray, Context context) {
//        this.context = context;
//        this.postArray = postArray;
//    }
//    @Override
//    public PostListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
//        PostListViewHolder holder = new PostListViewHolder(itemView);
//        return holder;
//
//
//
//    }
//
//
//    @Override
//    public void onBindViewHolder(final PostListAdapter.PostListViewHolder holder, final int position) {
//        TextView title = holder.title;
//
//        title.setText(postArray[position].getTitle());
//
//
//        Picasso.with(context)
//                .load(postArray[position].getImageURL())
//                .resize(800, 500)
//                .centerCrop()
//                .into(holder.mImageView);
//
//        //FOR ANIMATION
//        if(position > previousPosition){
//            //We are scrolling down
//            AnimationUtil.animate(holder, true);
//        }else { //We are scrolling up
//            AnimationUtil.animate(holder, false);
//        }
//        previousPosition = position;
//
//        setScaleAnimation(holder.mImageView);
//        setFadeAnimation(holder.title);
//        setAnimation(holder.mImageView, lastPosition);
//
//        holder.mImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "on click position" + position, Toast.LENGTH_SHORT).show();
//            }
//
//        });
//
//        holder.mImageView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Toast.makeText(context, "on long clickposition" + position, Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return 1;
//    }
//
//
//    public static class PostListViewHolder extends RecyclerView.ViewHolder{
//
//        public TextView title;
//        public ImageView mImageView;
//
//        public PostListViewHolder(View itemView) {
//            super(itemView);
//            title = (TextView) itemView.findViewById(R.id.textView);
//            mImageView = (ImageView) itemView.findViewById(R.id.imageView);
//        }
//    }
//
//
//
//    //ANIMATIONS
//    private final static int SCALE_DURATION = 180;
//    private final static int FADE_DURATION = 2000;
//
//    private void setFadeAnimation(View view) {
//        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
//        anim.setDuration(FADE_DURATION);
//        view.startAnimation(anim);
//    }
//
//    private void setScaleAnimation(View view) {
//        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        anim.setDuration(SCALE_DURATION);
//        view.startAnimation(anim);
//    }
//
//    private int lastPosition = -1;
//
//    private void setAnimation(View viewToAnimate, int position) {
//        // If the bound view wasn't previously displayed on screen, it's animated
//        if (position > lastPosition) {
//            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//            anim.setDuration(new Random().nextInt(501));//to make duration random number between [0,501)
//            viewToAnimate.startAnimation(anim);
//            lastPosition = position;
//        }
//    }
//
//}
