package com.example.gabekeyner.nostalgia;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;

public class AnimationUtil {

    public static void animate(RecyclerView.ViewHolder holder, boolean goesDown){
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.itemView, "translationY", goesDown==true ? 500 : -500, 0);
        animatorTranslateY.setDuration(800);

//        ObjectAnimator animatorTranslateX = ObjectAnimator.ofFloat(holder.itemView, "translationX", -50, 50, -30, 30, -20, 20, -5, 5, 0);
//        animatorTranslateX.setDuration(800);

//        animatorSet.playTogether(animatorTranslateX, animatorTranslateY);

        animatorSet.playTogether(animatorTranslateY);
        animatorSet.start();
    }
}
