package com.mouthofrandom.cardshark.graphics.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.Dimension;

import com.mouthofrandom.cardshark.R;

import java.io.InputStream;

/**
 * Created by coleman on 2/22/17.
 */

public class Tile implements Drawable
{
    private boolean isInitialized = false;

    private Bitmap bitmap = null;

    private boolean isWalkable = false;

    private GameAction gameAction = null;

    public Tile(Context context, InputStream inputStream, boolean isWalkable, GameAction gameAction)
    {
        if(context != null)
        {
            isInitialized = true;
        }

        int tiles = context.getResources().getDimensionPixelSize(R.dimen.tiles);

        bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeStream(inputStream), 400, 400, false);

        this.isWalkable = isWalkable;

        this.gameAction = gameAction;
    }

    @Override
    public void draw(Canvas canvas, Matrix matrix) throws DrawableNotInitializedException
    {
        if(!isInitialized)
        {
            throw new DrawableNotInitializedException(this.getClass());
        }

        canvas.drawBitmap(bitmap, matrix, null);
    }
}
