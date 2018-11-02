package beansjar.djimpanse.com.beansjar.animations;


import android.app.Activity;
import android.graphics.Point;
import android.view.Display;


public class AnimationSettings {

    private int startColor;
    private int endColor;

    private float startX;
    private float startY;
    private float startRadius;
    private float endRadius;

    private long duration;

    private AnimationSettings() {}

    public static AnimationSettings constructRevealAnimation(Activity activity, float startX,
                                                             float startY, long duration, int
                                                                     startColor, int endColor) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;

        AnimationSettings settings = new AnimationSettings();

        settings.startX = startX;
        settings.startY = startY;
        settings.startRadius = 10;
        settings.endRadius = height + 200;
        settings.duration = duration;
        settings.startColor = startColor;
        settings.endColor = endColor;

        return settings;
    }

    public float getStartX() {
        return startX;
    }

    public float getStartY() {
        return startY;
    }

    public float getStartRadius() {
        return startRadius;
    }

    public float getEndRadius() {
        return endRadius;
    }

    public long getDuration() {
        return duration;
    }

    public int getStartColor() {
        return startColor;
    }

    public int getEndColor() {
        return endColor;
    }
}
