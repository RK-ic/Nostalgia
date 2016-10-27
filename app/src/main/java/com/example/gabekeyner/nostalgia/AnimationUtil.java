package com.example.gabekeyner.nostalgia;


        import android.animation.AnimatorSet;
        import android.animation.ObjectAnimator;
        import android.support.v7.widget.RecyclerView;
        import android.view.View;
        import android.view.animation.AlphaAnimation;
        import android.view.animation.Animation;
        import android.view.animation.ScaleAnimation;

        import java.util.Random;

public class AnimationUtil {
    private final static int SCALE_DURATION = 180;
    private final static int FADE_DURATION = 2000;

    public static void animate(RecyclerView.ViewHolder holder, boolean goesDown){
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.itemView, "translationY", goesDown==true ? 800 : -800, 0);
        animatorTranslateY.setDuration(800);
        animatorSet.playTogether(animatorTranslateY);
        animatorSet.start();
    }
    public static void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(SCALE_DURATION);
        view.startAnimation(anim);
    }
    public static void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        int lastPosition = -1;
        if (position > lastPosition) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(new Random().nextInt(501));//to make duration random number between [0,501)
            viewToAnimate.startAnimation(anim);
            lastPosition = position;
        }
    }
    public static void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }
}
