package com.mouthofrandom.cardshark.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.mouthofrandom.cardshark.R;
import com.mouthofrandom.cardshark.graphics.utility.Observer;
import com.mouthofrandom.cardshark.graphics.utility.Subject;

import java.util.HashMap;
import java.util.Map;

import static com.mouthofrandom.cardshark.graphics.Sprite.Direction.*;

public class Sprite implements Observer
{
    private static int WALK_FRAMES = 32;

    private boolean isRunning = false;

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

    private Direction current = FRONT;

    private Bitmap bitmap = null;

    private int walkCount = 0;

    private int draw_x;
    private int draw_y;

    public Sprite(Context context)
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

        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);

        draw_x = (metrics.widthPixels/2) - (bitmap.getWidth()/2);
        draw_y = (metrics.heightPixels/2) - (bitmap.getHeight()/2);

        wasInitialized = true;
    }

    @Override
    public void draw(Canvas canvas) throws DrawableNotInitializedException
    {
        if(!wasInitialized)
        {
            throw new DrawableNotInitializedException(this.getClass());
        }

        canvas.drawBitmap(bitmap, draw_x, draw_y, null);
    }

    @Override
    public void start(AnimationArguments animationArgs)
    {
        current = ((SpriteAnimationArguments)animationArgs).direction;

        isRunning = true;
    }

    @Override
    public void next()
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
    }

    @Override
    public boolean isRunning()
    {
        return isRunning;
    }

    @Override
    public boolean isComplete()
    {
        return walkCount > WALK_FRAMES;
    }

    @Override
    public void finish()
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

        isRunning = false;
    }

    @Override
    public void update(Subject.Event event)
    {
        if(event == Subject.TouchEvent.TOUCH_BUTTON)
        {
            return;
        }

        start(new SpriteAnimationArguments(((Subject.TouchEvent)event)));
    }

    private static class SpriteAnimationArguments implements AnimationArguments
    {
        SpriteAnimationArguments(Subject.TouchEvent touchEvent)
        {
            switch(touchEvent)
            {
                case SWIPE_UP:
                    direction = BACK;
                    break;
                case SWIPE_DOWN:
                    direction = FRONT;
                    break;
                case SWIPE_LEFT:
                    direction = LEFT;
                    break;
                case SWIPE_RIGHT:
                    direction = RIGHT;
                    break;
            }
        }

        Direction direction;
    }
}
