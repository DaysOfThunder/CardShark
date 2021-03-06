package com.mouthofrandom.cardshark.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.mouthofrandom.cardshark.R;

/**
 * Created by Stephen on 3/29/2017.
 */

public class RouletteWheelActivity extends AppCompatActivity
{
    public static int result = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Extract result number:
        Intent intent = getIntent();
        result = intent.getIntExtra(RouletteBoardActivity.RESULT_MESSAGE,0);

        //Spin the wheel:
        setContentView(R.layout.activity_roulette_wheel);

        //Setup return:
        Button returnButton = (Button) findViewById(R.id.RouletteReturnButton);
        final RouletteWheelView sv = (RouletteWheelView) findViewById(R.id.RouletteWheelSurfaceView);
        returnButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!sv.getRunning()) {
                    finish();
                }
            }
        });
    }
}

class RouletteWheelView extends SurfaceView implements SurfaceHolder.Callback
{
    private static int ROT_MIN_SPIN = 3;//Minimum number of times the wheel should spin.
    private static int MAX_TICKS_PER_LOC = 7;//Minimum number of ticks to spin between two locations on the wheel.
    private static int[] NUM_ORDER = {0,8,15,2,17,12,1,6,11,4,3,10,7,16,9,18,5,14,13};//Location of number on wheel (ccw from 0).

    private boolean running = false;
    private int currentLocation = 0;//Where the wheel is at.
    private int endLocation;
    private int ticksToNextLoc = 1;
    private int currentTicksPerLocChange = 1;//Speed of wheel in ticks per change between locations.
    private Bitmap wheel;
    private Bitmap arrow;
    private SurfaceHolder surfaceHolder;
    private MediaPlayer player;

    public RouletteWheelView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        //Setup:
        wheel = BitmapFactory.decodeResource(getResources(),R.drawable.rouletteboard);
        arrow = BitmapFactory.decodeResource(getResources(),R.drawable.roulette_arrow);
        endLocation = NUM_ORDER[RouletteWheelActivity.result] + ROT_MIN_SPIN*NUM_ORDER.length;//Spin several times and then go to result location.

        //Set up Music:
        player = MediaPlayer.create(context, R.raw.tick);

        getHolder().addCallback(this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder)
    {
        this.surfaceHolder = surfaceHolder;
        setWillNotDraw(false);
        Canvas canvas = surfaceHolder.lockCanvas();
        doDraw(canvas);
        surfaceHolder.unlockCanvasAndPost(canvas);
        Animation animation = new Animation(getHolder(), this);
        animation.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {}

    private boolean spinToNext()
    {
        if(ticksToNextLoc == 0)
        {
            currentLocation++;
            currentTicksPerLocChange = (int)Math.max(1,MAX_TICKS_PER_LOC*Math.pow((double)currentLocation/endLocation,1.5));
            ticksToNextLoc = currentTicksPerLocChange;
        }
        else
        {
            ticksToNextLoc--;
        }

        return currentLocation == endLocation;//End
    }

    public void setRunning(boolean t)
    {
        running = t;
    }

    public boolean getRunning()
    {
        return running;
    }

    protected void doDraw(Canvas canvas)
    {
        //Paint wheel:
        Paint paint = new Paint();
        paint.setColor(0xff016B3A);
        canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(),paint);
        Matrix scaleMatrix = new Matrix();
        Matrix rotatematrix = new Matrix();
        double partialPosition = (currentTicksPerLocChange-ticksToNextLoc)/(double)currentTicksPerLocChange;
        int degrees = (int)((360.0/19)*(currentLocation + partialPosition));
        int width = canvas.getWidth();
        int imgWidth = wheel.getWidth();
        scaleMatrix.postScale((float)width/imgWidth,(float)width/imgWidth);
        Bitmap scaleWheel = Bitmap.createBitmap(wheel,0,0,wheel.getWidth(),wheel.getHeight(),scaleMatrix,true);
        rotatematrix.postRotate(degrees,width/2,width/2);
        canvas.drawBitmap(scaleWheel,rotatematrix,null);

        //Paint Arrow:
        Matrix arrowScaleMatrix = new Matrix();
        int arrowWidth = arrow.getWidth();
        int arrowHeight = arrow.getHeight();
        arrowScaleMatrix.postScale((float)0.25*(float)width/arrowWidth,(float)0.1*(float)width/arrowWidth);
        Bitmap scaleArrow = Bitmap.createBitmap(arrow,0,0,arrow.getWidth(),arrow.getHeight(),arrowScaleMatrix,true);
        canvas.drawBitmap(scaleArrow,(int)(width*0.8),(int)(width*0.475),null);
    }

    private class Animation extends Thread
    {
        private final SurfaceHolder surfaceHolder;

        private final RouletteWheelView rouletteWheelView;

        private boolean running = false;
        private boolean paused = false;

        Animation(SurfaceHolder surfaceHolder, RouletteWheelView rouletteWheelView)
        {
            this.surfaceHolder = surfaceHolder;

            this.rouletteWheelView = rouletteWheelView;
        }

        @Override
        public void start()
        {
            super.start();

            running = true;
            rouletteWheelView.setRunning(true);
        }

        void flagStop()
        {
            player.stop();
            synchronized(surfaceHolder)
            {
                running = false;
                rouletteWheelView.setRunning(false);
                paused = false;
                surfaceHolder.notifyAll();
            }
        }

        void onPause()
        {
            player.stop();
            synchronized(surfaceHolder)
            {
                paused = true;
            }
        }

        void onRestart()
        {
            synchronized (surfaceHolder)
            {
                paused = false;
                surfaceHolder.notifyAll();
            }
        }

        long timeNow;
        long timePrevFrame = 0;
        long timeDelta;

        @Override
        public void run()
        {
            Canvas canvas = null;

            while(running && !spinToNext())
            {
                timeNow = System.currentTimeMillis();
                timeDelta = timeNow - timePrevFrame;

                if(timeDelta < 16)
                {
                    try
                    {
                        Thread.sleep(16 - timeDelta);
                    }
                    catch (InterruptedException e)
                    {
                        System.err.println("Thread Interrupted");
                    }
                }

                timePrevFrame = System.currentTimeMillis();

                try
                {
                    canvas = surfaceHolder.lockCanvas();

                    synchronized(surfaceHolder)
                    {
                        rouletteWheelView.doDraw(canvas);
                    }
                }
                finally
                {
                    if(null != canvas)
                    {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    if(ticksToNextLoc == currentTicksPerLocChange)
                    {
                        player.start();
                    }
                }

                synchronized(surfaceHolder)
                {
                    while(paused)
                    {
                        try
                        {
                            surfaceHolder.wait();
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
            running = false;
            rouletteWheelView.setRunning(false);
        }
    }
}