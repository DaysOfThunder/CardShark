package com.mouthofrandom.cardshark.graphics.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.mouthofrandom.cardshark.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by coleman on 2/21/17.
 */

class Sprite implements Drawable
{
    public static int WALK_FRAMES = 32;
    public static int WALK_TIME = 200;

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

    private int walkCount = 0;

    Sprite(Context context)
    {
        bitmaps = new HashMap<>();

        Matrix m = new Matrix();
        Bitmap temp;

        bitmaps.put(FRONT_STANDING, BitmapFactory.decodeResource(context.getResources(), R.mipmap.front_standing));
        bitmaps.put(BACK_STANDING, BitmapFactory.decodeResource(context.getResources(), R.mipmap.back_standing));

        temp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.side_standing);

        bitmaps.put(LEFT_STANDING, BitmapFactory.decodeResource(context.getResources(), R.mipmap.side_standing));

        m.setScale(-1, 1);

        bitmaps.put(RIGHT_STANDING, Bitmap.createBitmap(temp, 0, 0, temp.getWidth(), temp.getHeight(), m, true));

        m = new Matrix();

        temp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.front_walking);

        bitmaps.put(FRONT_WALK_LEFT, temp);

        m.setScale(-1, 1);

        bitmaps.put(FRONT_WALK_RIGHT, Bitmap.createBitmap(temp, 0, 0, temp.getWidth(), temp.getHeight(), m, true));

        m = new Matrix();

        temp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.back_walking);

        bitmaps.put(BACK_WALK_LEFT, temp);

        m.setScale(-1, 1);

        bitmaps.put(BACK_WALK_RIGHT, Bitmap.createBitmap(temp, 0, 0, temp.getWidth(), temp.getHeight(), m, true));

        m = new Matrix();

        temp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.side_walking);

        bitmaps.put(LEFT_WALK_LEFT, temp);

        bitmap = bitmaps.get(FRONT_STANDING);

        m.setScale(-1, 1);

        bitmaps.put(RIGHT_WALK_RIGHT, Bitmap.createBitmap(temp, 0, 0, temp.getWidth(), temp.getHeight(), m, true));

        m = new Matrix();

        temp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.side_walking_2);

        bitmaps.put(LEFT_WALK_RIGHT, temp);

        bitmap = bitmaps.get(FRONT_STANDING);

        m.setScale(-1, 1);

        bitmaps.put(RIGHT_WALK_LEFT, Bitmap.createBitmap(temp, 0, 0, temp.getWidth(), temp.getHeight(), m, true));

        wasInitialized = true;
    }

    public void walk()
    {
        walkCount++;

        if(walkCount != WALK_FRAMES/2 && walkCount != 1)
        {
            return;
        }

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

        if(walkCount == WALK_FRAMES)
        {
            stop();
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

        walkCount = 0;
    }

    public void turn(Direction direction)
    {
        current = direction;

        stop();
    }

    public int getWalkCount()
    {
        return walkCount;
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
