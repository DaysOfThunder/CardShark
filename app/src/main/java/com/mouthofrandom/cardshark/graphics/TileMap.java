package com.mouthofrandom.cardshark.graphics;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

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

import static com.mouthofrandom.cardshark.graphics.TileMap.Direction.*;

public class TileMap implements Observer
{
    private static final int DIMENSIONS = 416;
    private static final int FRAMES = 32;
    private static final int offset_x = (-DIMENSIONS/2) + (Resources.getSystem().getDisplayMetrics().widthPixels/2);
    private static final int offset_y = (-DIMENSIONS/2) + (DIMENSIONS/3) + (Resources.getSystem().getDisplayMetrics().heightPixels/2);

    private List<Tile> tiles;
    private int[][] map;

    private int width;
    private int height;
    private int position_x;
    private int position_y;

    private int walk_offset_x = 0;
    private int walk_offset_y = 0;

    private Direction current;
    private boolean isRunning = false;
    private int frameCount = 0;

    public TileMap(Context context)
    {
        AssetManager assetManager = context.getAssets();

        String prefix = "tiles";

        tiles = new ArrayList<>();

        try
        {
            for(String path : assetManager.list(prefix))
            {
                tiles.add(new Tile(assetManager.open(prefix + "/" + path), path.contains("walk")));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            CSVParser mapParser = new CSVParser(new InputStreamReader(assetManager.open("maps/outside.csv")), CSVFormat.DEFAULT);

            List<CSVRecord> rows = mapParser.getRecords();

            width = Integer.parseInt(rows.get(0).get(0));
            height = Integer.parseInt(rows.get(0).get(1));
            position_x = Integer.parseInt(rows.get(0).get(2));
            position_y = Integer.parseInt(rows.get(0).get(3));

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
        for(int i = 0; i < width; i++)
        {
            for(int j = 0; j < height; j++)
            {
                Tile tile = tiles.get(map[i][j]);
                canvas.drawBitmap(tile.getBitmap(),
                        (i * DIMENSIONS) + walk_offset_x + offset_x - (position_x * DIMENSIONS),
                        (j * DIMENSIONS) + walk_offset_y + offset_y - (position_y * DIMENSIONS),
                        null);
            }
        }
    }

    @Override
    public void update(Subject.TouchEvent touchEvent)
    {
        start(new TileMapAnimationArguments(touchEvent));
    }

    @Override
    public void start(AnimationArguments animationArgs)
    {
        current = ((TileMapAnimationArguments)animationArgs).direction;

        boolean isWalkable = false;

        switch(current)
        {
            case UP:
                isWalkable = tiles.get(map[position_x][position_y + 1]).isWalkable();
                break;
            case DOWN:
                isWalkable = tiles.get(map[position_x][position_y - 1]).isWalkable();
                break;
            case LEFT:
                isWalkable = tiles.get(map[position_x - 1][position_y]).isWalkable();
                break;
            case RIGHT:
                isWalkable = tiles.get(map[position_x + 1][position_y]).isWalkable();
                break;
        }

        if(isWalkable)
        {
            isRunning = true;
        }
        else
        {
            current = null;
        }
    }

    @Override
    public void next()
    {
        switch(current)
        {
            case UP:
                walk_offset_y += DIMENSIONS/FRAMES;
                break;
            case DOWN:
                walk_offset_y -= DIMENSIONS/FRAMES;
                break;
            case LEFT:
                walk_offset_x -= DIMENSIONS/FRAMES;
                break;
            case RIGHT:
                walk_offset_x += DIMENSIONS/FRAMES;
                break;
        }

        frameCount++;
    }

    @Override
    public boolean isRunning()
    {
        return isRunning;
    }

    @Override
    public boolean isComplete()
    {
        return frameCount > FRAMES;
    }

    @Override
    public void finish()
    {
        switch(current)
        {
            case UP:
                position_y++;
                break;
            case DOWN:
                position_y--;
                break;
            case LEFT:
                position_x--;
                break;
            case RIGHT:
                position_x++;
                break;
        }

        frameCount = 0;
        current = null;
        isRunning = false;

        walk_offset_x = 0;
        walk_offset_y = 0;
    }

    private static class TileMapAnimationArguments implements AnimationArguments
    {
        Direction direction;

        TileMapAnimationArguments(Subject.TouchEvent touchEvent)
        {
            switch(touchEvent)
            {
                case SWIPE_UP:
                    direction = UP;
                    break;
                case SWIPE_DOWN:
                    direction = DOWN;
                    break;
                case SWIPE_LEFT:
                    direction = LEFT;
                    break;
                case SWIPE_RIGHT:
                    direction = RIGHT;
                    break;
            }
        }
    }

    enum Direction
    {
        UP, DOWN, LEFT, RIGHT
    }

    private static class Tile
    {
        private Bitmap bitmap = null;

        private boolean isWalkable = false;

        Tile(InputStream inputStream, boolean isWalkable)
        {
            bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeStream(inputStream), DIMENSIONS, DIMENSIONS, false);

            this.isWalkable = isWalkable;
        }

        Bitmap getBitmap()
        {
            return bitmap;
        }

        boolean isWalkable()
        {
            return isWalkable;
        }
    }
}
