package com.mouthofrandom.cardshark.graphics.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.mouthofrandom.cardshark.R;

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

    int walkFrames = 32;
    int walkCount = 0;
    Walk walking = null;
    int walkTime = 300;

    long startTime;

    int tilesetDimension = 400;

    private Bitmap nBitmap = Bitmap.createScaledBitmap(mBitmap, mBitmap.getWidth() * scaleFac, mBitmap.getHeight() * scaleFac, false);

    private Bitmap oBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.trainer);

    public CanvasView(Context c, AttributeSet attrs)
    {
        super(c, attrs);
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

        canvas.drawBitmap(nBitmap, x, y, null);

        canvas.drawBitmap(oBitmap, dim * 2, dim * 3, null);

        /*
        ArrayList<Bitmap> tileset = new ArrayList<Bitmap>();


        for (int row = 0; row < (mBitmap.getHeight() / tilesetDimension) ; row++){
            for (int column = 0; column < (mBitmap.getWidth() / tilesetDimension); column++){
                tileset.add(Bitmap.createBitmap(mBitmap, (column * tilesetDimension), (row * tilesetDimension), tilesetDimension, tilesetDimension));

        */

        if(null != walking && walkCount < walkFrames)
        {
            animateWalk();
        }
        else
        {
            walking = null;
            walkCount = 0;
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
                        walking = Walk.RIGHT;
                        animateWalk();
                    }
                    else
                    {
                        walking = Walk.LEFT;
                        animateWalk();
                    }
                }
                else if(Math.abs(deltaY) > MIN_DISTANCE)
                {
                    if(y2 > y1)
                    {
                        walking = Walk.UP;
                        animateWalk();
                    }
                    else
                    {
                        walking = Walk.DOWN;
                        animateWalk();
                    }
                }
                break;
        }

        invalidate();

        return super.onTouchEvent(motionEvent);

    }

    private enum Walk
    {
        LEFT, RIGHT, UP, DOWN
    }

    private void animateWalk()
    {
        switch(walking)
        {
            case RIGHT:
                x += dim/walkFrames; // move 100 pixels to the right
                break;
            case LEFT:
                x -= dim/walkFrames; // move 100 pixels to the left
                break;
            case UP:
                y += dim/walkFrames; // move 100 pixels to the up
                break;
            case DOWN:
                y -= dim/walkFrames; // move 100 pixels to the down
                break;
        }

        walkCount++;

        this.postInvalidateDelayed(walkTime/walkFrames);
    }
}
