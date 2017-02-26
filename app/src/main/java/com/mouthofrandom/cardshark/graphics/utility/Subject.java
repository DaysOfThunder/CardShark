package com.mouthofrandom.cardshark.graphics.utility;

/**
 * Created by coleman on 2/26/17.
 */
public interface Subject
{
    void notify(TouchEvent touchEvent);

    enum TouchEvent
    {
        SWIPE_UP, SWIPE_DOWN, SWIPE_LEFT, SWIPE_RIGHT
    }
}
