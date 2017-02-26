package com.mouthofrandom.cardshark.graphics.utility;

import android.graphics.Canvas;
import android.graphics.Matrix;

/**
 * Created by coleman on 2/21/17.
 */

public interface Drawable
{
    /**
     * This method accepts the drawing canvas for the current view and attempts to draw the
     * object representation
     * @param canvas - A canvas object from the parent view.
     * @throws DrawableNotInitializedException - if the object has not been properly initialized to
     * use the resources from that view.
     */
    void draw(Canvas canvas) throws DrawableNotInitializedException;

    class DrawableNotInitializedException extends Exception
    {
        public DrawableNotInitializedException(Class c)
        {
            System.err.println(
                    "Drawable with class " +
                    c.getName() +
                    "has not been properly initialized."
            );
        }
    }
}
