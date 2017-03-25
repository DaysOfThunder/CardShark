package com.mouthofrandom.cardshark.graphics;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.mouthofrandom.cardshark.activities.RouletteBoardActivity;
import com.mouthofrandom.cardshark.graphics.utility.Observer;
import com.mouthofrandom.cardshark.graphics.utility.Subject;

/**
 * Created by coleman on 2/28/17.
 */

public class ActionButton implements Observer
{
    private static final int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private static final int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private static final int buttonX = (int)(screenWidth - (.1 * screenHeight));
    private static final int buttonY = (int)(screenHeight - (.1 * screenHeight));
    private static final int buttonDiameter = screenHeight / 16;

    private static final int FRAMES = 30;

    private Context context;

    private Subject.ActionEvent event;

    private boolean isRunning = false;

    private int frameCount = 0;
    private boolean imageOn = false;

    public ActionButton() {}

    public ActionButton(Context context)
    {
        setContext(context);
    }

    public ActionButton setContext(Context context)
    {
        this.context = context;

        return this;
    }

    @Override
    public void update(Subject.Event event)
    {
        if(event instanceof Subject.ActionEvent)
        {
            Subject.ActionEvent _event = (Subject.ActionEvent) event;

            this.isRunning = _event != Subject.ActionEvent.NONE;

            this.event = _event;
        }
        else
        {
            Subject.TouchEvent _event = (Subject.TouchEvent) event;

            if(_event == Subject.TouchEvent.TOUCH_BUTTON)
            {
                switch(this.event)
                {
                    case NONE:
                        frameCount = 0;
                        break;
                    case ROULETTE:
                        context.startActivity(new Intent(context, RouletteBoardActivity.class));
                        break;
                }
            }
        }
    }

    @Override
    public void start(AnimationArguments animationArgs)
    {

    }

    @Override
    public void next()
    {
        if(frameCount < FRAMES)
        {
            frameCount++;
        }
        else
        {
            imageOn = !imageOn;
            frameCount = 0;
        }
    }

    @Override
    public boolean isRunning()
    {
        return isRunning;
    }

    @Override
    public boolean isComplete()
    {
        return false;
    }

    @Override
    public void finish()
    {
        return;
    }

    @Override
    public void draw(Canvas canvas) throws DrawableNotInitializedException
    {
        if(isRunning)
        {
            Paint p = new Paint();
            p.setColor(Color.RED);

            canvas.drawCircle(buttonX, buttonY, buttonDiameter, p);
        }
    }

    public static boolean isInButton(float x, float y)
    {
        return (x > (buttonX * .9) && y > (buttonY * .9));
    }
}
