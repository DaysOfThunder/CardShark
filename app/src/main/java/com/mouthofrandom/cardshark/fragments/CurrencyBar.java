package com.mouthofrandom.cardshark.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mouthofrandom.cardshark.R;

import java.text.NumberFormat;

public class CurrencyBar extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_currency_bar, container, false);

        //Update Status Bar with current balance
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        Typeface status_font = Typeface.createFromAsset(getContext().getAssets(), "fonts/8bitOperatorPlusSC-Regular.ttf");
        TextView status_text = (TextView) view.findViewById(R.id.statustext);
        status_text.setTypeface(status_font);
        status_text.setText(formatter.format(0));

        // Inflate the layout for this fragment
        return view;
    }
}
