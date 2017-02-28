package com.mouthofrandom.cardshark.game;

import android.app.Activity;
import android.content.Intent;

import com.mouthofrandom.cardshark.activities.CasinoActivity;
import com.mouthofrandom.cardshark.activities.RouletteBoardActivity;

/**
 * Created by coleman on 2/28/17.
 */

public class ActionFactory
{
    public static final String ROULETTE = "ROULETTE";
    public static final String NO_ACTION = "NO_ACTION";

    public interface Action
    {
        void doAction(Activity activity);
    }

    public static class NoAction implements Action
    {

        @Override
        public void doAction(Activity activity)
        {
            CasinoActivity _activity = (CasinoActivity) activity;

            _activity.setActionButtonVisibility(false);
        }
    }

    public static class RouletteAction implements Action
    {
        @Override
        public void doAction(Activity activity)
        {
            CasinoActivity _activity = (CasinoActivity) activity;

            Intent intent = new Intent(_activity, RouletteBoardActivity.class);
            _activity.startActivity(intent);

            _activity.setActionButtonVisibility(true);
        }
    }

    public static Action buildAction(String activityName)
    {
        if(activityName.equals(ROULETTE))
        {
            return new RouletteAction();
        }
        else
        {
            return new NoAction();
        }
    }
}
