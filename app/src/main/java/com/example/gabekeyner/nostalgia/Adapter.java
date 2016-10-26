package com.example.gabekeyner.nostalgia;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ArrayList<ImageHelper> images;
    private Context context;
    int previousPosition = 0;

    public Adapter(Context context, ArrayList<ImageHelper> images) {
        this.images = images;
        this.context = context;
    }
    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final Adapter.ViewHolder holder, final int position) {
        final ImageHelper photo = images.get(position);
        holder.mTextView.setText(photo.getImageHelper_name());
        PicassoClient.downloadImage(context, photo.getImageHelper_url(), holder.mImageView);
        Picasso.with(context)
                .load(images.get(position)
                        .getImageHelper_url())
                .resize(800, 500)
                .centerCrop()
                .into(holder.mImageView);

        //FOR ANIMATION
        if (position > previousPosition) {
            //We are scrolling down
            AnimationUtil.animate(holder, true);
        } else { //We are scrolling up
            AnimationUtil.animate(holder, false);
        }
        previousPosition = position;
        int lastPosition = -1;
        AnimationUtil.setScaleAnimation(holder.mImageView);
        AnimationUtil.setFadeAnimation(holder.mTextView);
        AnimationUtil.setAnimation(holder.mImageView, lastPosition);

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Calls the Open Detail Activity Method
                openDetailActivity(photo.getImageHelper_name(), photo.getImageHelper_url());

            }
        });
        holder.mImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//            TODO make a buidler for delete or save Image    AlertDialog dialog = new AlertDialog()
                Toast.makeText(context, "on long clickposition" + position, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
    @Override
    public int getItemCount() {
        return images.size();
    }
    //Handles the send the data to the Detail Activity
    private void openDetailActivity(String title, String imageUrl) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("imageUrl", imageUrl);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ImageView mImageView;

        public ViewHolder(final View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            mTextView = (TextView) itemView.findViewById(R.id.textView);
            mImageView = (ImageView) itemView.findViewById(R.id.imageView);
        }

    }
}
