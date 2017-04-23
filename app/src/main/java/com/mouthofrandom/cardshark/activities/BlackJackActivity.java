package com.mouthofrandom.cardshark.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mouthofrandom.cardshark.R;
import com.mouthofrandom.cardshark.fragments.CurrencyBar;
import com.mouthofrandom.cardshark.game.blackjack.BlackJack;
import com.mouthofrandom.cardshark.graphics.utility.Informer;
import com.mouthofrandom.cardshark.graphics.utility.Listener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Stephen on 4/23/2017.
 */

public class BlackJackActivity extends AppCompatActivity implements Informer
{
    /**
     * Bet amount from last betting round.
     */
    private int lastBet = 0;
    /**
     * Bet amount of current betting round. (Amount changed from last round)
     */
    private int currentBet = 0;
    /**
     * Value of betting chip selected.
     */
    private int betAmount = 0;
    /**
     * If we are in a round rn.
     */
    private boolean inRound = false;

    /**
     * Game cards(Resource id) and names.
     */
    private HashMap<String,Integer> cards;

    /**
     * Cards in hand.
     */
    private ImageView[] playerCards;
    private int pCardIndex = 0;
    private ImageView[] dealerCards;
    private int dCardIndex = 0;

    private Collection<Listener> listeners;
    private BlackJack bj;
    private MediaPlayer player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blackjack);

        //Set up activity:
        init();
        setupBetAmountOptions();
        setupCards();
        setupGameActions();
        setupQuit();

        //Set up Music:
        player = MediaPlayer.create(this, R.raw.cardshark_roulette);
        player.setLooping(true);
        float vol = (float).75;
        player.setVolume(vol,vol);
        player.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.start();
        this.listeners.add(CurrencyBar.CURRENCY_BAR);
    }

    @Override
    protected void onPause() {
        player.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        player.stop();
        super.onDestroy();
    }

    /**
     * Initializes member objects.
     */
    private void init()
    {
        listeners = new ArrayList<>();
        bj = new BlackJack();
        playerCards = new ImageView[4];
        dealerCards = new ImageView[4];
        cards = new HashMap<String,Integer>();
    }

    /**
     * Sets up the radio buttons to select how much money the user wants to bet.
     */
    private void setupBetAmountOptions(){
        Button chip10 = (Button) findViewById(R.id.Chip10_BJ);
        chip10.setOnClickListener(new BetAmountClickListener(10));

        Button chip25 = (Button) findViewById(R.id.Chip25_BJ);
        chip25.setOnClickListener(new BetAmountClickListener(25));

        Button chip50 = (Button) findViewById(R.id.Chip50_BJ);
        chip50.setOnClickListener(new BetAmountClickListener(50));

        Button chip100 = (Button) findViewById(R.id.Chip100_BJ);
        chip100.setOnClickListener(new BetAmountClickListener(100));

        Button chipClear = (Button) findViewById(R.id.ChipClear_BJ);
        chipClear.setOnClickListener(new BetAmountClickListener(0));
    }

    /**
     * Sets up the card map.
     */
    private void setupCards()
    {
        cards.put("(SPADES, VALUE, 2)",R.drawable.spades_2);
        cards.put("(SPADES, VALUE, 3)",R.drawable.spades_3);
        cards.put("(SPADES, VALUE, 4)",R.drawable.spades_4);
        cards.put("(SPADES, VALUE, 5)",R.drawable.spades_5);
        cards.put("(SPADES, VALUE, 6)",R.drawable.spades_6);
        cards.put("(SPADES, VALUE, 7)",R.drawable.spades_7);
        cards.put("(SPADES, VALUE, 8)",R.drawable.spades_8);
        cards.put("(SPADES, VALUE, 9)",R.drawable.spades_9);
        cards.put("(SPADES, VALUE, 10)",R.drawable.spades_10);
        cards.put("(SPADES, JACK, 10)",R.drawable.spades_jack);
        cards.put("(SPADES, QUEEN, 10)",R.drawable.spades_queen);
        cards.put("(SPADES, KING, 10)",R.drawable.spades_king);
        cards.put("(SPADES, ACE, 11)",R.drawable.spades_ace);

        cards.put("(DIAMONDS, VALUE, 2)",R.drawable.diamonds_2);
        cards.put("(DIAMONDS, VALUE, 3)",R.drawable.diamonds_3);
        cards.put("(DIAMONDS, VALUE, 4)",R.drawable.diamonds_4);
        cards.put("(DIAMONDS, VALUE, 5)",R.drawable.diamonds_5);
        cards.put("(DIAMONDS, VALUE, 6)",R.drawable.diamonds_6);
        cards.put("(DIAMONDS, VALUE, 7)",R.drawable.diamonds_7);
        cards.put("(DIAMONDS, VALUE, 8)",R.drawable.diamonds_8);
        cards.put("(DIAMONDS, VALUE, 9)",R.drawable.diamonds_9);
        cards.put("(DIAMONDS, VALUE, 10)",R.drawable.diamonds_10);
        cards.put("(DIAMONDS, JACK, 10)",R.drawable.diamonds_jack);
        cards.put("(DIAMONDS, QUEEN, 10)",R.drawable.diamonds_queen);
        cards.put("(DIAMONDS, KING, 10)",R.drawable.diamonds_king);
        cards.put("(DIAMONDS, ACE, 11)",R.drawable.diamonds_ace);

        cards.put("(CLUBS, VALUE, 2)",R.drawable.clubs_2);
        cards.put("(CLUBS, VALUE, 3)",R.drawable.clubs_3);
        cards.put("(CLUBS, VALUE, 4)",R.drawable.clubs_4);
        cards.put("(CLUBS, VALUE, 5)",R.drawable.clubs_5);
        cards.put("(CLUBS, VALUE, 6)",R.drawable.clubs_6);
        cards.put("(CLUBS, VALUE, 7)",R.drawable.clubs_7);
        cards.put("(CLUBS, VALUE, 8)",R.drawable.clubs_8);
        cards.put("(CLUBS, VALUE, 9)",R.drawable.clubs_9);
        cards.put("(CLUBS, VALUE, 10)",R.drawable.clubs_10);
        cards.put("(CLUBS, JACK, 10)",R.drawable.clubs_jack);
        cards.put("(CLUBS, QUEEN, 10)",R.drawable.clubs_queen);
        cards.put("(CLUBS, KING, 10)",R.drawable.clubs_king);
        cards.put("(CLUBS, ACE, 11)",R.drawable.clubs_ace);

        cards.put("(HEARTS, VALUE, 2)",R.drawable.hearts_2);
        cards.put("(HEARTS, VALUE, 3)",R.drawable.hearts_3);
        cards.put("(HEARTS, VALUE, 4)",R.drawable.hearts_4);
        cards.put("(HEARTS, VALUE, 5)",R.drawable.hearts_5);
        cards.put("(HEARTS, VALUE, 6)",R.drawable.hearts_6);
        cards.put("(HEARTS, VALUE, 7)",R.drawable.hearts_7);
        cards.put("(HEARTS, VALUE, 8)",R.drawable.hearts_8);
        cards.put("(HEARTS, VALUE, 9)",R.drawable.hearts_9);
        cards.put("(HEARTS, VALUE, 10)",R.drawable.hearts_10);
        cards.put("(HEARTS, JACK, 10)",R.drawable.hearts_jack);
        cards.put("(HEARTS, QUEEN, 10)",R.drawable.hearts_queen);
        cards.put("(HEARTS, KING, 10)",R.drawable.hearts_king);
        cards.put("(HEARTS, ACE, 11)",R.drawable.hearts_ace);

        playerCards[0] = (ImageView) findViewById(R.id.Player1);
        playerCards[1] = (ImageView) findViewById(R.id.Player2);
        playerCards[2] = (ImageView) findViewById(R.id.Player3);
        playerCards[3] = (ImageView) findViewById(R.id.Player4);
        dealerCards[0] = (ImageView) findViewById(R.id.Dealer1);
        dealerCards[1] = (ImageView) findViewById(R.id.Dealer2);
        dealerCards[2] = (ImageView) findViewById(R.id.Dealer3);
        dealerCards[3] = (ImageView) findViewById(R.id.Dealer4);
    }


    /**
     * Sets up the actions: deal, hit, stand, bet.
     */
    private void setupGameActions()
    {
        //Bet:
        final Button betZone = (Button) findViewById(R.id.BetZone_BJ);
        betZone.setOnClickListener(new BetClickListener(betZone));

        //Deal:
        final Button deal = (Button) findViewById(R.id.Deal);
        deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Deal clicked! " + inRound);
                if(inRound)
                {
                    return;
                }
                inRound = true;
                bj.play(0);

                //Set up cards:
                dealerCards[0].setImageResource(cards.get(bj.dealerTopCard().toString()));
                dealerCards[1].setImageResource(R.drawable.cardback);
                dealerCards[2].setImageResource(R.drawable.cardspace);
                dealerCards[3].setImageResource(R.drawable.cardspace);
                dCardIndex = 2;
                playerCards[0].setImageResource(cards.get(bj.playerCards().get(0).toString()));
                playerCards[1].setImageResource(cards.get(bj.playerCards().get(1).toString()));
                playerCards[2].setImageResource(R.drawable.cardspace);
                playerCards[3].setImageResource(R.drawable.cardspace);
                pCardIndex = 2;

                //Reset bet counter:
                betZone.setText("Bet: $0");
                currentBet = 0;
                lastBet = 0;
            }
        });

        //Stand:
        final Button stand = (Button) findViewById(R.id.Stand);
        stand.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                System.out.println("Stand clicked! " + inRound);
                if(!inRound)
                {
                    return;
                }

                //Finish:
                int payout = bj.stand();
                dCardIndex = bj.delearNumCards();
                inRound = false;

                //Show all cards:
                for(int i = 0; i < dCardIndex; i++)
                {
                    dealerCards[i].setImageResource(cards.get(bj.dealerCards().get(i).toString()));
                }
                for(int i = 0; i < pCardIndex; i++)
                {
                    playerCards[i].setImageResource(cards.get(bj.playerCards().get(i).toString()));
                }

                //Pay back:
                MoneyEvent event = new MoneyEvent();
                if(payout > 0)
                {
                    event.amount = payout;
                    betZone.setText("WINNER! $" + payout);
                }
                else if(payout < 0)
                {
                    betZone.setText("LOSE!");
                }
                else {
                    betZone.setText("Bet Here Next Time!");
                }
                BlackJackActivity.this.notify(event);
            }
        });

        //Hit:
        final Button hit = (Button) findViewById(R.id.Hit);
        hit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Hit clicked! " + inRound);
                if(!inRound)
                {
                    return;
                }

                //Process:
                lastBet = currentBet;
                currentBet = 0;
                bj.hit();

                dCardIndex = bj.delearNumCards();
                pCardIndex = bj.playerCards().size();

                //Show cards:
                for(int i = 0; i < dCardIndex; i++)
                {
                    if(i == 0){
                        dealerCards[i].setImageResource(cards.get(bj.dealerCards().get(i).toString()));
                    }
                    else{
                        dealerCards[i].setImageResource(R.drawable.cardback);//Hide dealer cards.
                    }
                }
                for(int i = 0; i < pCardIndex; i++)
                {
                    playerCards[i].setImageResource(cards.get(bj.playerCards().get(i).toString()));
                }

                //Max number of cards or bust; end now:
                if(pCardIndex == 4 || bj.bust())
                {
                    stand.performClick();
                }
            }
        });
    }

    /**
     * Returns to previous activity.
     */
    private void setupQuit()
    {
        //final Intent intent = new Intent(RouletteBoardActivity.this, CasinoActivity.class);
        Button quitButton = (Button) findViewById(R.id.Quit_BJ);

        quitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(inRound)
                {
                    return;
                }
                finish();
            }
        });

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
        this.listeners.add(listener);
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
     * Class to handle clicking on betting tiles.
     */
    private class BetClickListener implements View.OnClickListener
    {
        Button button;

        public BetClickListener(Button button)
        {
            super();
            this.button = button;
        }

        @Override
        public void onClick(View v)
        {
            if(!inRound)
                return;

            MoneyEvent event = new MoneyEvent();

            if(betAmount == 0)
            {
                //Reset bet:
                event.amount = currentBet;
                bj.addBet(-currentBet);
                currentBet = 0;
            }
            else
            {
                //Add bet:
                event.amount = betAmount * -1;
                bj.addBet(betAmount);
                currentBet += betAmount;
            }

            button.setText("Bet: $" + String.valueOf(lastBet + currentBet));
            BlackJackActivity.this.notify(event);
        }
    }
}
