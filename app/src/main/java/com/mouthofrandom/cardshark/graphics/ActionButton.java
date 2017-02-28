package com.mouthofrandom.cardshark.graphics;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;

import com.mouthofrandom.cardshark.activities.RouletteBoardActivity;
import com.mouthofrandom.cardshark.graphics.utility.Observer;
import com.mouthofrandom.cardshark.graphics.utility.Subject;

/**
 * Created by coleman on 2/28/17.
 */

public class ActionButton implements Observer
{
    private Context context;

    private Subject.ActionEvent event;

    private boolean isRunning = false;

    public ActionButton(Context context)
    {
        this.context = context;
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

    }
}
