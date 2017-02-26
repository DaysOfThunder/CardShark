package com.mouthofrandom.cardshark.graphics.utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressLint("ViewConstructor")
public class CardSharkView extends SurfaceView implements SurfaceHolder.Callback, Subject
{
    CardSharkView.Animation animation;
    List<Drawable> elements;

    public CardSharkView(Context context, AttributeSet attrs, Drawable... drawables)
    {
        super(context, attrs);

        elements = new ArrayList<>();

        Collections.addAll(elements, drawables);

        getHolder().addCallback(this);

        setFocusable(true);
    }

    @Override
    public void onSizeChanged(int newHeight, int newWidth, int oldHeight, int oldWidth)
    {
        // When the screen rotates.
    }

    public void doDraw(Canvas canvas)
    {
        super.draw(canvas);

        try
        {
            for(Drawable drawable : elements)
            {
                if(drawable instanceof Animatable)
                {
                    Animatable animatable = (Animatable) drawable;

                    if(animatable.isRunning())
                    {
                        if(animatable.isComplete())
                        {
                            animatable.finish();
                        }
                        else
                        {
                            animatable.next();
                        }
                    }
                }

                drawable.draw(canvas);

            }

        }
        catch (Drawable.DrawableNotInitializedException e)
        {
            e.printStackTrace();
        }
    }

    private float touch_x_start;
    private float touch_y_start;
    private static final int MIN_DISTANCE = 150;

    @Override
    public synchronized boolean onTouchEvent(MotionEvent motionEvent)
    {
        switch(motionEvent.getAction())
        {
            case MotionEvent.ACTION_DOWN:

                touch_x_start = motionEvent.getX();
                touch_y_start = motionEvent.getY();

                return true;

            case MotionEvent.ACTION_UP:

                float touch_x_end = motionEvent.getX();
                float touch_y_end = motionEvent.getY();
                float deltaX = touch_x_end - touch_x_start;
                float deltaY = touch_y_end - touch_y_start;

                if (Math.abs(deltaX) > MIN_DISTANCE)
                {
                    if (touch_x_end > touch_x_start)
                    {
                        notify(TouchEvent.SWIPE_RIGHT);
                    }
                    else
                    {
                        notify(TouchEvent.SWIPE_LEFT);
                    }
                }
                else if(Math.abs(deltaY) > MIN_DISTANCE)
                {
                    if(touch_y_end > touch_y_start)
                    {
                        notify(TouchEvent.SWIPE_UP);
                    }
                    else
                    {
                        notify(TouchEvent.SWIPE_DOWN);
                    }
                }
                break;
        }

        return false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder)
    {
        animation = new CardSharkView.Animation(getHolder(), this);
        animation.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder)
    {
        boolean retry = true;

        animation.flagStop();

        while (retry)
        {
            try
            {
                animation.join();
                retry = false;
            }
            catch (InterruptedException e)
            {
                System.err.println("Thread Interrupted");
            }
        }
    }

    @Override
    public void notify(TouchEvent touchEvent)
    {
        for(Drawable element : elements)
        {
            if(element instanceof Observer)
            {
                ((Observer) element).update(touchEvent);
            }
        }
    }

    private class Animation extends Thread
    {
        private final SurfaceHolder surfaceHolder;

        private final CardSharkView cardSharkView;

        private boolean running = false;

        Animation(SurfaceHolder surfaceHolder, CardSharkView cardSharkView)
        {
            this.surfaceHolder = surfaceHolder;

            this.cardSharkView = cardSharkView;
        }

        @Override
        public void start()
        {
            super.start();

            running = true;
        }

        void flagStop()
        {
            running = false;
        }

        long timeNow;
        long timePrevFrame = 0;
        long timeDelta;

        @Override
        public void run()
        {
            Canvas canvas = null;

            while (running)
            {
                timeNow = System.currentTimeMillis();

                timeDelta = timeNow - timePrevFrame;

                if(timeDelta < 16)
                {
                    try
                    {
                        Thread.sleep(16 - timeDelta);
                    }
                    catch(InterruptedException e)
                    {
                        System.err.println("Thread Interrupted");
                    }
                }

                timePrevFrame = System.currentTimeMillis();

                try
                {
                    canvas = surfaceHolder.lockCanvas();

                    synchronized (surfaceHolder)
                    {
                        cardSharkView.doDraw(canvas);
                    }
                }
                finally
                {
                    if (null != canvas)
                    {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }
}
