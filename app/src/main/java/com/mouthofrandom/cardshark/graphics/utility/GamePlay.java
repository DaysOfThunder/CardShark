package com.mouthofrandom.cardshark.graphics.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by coleman on 2/18/17.
 */

public class GamePlay extends SurfaceView implements SurfaceHolder.Callback
{
    private Bitmap myChar;
    private SurfaceHolder holder;

    public GamePlay(Context context)
    {
        super(context);

        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawColor(Color.BLUE);
        canvas.drawBitmap(myChar, 10, 10, null);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder)
    {
        Canvas canvas = holder.lockCanvas(null);
        draw(canvas);
        holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2)
    {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder)
    {

    }
    
}
