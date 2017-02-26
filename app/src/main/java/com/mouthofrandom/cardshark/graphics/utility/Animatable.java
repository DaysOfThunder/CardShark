package com.mouthofrandom.cardshark.graphics.utility;

/**
 * Created by coleman on 2/22/17.
 */

public interface Animatable extends Drawable
{
    void start(AnimationArguments animationArgs);

    void next();

    boolean isComplete();

    void finish();

    interface AnimationArguments{}
}
