package com.mouthofrandom.cardshark.graphics.utility;

/**
 * Created by coleman on 2/26/17.
 */
public interface Subject
{
    void notify(Event event);

    void addObserver(Observer observer);

    interface Event {};

    enum TouchEvent implements Event
    {
        SWIPE_UP, SWIPE_DOWN, SWIPE_LEFT, TOUCH_BUTTON, SWIPE_RIGHT
    }

    enum ActionEvent implements Event
    {
        NONE, ROULETTE, BLACKJACK;

        public static ActionEvent match(String string)
        {
            for(ActionEvent actionEvent : values())
            {
                if(string.toUpperCase().contains(actionEvent.name()))
                {
                    return actionEvent;
                }
            }

            return NONE;
        }
    }
}
