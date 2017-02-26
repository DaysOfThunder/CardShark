package com.mouthofrandom.cardshark;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import com.mouthofrandom.cardshark.graphics.utility.Animatable;
import com.mouthofrandom.cardshark.graphics.utility.Drawable;
import com.mouthofrandom.cardshark.graphics.utility.Sprite;

import java.util.ArrayList;
import java.util.List;

import static com.mouthofrandom.cardshark.graphics.utility.Sprite.*;


public class CasinoView extends SurfaceView implements SurfaceHolder.Callback
{
    Animation animation;

    List<Animatable> animatables;

    public CasinoView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        animatables = new ArrayList<>();

        animatables.add(new Sprite(context));

        getHolder().addCallback(this);

        setFocusable(true);
    }

    @Override
    public void onSizeChanged(int newHeight, int newWidth, int oldHeight, int oldWidth)
    {
        // When the screen rotates.
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        try
        {
            for(Animatable animatable : animatables)
            {
                if(!animatable.isComplete())
                {
                    animatable.next();
                }

                animatable.draw(canvas);
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

                Sprite.SpriteAnimationArguments args = new Sprite.SpriteAnimationArguments();

                if (Math.abs(deltaX) > MIN_DISTANCE)
                {
                    if (touch_x_end > touch_x_start)
                    {
                        args.direction = Direction.RIGHT;
                        trainer.start(args);
                    }
                    else
                    {
                        args.direction = Direction.LEFT;
                        trainer.start(args);
                    }
                }
                else if(Math.abs(deltaY) > MIN_DISTANCE)
                {
                    if(touch_y_end > touch_y_start)
                    {
                        args.direction = Direction.BACK;
                        trainer.start(args);
                    }
                    else
                    {
                        args.direction = Direction.FRONT;
                        trainer.start(args);
                    }
                }
                break;
        }

        return false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder)
    {
        animation = new Animation(getHolder(), this);
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

    private class Animation extends Thread
    {
        private final SurfaceHolder surfaceHolder;

        private final CasinoView casinoView;

        private boolean running = false;

        Animation(SurfaceHolder surfaceHolder, CasinoView casinoView)
        {
            this.surfaceHolder = surfaceHolder;

            this.casinoView = casinoView;
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
                    canvas = surfaceHolder.lockCanvas(null);

                    synchronized (surfaceHolder)
                    {
                        casinoView.onDraw(canvas);
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
