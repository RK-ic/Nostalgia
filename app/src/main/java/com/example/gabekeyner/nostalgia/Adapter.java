package com.example.gabekeyner.nostalgia;

import android.content.Context;
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
    public void onBindViewHolder(Adapter.ViewHolder holder, final int position) {
        holder.mTextView.setText(images.get(position).getImageHelper_name());
        Picasso.with(context).load(images.get(position).getImageHelper_url()).into(holder.mImageView);
        //FOR ANIMATION
        if(position > previousPosition){
            //We are scrolling down
            AnimationUtil.animate(holder, true);
        }else { //We are scrolling up
            AnimationUtil.animate(holder, false);
        }
        previousPosition = position;

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "on click position" + position, Toast.LENGTH_SHORT).show();
            }
        });

        holder.mImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(context, "on long clickposition" + position, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }


    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ImageView mImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.textView);
            mImageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }


}
