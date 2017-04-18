package com.mouthofrandom.cardshark.graphics.utility;

/**
 * Created by coleman on 4/18/17.
 */

public interface Informer
{
    void notify(Event event);

    void addListener(Listener listener);

    interface Event {}

    static class MoneyEvent implements Event
    {
        public int amount;
    }
}
