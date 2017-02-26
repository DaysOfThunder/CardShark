package com.mouthofrandom.cardshark.graphics;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.mouthofrandom.cardshark.R;
import com.mouthofrandom.cardshark.graphics.utility.Animatable;
import com.mouthofrandom.cardshark.graphics.utility.Drawable;
import com.mouthofrandom.cardshark.graphics.utility.Observer;
import com.mouthofrandom.cardshark.graphics.utility.Subject;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by coleman on 2/22/17.
 */

public class TileMap implements Observer
{
    private boolean isInitialized = false;

    private List<Tile> tiles;

    private int[][] map;

    private int width;
    private int height;

    private static final int SCREEN_WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    private static final int SCREEN_HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;

    public int offset_x = 0;
    public int offset_y = 0;

    public TileMap(Context context, InputStream inputStream)
    {
        if(context != null)
        {
            isInitialized = true;
        }

        AssetManager assetManager = context.getAssets();

        String prefix = "tiles";

        tiles = new ArrayList<>();

        try
        {
            for(String path : assetManager.list(prefix))
            {
                //tiles.add(new Tile(context, assetManager.open(prefix + "/" + path), path.contains("walk"), null));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            CSVParser mapParser = new CSVParser(new InputStreamReader(inputStream), CSVFormat.DEFAULT);

            List<CSVRecord> rows = mapParser.getRecords();

            width = Integer.parseInt(rows.get(0).get(0));
            height = Integer.parseInt(rows.get(0).get(1));

            offset_x = Integer.parseInt(rows.get(0).get(2)) * 400;
            offset_y = Integer.parseInt(rows.get(0).get(3)) * 400;

            map = new int[width][height];

            for(int i = 0; i < width; i++)
            {
                for(int j = 0; j < height; j++)
                {
                    map[i][j] = Integer.parseInt(rows.get(i + 1).get(j));
                }
            }

            mapParser.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Canvas canvas) throws DrawableNotInitializedException
    {
        if(!isInitialized)
        {
            throw new DrawableNotInitializedException(this.getClass());
        }

        Matrix _matrix = new Matrix();

        for(int i = 0; i < width; i++)
        {
            for(int j = 0; j < height; j++)
            {
                _matrix.setTranslate((400 * i) + offset_x, (400 * j) + offset_y);

                //tiles.get(map[i][j]).draw(canvas);
            }
        }
    }

    @Override
    public void update(Subject.TouchEvent touchEvent) {

    }

    @Override
    public void start(AnimationArguments animationArgs) {

    }

    @Override
    public void next() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public void finish() {

    }

    private static class Tile
    {
        private Bitmap bitmap = null;

        private boolean isWalkable = false;

        public Tile(Context context, InputStream inputStream, boolean isWalkable)
        {
            int tiles = context.getResources().getDimensionPixelSize(R.dimen.tiles);

            bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeStream(inputStream), 400, 400, false);

            this.isWalkable = isWalkable;
        }
    }
}
