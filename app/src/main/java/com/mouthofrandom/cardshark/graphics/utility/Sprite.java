package com.mouthofrandom.cardshark.graphics.utility;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.mouthofrandom.cardshark.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by coleman on 2/21/17.
 */

class Sprite implements Drawable
{
    enum Direction
    {
        LEFT, RIGHT, BACK, FRONT
    }

    private boolean wasInitialized = false;

    private final Map<String, Bitmap> bitmaps;

    private static final String
            FRONT_STANDING = "front_standing",
            BACK_STANDING = "back_standing",
            LEFT_STANDING = "left_standing",
            RIGHT_STANDING = "right_standing",
            FRONT_WALK_LEFT = "front_walk_left",
            FRONT_WALK_RIGHT = "front_walk_right",
            BACK_WALK_LEFT = "back_walk_left",
            BACK_WALK_RIGHT = "back_walk_right",
            LEFT_WALK_LEFT = "left_walk_left",
            LEFT_WALK_RIGHT = "left_walk_right",
            RIGHT_WALK_LEFT = "right_walk_left",
            RIGHT_WALK_RIGHT = "right_walk_right";

    private Direction current = Direction.FRONT;

    private Bitmap bitmap = null;

    Sprite(Resources res)
    {
        bitmaps = new HashMap<>();

        bitmaps.put(FRONT_STANDING, BitmapFactory.decodeResource(res, R.mipmap.trainer));

        bitmap = bitmaps.get(FRONT_STANDING);

        wasInitialized = true;
    }

    public void walk()
    {
        switch(current)
        {
            case LEFT:

                if(bitmap == bitmaps.get(LEFT_WALK_RIGHT))
                {
                    bitmap = bitmaps.get(LEFT_WALK_LEFT);
                }
                else
                {
                    bitmap = bitmaps.get(LEFT_WALK_RIGHT);
                }

                break;

            case RIGHT:

                if(bitmap == bitmaps.get(RIGHT_WALK_RIGHT))
                {
                    bitmap = bitmaps.get(RIGHT_WALK_LEFT);
                }
                else
                {
                    bitmap = bitmaps.get(RIGHT_WALK_RIGHT);
                }

                break;

            case BACK:

                if(bitmap == bitmaps.get(BACK_WALK_RIGHT))
                {
                    bitmap = bitmaps.get(BACK_WALK_LEFT);
                }
                else
                {
                    bitmap = bitmaps.get(BACK_WALK_RIGHT);
                }

                break;

            case FRONT:

                if(bitmap == bitmaps.get(FRONT_WALK_RIGHT))
                {
                    bitmap = bitmaps.get(FRONT_WALK_LEFT);
                }
                else
                {
                    bitmap = bitmaps.get(FRONT_WALK_RIGHT);
                }

                break;
        }
    }

    public void stop()
    {
        switch(current)
        {
            case LEFT:
                bitmap = bitmaps.get(LEFT_STANDING);
                break;
            case RIGHT:
                bitmap = bitmaps.get(RIGHT_STANDING);
                break;
            case BACK:
                bitmap = bitmaps.get(BACK_STANDING);
                break;
            case FRONT:
                bitmap = bitmaps.get(FRONT_STANDING);
                break;
        }
    }

    public void turn(Direction direction)
    {
        current = direction;

        stop();
    }

    @Override
    public void draw(Canvas canvas) throws DrawableNotInitializedException
    {
        if(!wasInitialized)
        {
            throw new DrawableNotInitializedException(this.getClass());
        }

        canvas.drawBitmap(bitmap, 576, 864, null);
    }
}
