package com.mouthofrandom.cardshark.graphics;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.mouthofrandom.cardshark.fragments.CurrencyBar;
import com.mouthofrandom.cardshark.graphics.utility.Informer;
import com.mouthofrandom.cardshark.graphics.utility.Listener;
import com.mouthofrandom.cardshark.graphics.utility.Observer;
import com.mouthofrandom.cardshark.graphics.utility.Subject;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static com.mouthofrandom.cardshark.graphics.TileMap.Direction.DOWN;
import static com.mouthofrandom.cardshark.graphics.TileMap.Direction.LEFT;
import static com.mouthofrandom.cardshark.graphics.TileMap.Direction.RIGHT;
import static com.mouthofrandom.cardshark.graphics.TileMap.Direction.UP;

public class TileMap implements Observer, Subject, Informer
{
    private static final int DIMENSIONS = 416;
    private static final int FRAMES = 32;
    private static final int offset_x = (-DIMENSIONS/2) + (Resources.getSystem().getDisplayMetrics().widthPixels/2);
    private static final int offset_y = (-DIMENSIONS/2) + (DIMENSIONS/3) + (Resources.getSystem().getDisplayMetrics().heightPixels/2);
    private HashMap<Integer, Tile> tiles = new HashMap<Integer, Tile>();
    //private List<Tile> tiles;
    private int[][] map;

    private int width;
    private int height;
    private int position_x;
    private int position_y;

    private int walk_offset_x = 0;
    private int walk_offset_y = 0;

    private Direction current;
    private ActionEvent currentEvent;
    private boolean isRunning = false;
    private int frameCount = 0;
    private Collection<Observer> observers;
    private Collection<Listener> listeners;

    public TileMap(Context context, Observer... observers)
    {
        this.observers = new ArrayList<>();
        this.listeners = new ArrayList<>();

        this.observers.addAll(Arrays.asList(observers));

        this.listeners.add(CurrencyBar.CURRENCY_BAR);

        AssetManager assetManager = context.getAssets();

        String prefix = "tilez";

        //tiles = new ArrayList<>();


        try
        {
            String[] tilefiles = assetManager.list(prefix);
            for(String path : tilefiles)
            {

                tiles.put(Integer.parseInt(path.substring(0,3)), new Tile(assetManager.open(prefix + "/" + path), path.contains("walk"), ActionEvent.match(path)));


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
            position_y = Integer.parseInt(rows.get(0).get(2));
            position_x = Integer.parseInt(rows.get(0).get(3));

            current = DOWN;

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

        notify(tiles.get(map[position_x][position_y + 1]).actionEvent);
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
    public void update(Subject.Event event)
    {
        if(event == TouchEvent.TOUCH_BUTTON)
        {
            return;
        }

        start(new TileMapAnimationArguments(((Subject.TouchEvent) event)));
    }

    @Override
    public void start(AnimationArguments animationArgs)
    {
        current = ((TileMapAnimationArguments)animationArgs).direction;

        boolean isWalkable = false;

        int facing_x = 0;
        int facing_y = 0;

        switch(current)
        {

            case UP:
                isWalkable = tiles.get(map[position_x][position_y - 1]).isWalkable();
                facing_x = position_x;
                facing_y = position_y - (isWalkable ? 2 : 1);
                break;
            case DOWN:
                isWalkable = tiles.get(map[position_x][position_y + 1]).isWalkable();
                facing_x = position_x;
                facing_y = position_y + (isWalkable ? 2 : 1);
                break;
            case LEFT:
                isWalkable = tiles.get(map[position_x + 1][position_y]).isWalkable();
                facing_x = position_x + (isWalkable ? 2 : 1);
                facing_y = position_y;
                break;
            case RIGHT:
                isWalkable = tiles.get(map[position_x - 1][position_y]).isWalkable();
                facing_x = position_x - (isWalkable ? 2 : 1);
                facing_y = position_y;
                break;
        }

        currentEvent = tiles.get(map[facing_x][facing_y]).actionEvent;

        if(currentEvent == ActionEvent.NONE)
        {
            notify(currentEvent);
        }

        if(isWalkable)
        {
            isRunning = true;
        }
        else
        {
            notify(currentEvent);
            currentEvent = null;
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
                position_y--;
                break;
            case DOWN:
                position_y++;
                break;
            case LEFT:
                position_x++;
                break;
            case RIGHT:
                position_x--;
                break;
            default:
                break;
        }

        frameCount = 0;
        current = null;
        isRunning = false;

        notify(currentEvent);
        currentEvent = null;

        walk_offset_x = 0;
        walk_offset_y = 0;

    }

    @Override
    public void notify(Informer.Event event)
    {
        for(Listener listener : listeners)
        {
            listener.update(event);
        }
    }

    @Override
    public void addListener(Listener listener)
    {
        listeners.add(listener);
    }

    @Override
    public void notify(Subject.Event event)
    {
        for(Observer observer : observers)
        {
            observer.update(event);
        }
    }

    @Override
    public void addObserver(Observer observer)
    {
        this.observers.add(observer);
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

        private ActionEvent actionEvent;

        Tile(InputStream inputStream, boolean isWalkable, ActionEvent actionEvent)
        {
            bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeStream(inputStream), DIMENSIONS, DIMENSIONS, false);

            this.isWalkable = isWalkable;

            this.actionEvent = actionEvent;
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
