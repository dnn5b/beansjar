package beansjar.djimpanse.com.beansjar.animations;


import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.ViewAnimationUtils;


public class Animator {

    private AnimationSettings settings;

    public Animator(AnimationSettings settings) {
        this.settings = settings;
    }

    public void startCircularEnter(View view) {
        view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int
                    oldLeft, int oldTop, int oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);

                android.animation.Animator anim = ViewAnimationUtils.createCircularReveal(v,
                        (int) settings.getStartX(), (int) settings.getStartY(), settings
                                .getStartRadius(), settings.getEndRadius());
                anim.setDuration(settings.getDuration());
                anim.setInterpolator(new FastOutSlowInInterpolator());
                anim.start();

                startColorAnimation(view, false);
            }
        });
    }

    public void startCircularExit(final View view, final OnDismissedListener listener) {
        android.animation.Animator anim = ViewAnimationUtils.createCircularReveal(view, 0, 0,
                settings.getStartRadius(), settings.getEndRadius());
        anim.setDuration(settings.getDuration());
        anim.setInterpolator(new FastOutSlowInInterpolator());

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                super.onAnimationEnd(animation);
                listener.onDismissed();
            }
        });

        anim.start();
        startColorAnimation(view, true);
    }

    private void startColorAnimation(final View view, boolean reverse) {
        ValueAnimator anim = new ValueAnimator();

        if (reverse) {
            anim.setIntValues(settings.getEndColor(), settings.getStartColor());
        } else {
            anim.setIntValues(settings.getStartColor(), settings.getEndColor());
        }

        anim.setEvaluator(new ArgbEvaluator());
        anim.addUpdateListener(valueAnimator -> view.setBackgroundColor((Integer) valueAnimator
                .getAnimatedValue()));
        anim.setDuration(settings.getDuration());
        anim.start();
    }

    public interface OnDismissedListener {

        void onDismissed();
    }

}
