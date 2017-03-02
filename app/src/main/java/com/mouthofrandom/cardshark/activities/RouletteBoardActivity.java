package com.mouthofrandom.cardshark.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.mouthofrandom.cardshark.games.roulette.RouletteTable;

import com.mouthofrandom.cardshark.R;

public class RouletteBoardActivity extends AppCompatActivity {

    private int betAmount = 0;//How much we want to bet on a tile.
    RouletteTable table;//Backend.
    private Map<String, Button> buttons;//Betting tile buttons and associated ids.
    private Map<String, String> buttonText;//Display names of betting tiles.
    private Map<String, Integer> buttonTextColor;//Text colors of betting tiles.
    private Map<String, Integer> buttonColors;//Background display colors of betting tiles.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roulette_board);

        //Set up activity:
        init();
        setupBetAmountOptions();
        setupBetTiles();
        setupQuit();
        setupSpin();
    }

    /**
     * Initializes member objects.
     */
    private void init()
    {
        buttons = new HashMap<String, Button>();
        buttonText = new HashMap<String, String>();
        buttonTextColor = new HashMap<String, Integer>();
        buttonColors = new HashMap<String, Integer>();
        table = new RouletteTable();
    }

    /**
     * Creates action listeners for the betting tiles.
     */
    private void setupBetTiles(){
        ArrayList<Button> singleBets = new ArrayList<Button>();
        singleBets.add((Button) findViewById(R.id.Bet0));
        singleBets.add((Button) findViewById(R.id.Bet1));
        singleBets.add((Button) findViewById(R.id.Bet2));
        singleBets.add((Button) findViewById(R.id.Bet3));
        singleBets.add((Button) findViewById(R.id.Bet4));
        singleBets.add((Button) findViewById(R.id.Bet5));
        singleBets.add((Button) findViewById(R.id.Bet6));
        singleBets.add((Button) findViewById(R.id.Bet7));
        singleBets.add((Button) findViewById(R.id.Bet8));
        singleBets.add((Button) findViewById(R.id.Bet9));
        singleBets.add((Button) findViewById(R.id.Bet10));
        singleBets.add((Button) findViewById(R.id.Bet11));
        singleBets.add((Button) findViewById(R.id.Bet12));
        singleBets.add((Button) findViewById(R.id.Bet13));
        singleBets.add((Button) findViewById(R.id.Bet14));
        singleBets.add((Button) findViewById(R.id.Bet15));
        singleBets.add((Button) findViewById(R.id.Bet16));
        singleBets.add((Button) findViewById(R.id.Bet17));
        singleBets.add((Button) findViewById(R.id.Bet18));
        for(int i = 0; i < singleBets.size(); i++)
        {
            //Get the buttons and store them:
            Button b = singleBets.get(i);
            String id = String.valueOf(i);
            buttons.put(id,b);
            //Add in the listener:
            b.setOnClickListener(new BetClickListener(id,b));
            //Set up the default text, text color, and background color:
            b.setText(id);
            buttonText.put(id,id);
            if(i == 0){
                b.setBackgroundColor(Color.GREEN);
                buttonColors.put(id,Color.GREEN);
                b.setTextColor(Color.BLACK);
                buttonTextColor.put(id,Color.BLACK);
            } else if(0 == i%2) {
                b.setBackgroundColor(Color.BLACK);
                buttonColors.put(id,Color.BLACK);
                b.setTextColor(Color.WHITE);
                buttonTextColor.put(id,Color.WHITE);
            } else {
                b.setBackgroundColor(Color.RED);
                buttonColors.put(id,Color.RED);
                b.setTextColor(Color.BLACK);
                buttonTextColor.put(id,Color.BLACK);
            }
        }

        String[] names = {"1st 6","2nd 6","3rd 6","RED","BLACK","EVEN","ODD"};//Compound Bet names.
        int[] colors = {Color.GREEN,Color.GREEN,Color.GREEN,Color.RED,Color.BLACK,Color.GREEN,Color.GREEN};//Compound Bet colors.
        int[] textColors = {Color.BLACK,Color.BLACK,Color.BLACK,Color.BLACK,Color.WHITE,Color.BLACK,Color.BLACK};//Compound Bet text colors.
        ArrayList<Button> compoundBets = new ArrayList<Button>();
        compoundBets.add((Button) findViewById(R.id.BetFirst6));
        compoundBets.add((Button) findViewById(R.id.BetSecond6));
        compoundBets.add((Button) findViewById(R.id.BetThird6));
        compoundBets.add((Button) findViewById(R.id.BetRed));
        compoundBets.add((Button) findViewById(R.id.BetBlack));
        compoundBets.add((Button) findViewById(R.id.BetEven));
        compoundBets.add((Button) findViewById(R.id.BetOdd));
        for(int i = 0; i < compoundBets.size(); i++)
        {
            Button b = compoundBets.get(i);
            String id = names[i];
            buttons.put(id,b);
            b.setOnClickListener(new BetClickListener(id,b));
            b.setText(id);
            buttonText.put(id,id);
            buttonColors.put(id,colors[i]);
            b.setBackgroundColor(colors[i]);
            buttonTextColor.put(id,textColors[i]);
            b.setTextColor(textColors[i]);
        }
    }

    /**
     * Sets up the radio buttons to select how much money the user wants to bet.
     */
    private void setupBetAmountOptions(){
        Button chip10 = (Button) findViewById(R.id.Chip10);
        chip10.setOnClickListener(new BetAmountClickListener(10));

        Button chip25 = (Button) findViewById(R.id.Chip25);
        chip25.setOnClickListener(new BetAmountClickListener(25));

        Button chip50 = (Button) findViewById(R.id.Chip50);
        chip50.setOnClickListener(new BetAmountClickListener(50));

        Button chip100 = (Button) findViewById(R.id.Chip100);
        chip100.setOnClickListener(new BetAmountClickListener(100));

        Button chipClear = (Button) findViewById(R.id.ChipClear);
        chipClear.setOnClickListener(new BetAmountClickListener(0));
    }

    /**
     * Returns to previous activity.
     */
    private void setupQuit(){
        //TODO
    }

    /**
     * Spins the wheel and handles payout.
     */
    private void setupSpin(){
        Button spinButton = (Button) findViewById(R.id.Spin);
        spinButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int result = table.play();
                System.out.println("Spin Result:" + result);

                //TODO: Spin the roulette wheel.

                int payout = table.payout();
                System.out.println("Payout:" + payout);

                //TODO: Add the payout money, deduct the loss.

                //Reset board:
                resetAllBetTiles();
            }
        });
    }

    /**
     * Class to handle clicking on betting tiles.
     */
    private class BetClickListener implements View.OnClickListener
    {
        private String id;
        private Button button;//Associated button.

        public BetClickListener(String id, Button button)
        {
            this.id = id;
            this.button = button;
        }

        @Override
        public void onClick(View v) {
            //TODO: decrease money.
            if(betAmount == 0) {
                table.placeSingleBet(id,0);//Reset bet.
                resetBetTile(button,id);
                //TODO: return money.
            } else {
                table.addSingleBet(id,betAmount);
                button.setText("$" + String.valueOf(table.getTileBetAmount(id)));
                button.setBackgroundColor(Color.WHITE);
                button.setTextColor(Color.BLACK);
            }
        }
    }

    /**
     * Listener to determine what betting value is chosen.
     */
    private class BetAmountClickListener implements View.OnClickListener
    {
        private int value;

        public BetAmountClickListener(int value)
        {
            this.value = value;
        }

        @Override
        public void onClick(View v) {
            betAmount = value;//Set the selected betting amount.
        }
    }

    /**
     * Resets all betting tiles.
     */
    private void resetAllBetTiles()
    {
        for(String id : buttons.keySet())
        {
            resetBetTile(buttons.get(id),id);
        }
    }

    /**
     * Resets a single button tile's graphics: return to base color and name.
     * @param button
     * @param id
     */
    private void resetBetTile(Button button, String id)
    {
        button.setBackgroundColor(buttonColors.get(id));
        button.setTextColor(buttonTextColor.get(id));
        button.setText(buttonText.get(id));
    }

}
