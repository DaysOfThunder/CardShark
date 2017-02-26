package com.mouthofrandom.cardshark.graphics.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

import com.mouthofrandom.cardshark.R;

import java.io.IOException;

public class CanvasView extends View implements View.OnTouchListener {
    Context context;

    private BitmapFactory.Options tileStats = new BitmapFactory.Options();
    private Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.poketestmapbitbig, tileStats);

    int scaleFac = 2;
    int x = 0;
    int y = 0;

    float x1;
    float x2;
    float y1;
    float y2;

    int dim = 288;

    static final int MIN_DISTANCE = 150;

    Sprite.Direction walking = null;

    long startTime;

    int tilesetDimension = 400;

    private Bitmap nBitmap = Bitmap.createScaledBitmap(mBitmap, mBitmap.getWidth() * scaleFac, mBitmap.getHeight() * scaleFac, false);

    private Sprite trainer;

    private TileMap map;

    public CanvasView(Context c, AttributeSet attrs)
    {
        super(c, attrs);

        trainer = new Sprite(c);

        try
        {
            map = new TileMap(c, c.getAssets().open("maps/outside.csv"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        context = c;

        this.setOnTouchListener(this);

        // start the animation:
        this.startTime = System.currentTimeMillis();
        this.postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        canvas.drawRGB(256, 256, 256);

        if(null != walking && trainer.getWalkCount() < Sprite.WALK_FRAMES)
        {
            animateWalk();
        }
        else
        {
            walking = null;
            trainer.stop();
        }

        try
        {
            map.draw(canvas);
            //trainer.draw(canvas, null);
        }
        catch (Drawable.DrawableNotInitializedException e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        switch(motionEvent.getAction())
        {
            case MotionEvent.ACTION_DOWN:

                x1 = motionEvent.getX();
                y1 = motionEvent.getY();

                return true;

            case MotionEvent.ACTION_UP:

                x2 = motionEvent.getX();
                y2 = motionEvent.getY();
                float deltaX = x2 - x1;
                float deltaY = y2 - y1;

                if (Math.abs(deltaX) > MIN_DISTANCE)
                {
                    if (x2 > x1)
                    {
                        walking = Sprite.Direction.RIGHT;
                        trainer.turn(walking);
                        animateWalk();
                    }
                    else
                    {
                        walking = Sprite.Direction.LEFT;
                        trainer.turn(walking);
                        animateWalk();
                    }
                }
                else if(Math.abs(deltaY) > MIN_DISTANCE)
                {
                    if(y2 > y1)
                    {
                        walking = Sprite.Direction.BACK;
                        trainer.turn(walking);
                        animateWalk();
                    }
                    else
                    {
                        walking = Sprite.Direction.FRONT;
                        trainer.turn(walking);
                        animateWalk();
                    }
                }
                break;
        }

        invalidate();

        return super.onTouchEvent(motionEvent);

    }

    private void animateWalk()
    {
        switch(walking)
        {
            case RIGHT:
                map.offset_x += dim/Sprite.WALK_FRAMES; // move 100 pixels to the right
                break;
            case LEFT:
                map.offset_x -= dim/Sprite.WALK_FRAMES; // move 100 pixels to the left
                break;
            case BACK:
                map.offset_y += dim/Sprite.WALK_FRAMES; // move 100 pixels to the up
                break;
            case FRONT:
                map.offset_y -= dim/Sprite.WALK_FRAMES; // move 100 pixels to the down
                break;
        }

        trainer.walk();

        this.postInvalidateDelayed(Sprite.WALK_TIME/Sprite.WALK_FRAMES);
    }
}
