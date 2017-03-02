package com.mouthofrandom.cardshark.graphics.utility;

/**
 * Created by coleman on 2/26/17.
 */
public interface Observer extends Animatable
{
    void update(Subject.Event event);
}
