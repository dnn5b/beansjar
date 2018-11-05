package beansjar.djimpanse.com.beansjar.animations;


import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AlphaAnimation;


public class Animator {

    private AnimationSettings settings;

    public Animator(AnimationSettings settings) {
        this.settings = settings;
    }

    public void startCircularEnter(View view) {
        view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int
                    oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);

                // Circular reveal
                android.animation.Animator anim = ViewAnimationUtils.createCircularReveal(v, (int) settings.getStartX
                        (), (int) settings.getStartY(), settings.getStartRadius(), settings.getEndRadius());
                anim.setDuration(settings.getDuration());
                anim.setInterpolator(new FastOutSlowInInterpolator());
                anim.start();

                // Color fade
                startColorAnimation(view, false);

                // Alpha fade
                startAlphaAnimation(view, true);
            }
        });
    }

    public void startCircularExit(final View view, final AnimationListener listener) {
        // Circular reveal
        android.animation.Animator anim = ViewAnimationUtils.createCircularReveal(view, (int) settings.getStartX(),
                (int) settings.getStartY(), settings.getEndRadius(), settings.getStartRadius());
        anim.setDuration(settings.getDuration());
        anim.setInterpolator(new FastOutSlowInInterpolator());

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                super.onAnimationEnd(animation);
                listener.onAnimationEnd();
            }
        });
        anim.start();

        // Color fade
        startColorAnimation(view, true);

        // Alpha fade
        startAlphaAnimation(view, false);
    }

    private void startColorAnimation(final View view, boolean reverse) {
        ValueAnimator anim = new ValueAnimator();

        if (reverse) {
            anim.setIntValues(settings.getEndColor(), settings.getStartColor());
        } else {
            anim.setIntValues(settings.getStartColor(), settings.getEndColor());
        }

        anim.setEvaluator(new ArgbEvaluator());
        anim.addUpdateListener(valueAnimator -> view.setBackgroundColor((Integer) valueAnimator.getAnimatedValue()));
        anim.setDuration(settings.getDuration());
        anim.start();
    }

    private void startAlphaAnimation(View view, boolean toVisible) {
        AlphaAnimation animation;
        if (toVisible) {
            animation = new AlphaAnimation(0.2f, 1.0f);
        } else {
            animation = new AlphaAnimation(1.0f, 0.2f);
        }
        animation.setDuration(settings.getDuration());
        animation.setFillAfter(true);
        animation.setInterpolator(new FastOutSlowInInterpolator());
        view.startAnimation(animation);
    }

}
