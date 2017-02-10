package dev.levkovskiy.batterymanager.animation;

import android.view.animation.Animation;
import android.view.animation.Transformation;

import dev.levkovskiy.batterymanager.ui.view.PieView;

/**
 * @author Alejandro Zürcher (alejandro.zurcher@gmail.com)
 */
public class PieStrokeWidthAnimation extends Animation {

    private PieView arcView;
    private int oldWidth;

    public PieStrokeWidthAnimation(PieView arcView) {
        this.oldWidth = arcView.getPieInnerPadding();
        this.arcView = arcView;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        int width = (int)(oldWidth * interpolatedTime);
        arcView.setPieInnerPadding(width);
        arcView.requestLayout();
    }
}