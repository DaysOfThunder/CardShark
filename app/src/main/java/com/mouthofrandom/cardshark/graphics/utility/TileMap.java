package com.mouthofrandom.cardshark.graphics.utility;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Point;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by coleman on 2/22/17.
 */

public class TileMap implements Drawable
{
    private boolean isInitialized = false;

    private List<Tile> tiles;

    private int[][] map;

    private int width;
    private int height;

    private Point startingPosition;

    public TileMap(Context context, InputStream inputStream)
    {
        if(context != null)
        {
            isInitialized = true;
        }

        AssetManager assetManager = context.getAssets();

        String prefix = "assets/tiles";

        try
        {
            for(String path : assetManager.list(prefix))
            {
                tiles.add(new Tile(context, assetManager.open(prefix + "/" + path), path.contains("walk"), null));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        CSVParser mapParser = null;

        try
        {
            mapParser = new CSVParser(new InputStreamReader(inputStream), CSVFormat.DEFAULT);

            List<CSVRecord> rows = mapParser.getRecords();

            width = Integer.parseInt(rows.get(0).get(0));
            height = Integer.parseInt(rows.get(0).get(1));

            startingPosition = new Point(
                    Integer.parseInt(rows.get(0).get(2)),
                    Integer.parseInt(rows.get(0).get(3)));

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

        assetManager.close();
    }

    @Override
    public void draw(Canvas canvas) throws DrawableNotInitializedException
    {
        if(!isInitialized)
        {
            throw new DrawableNotInitializedException(this.getClass());
        }

        for(int i = 0; i < width; i++)
        {
            for(int j = 0; j < height; j++)
            {
                tiles.get(map[i][j]).draw(canvas);
            }
        }
    }
}
