package com.mouthofrandom.cardshark.fragments;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mouthofrandom.cardshark.R;
import com.mouthofrandom.cardshark.graphics.utility.Informer;
import com.mouthofrandom.cardshark.graphics.utility.Listener;
import com.mouthofrandom.cardshark.graphics.utility.Subject;

import java.text.NumberFormat;

public class CurrencyBar extends Fragment implements Listener
{
    public static final String CURRENCY = "CURRENCY";

    public static CurrencyBar CURRENCY_BAR;

    private TextView status_text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_currency_bar, container, false);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        int current = preferences.getInt(CURRENCY, -1);

        if(current == -1)
        {
            preferences.edit().putInt(CURRENCY, 1000).apply();
        }

        current = preferences.getInt(CURRENCY, -1);

        //Update Status Bar with current balance
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        Typeface status_font = Typeface.createFromAsset(getContext().getAssets(), "fonts/8bitOperatorPlusSC-Regular.ttf");
        status_text = (TextView) view.findViewById(R.id.statustext);
        status_text.setTypeface(status_font);
        status_text.setText(formatter.format(current));

        CURRENCY_BAR = this;

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void update(Informer.Event event)
    {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        Informer.MoneyEvent moneyEvent = (Informer.MoneyEvent) event;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        int current = preferences.getInt(CURRENCY, 0);

        current += moneyEvent.amount;

        preferences.edit().putInt(CURRENCY, current).apply();

        status_text.setText(formatter.format(current));
    }
}
