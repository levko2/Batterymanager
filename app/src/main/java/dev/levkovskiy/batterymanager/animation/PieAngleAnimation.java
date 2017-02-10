package dev.levkovskiy.batterymanager.animation;

import android.view.animation.Animation;
import android.view.animation.Transformation;

import dev.levkovskiy.batterymanager.ui.view.PieView;


/**
 * @author Alejandro Zürcher (alejandro.zurcher@gmail.com)
 */
public class PieAngleAnimation extends Animation {

    private PieView arcView;
    private float oldAngle;

    public PieAngleAnimation(PieView arcView) {
        this.oldAngle = arcView.getPieAngle();
        this.arcView = arcView;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        float angle = oldAngle * interpolatedTime;
        arcView.setPieAngle(angle);
        arcView.requestLayout();
    }
}